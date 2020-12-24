package com.jiuzhang.zhihu.service.vote.impl;

import com.jiuzhang.zhihu.constant.Constants;
import com.jiuzhang.zhihu.entity.VoteStats;
import com.jiuzhang.zhihu.entity.vo.VoteVO;
import com.jiuzhang.zhihu.service.IVoteStatsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.jiuzhang.zhihu.constant.Constants.VOTED_ANSWERS;

/**
 * 基于缓存的投票服务策略类（高并发点赞）
 *
 * @author 九章算法
 * @since 2020-11-25
 */
@Slf4j
@Service("cachedVoteStrategyService")
public class CachedVoteStrategyServiceImpl extends AbstractVoteStrategyServiceImpl {

	@Autowired
	@Qualifier("voteStatsService")
	private IVoteStatsService voteStatsService;

    @Autowired
    private RedisTemplate redisTemplate;

	@Override
	public boolean checkVote(Long answerId, int voteType, String userId) {
		return alreadyVoted(new VoteVO(answerId, userId, voteType));
	}

	/**
	 * 从redis中取得”赞“或”踩“的数量
	 */
	@Override
	public int getCount(Long answerId, int voteType) {
		String key = Constants.ANSWER_VOTE_COUNT + answerId + voteType;
		return (Integer) Optional.ofNullable( redisTemplate.opsForValue().get(key) ).orElse(0) ;
	}

	@Override
	public boolean submitVote(VoteVO vote) {
		loadStatsIfAbsent(vote);
		if (alreadyVoted(vote)) {
			return true;
		}
		return memorizeVotedAnswerId(vote.getAnswerId()) && addVoteUser(vote) && incrCount(vote);
	}

	@Override
	public boolean cancelVote(VoteVO vote) {
		loadStatsIfAbsent(vote);
		if (!alreadyVoted(vote)) {
			return true;
		}
		return memorizeVotedAnswerId(vote.getAnswerId()) && removeVoteUser(vote) && decrCount(vote);
	}


	// --------------------------- 记录已更新的answerId ---------------------------
	private boolean memorizeVotedAnswerId(Long answerId) {
		return redisTemplate.opsForSet().add(VOTED_ANSWERS, answerId) > 0;
	}

	// --------------------------- 加载器 ---------------------------
	private void loadStatsIfAbsent(VoteVO vote) {
		Long answerId = vote.getAnswerId();
		int type = vote.getType();
		String keyCnt = Constants.ANSWER_VOTE_COUNT + answerId + type;
		String keySet = Constants.ANSWER_VOTER_SET + answerId + type;
		boolean hasCnt = redisTemplate.hasKey(keyCnt);
		boolean hasSet = redisTemplate.hasKey(keySet);

		// 注：如果
		if (!hasSet || !hasCnt) {
			VoteStats stats = voteStatsService.queryByCondition(answerId, type);
			if (stats !=null) {
				redisTemplate.opsForValue().set(keyCnt, stats.getVoteCount());
				redisTemplate.opsForSet().add(keySet, stats.getVoteUsers());
			}
		}
	}

	// --------------------------- 投票者SET集合操作 ---------------------------
	private boolean alreadyVoted(VoteVO vote) {
		String key = Constants.ANSWER_VOTER_SET + vote.getAnswerId() + vote.getType();
		return redisTemplate.opsForSet().isMember(key, vote.getUserId());
	}

	private boolean addVoteUser(VoteVO vote) {
		String key = Constants.ANSWER_VOTER_SET + vote.getAnswerId() + vote.getType();
		return redisTemplate.opsForSet().add(key, vote.getUserId()) != null;
	}

	private boolean removeVoteUser(VoteVO vote) {
		String key = Constants.ANSWER_VOTER_SET + vote.getAnswerId() + vote.getType();
		return redisTemplate.opsForSet().remove(key, vote.getUserId()) != null;
	}

	// --------------------------- 投票数counter操作 ---------------------------
	private boolean incrCount(VoteVO vote) {
		String key = Constants.ANSWER_VOTE_COUNT + vote.getAnswerId() + vote.getType();
		return redisTemplate.opsForValue().increment(key) != null;
	}

	private boolean decrCount(VoteVO vote) {
		String key = Constants.ANSWER_VOTE_COUNT + vote.getAnswerId() + vote.getType();
		return redisTemplate.opsForValue().decrement(key) != null;
	}

}
