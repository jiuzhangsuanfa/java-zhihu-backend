package com.jiuzhang.zhihu.service.vote.impl;

import com.jiuzhang.zhihu.entity.vo.VoteVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

/**
 * 基于缓存的投票服务策略类（高并发点赞）
 *
 * @author 九章算法
 * @since 2020-11-25
 */
@Slf4j
@Primary
//@Service("cachedVoteStrategyService")
public class CachedVoteStrategyServiceImpl extends AbstractVoteStrategyServiceImpl {

	private static final String VOTED_ANSWERS = "ZHIHU:ANSWER:VOTED:";
	private static final String ANSWER_VOTER_SET = "ZHIHU:ANSWER:VOTERS:";
	private static final String ANSWER_VOTE_COUNT = "ZHIHU:ANSWER:VOTE:COUNT";

    @Autowired
    private RedisTemplate redisTemplate;

	@Override
	public boolean checkVote(Long answerId, int voteType, String userId) {
		VoteVO vote = new VoteVO();
		vote.setAnswerId(answerId);
		vote.setType(voteType);
		vote.setUserId(userId);
		return alreadyVoted(vote);
	}

	/**
	 * 从redis中取得”赞“或”踩“的数量
	 */
	@Override
	public int getCount(Long answerId, int voteType) {
		String key = ANSWER_VOTE_COUNT + answerId + voteType;
		return (Integer) Optional.ofNullable( redisTemplate.opsForValue().get(key) ).orElse(0) ;
	}

	@Override
	public boolean submitVote(VoteVO vote) {
		if (alreadyVoted(vote)) {
			return true;
		}
		return addVoteUser(vote) && incrCount(vote);
	}

	@Override
	public boolean cancelVote(VoteVO vote) {
		if (!alreadyVoted(vote)) {
			return true;
		}
		return removeVoteUser(vote) && decrCount(vote);
	}

	// --------------------------- uid集合操作 ---------------------------
	private boolean alreadyVoted(VoteVO vote) {
		String key = ANSWER_VOTER_SET + vote.getAnswerId() + vote.getType();
		return redisTemplate.opsForSet().isMember(key, vote.getUserId());
	}

	private boolean addVoteUser(VoteVO vote) {
		String key = ANSWER_VOTER_SET + vote.getAnswerId() + vote.getType();
		return redisTemplate.opsForSet().add(key, vote.getUserId()) != null;
	}

	private boolean removeVoteUser(VoteVO vote) {
		String key = ANSWER_VOTER_SET + vote.getAnswerId() + vote.getType();
		return redisTemplate.opsForSet().remove(key, vote.getUserId()) != null;
	}

	// --------------------------- 计数器操作 ---------------------------
	private boolean incrCount(VoteVO vote) {
		String key = ANSWER_VOTE_COUNT + vote.getAnswerId() + vote.getType();
		return redisTemplate.opsForValue().increment(key) != null;
	}

	private boolean decrCount(VoteVO vote) {
		String key = ANSWER_VOTE_COUNT + vote.getAnswerId() + vote.getType();
		return redisTemplate.opsForValue().decrement(key) != null;
	}

}
