package com.jiuzhang.zhihu.service.vote.impl;

import com.jiuzhang.zhihu.entity.vo.VoteVO;
import com.jiuzhang.zhihu.service.vote.CacheService;
import com.jiuzhang.zhihu.service.vote.IVoteStrategyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 基于缓存的投票服务策略类（高并发点赞）
 *
 * @author 九章算法
 * @since 2020-11-25
 */
@Slf4j
//@Service("cachedVoteStrategyService")
public class CachedVoteStrategyServiceImpl implements IVoteStrategyService {

	@Autowired
	private CacheService cacheService;


	@Override
	public boolean checkVote(Long answerId, int voteType, String userId) {
		return alreadyVoted(new VoteVO(answerId, userId, voteType));
	}

	/**
	 * 从redis中取得”赞“或”踩“的数量
	 */
	@Override
	public int getCount(Long answerId, int voteType) {
		return cacheService.getCount(answerId, voteType);
	}

	@Override
	public boolean submitVote(VoteVO vote) {
		loadStatsIfAbsent(vote);
		if (alreadyVoted(vote)) {
			return true;
		}
		return cacheService.addVotedAnswerId(vote.getAnswerId(), vote.getType())
				&& addVoteUser(vote)
				&& incrCount(vote);
	}

	@Override
	public boolean cancelVote(VoteVO vote) {
		loadStatsIfAbsent(vote);
		if (!alreadyVoted(vote)) {
			return true;
		}
		return cacheService.addVotedAnswerId(vote.getAnswerId(), vote.getType())
				&& removeVoteUser(vote)
				&& decrCount(vote);
	}


	// --------------------------- 加载器 ---------------------------
	public void loadStatsIfAbsent(VoteVO vote) {
		Long answerId = vote.getAnswerId();
		int type = vote.getType();
		boolean isLoaded = cacheService.isVoteStatsLoaded(answerId, type);
		if (!isLoaded) {
			cacheService.loadVoteStats(answerId, type);
		}
	}

	// --------------------------- 投票数counter操作 ---------------------------
	private boolean incrCount(VoteVO vote) {
		return cacheService.incrCount(vote.getAnswerId(), vote.getType());
	}

	private boolean decrCount(VoteVO vote) {
		return cacheService.decrCount(vote.getAnswerId(), vote.getType());
	}

	// --------------------------- 投票者SET集合操作 ---------------------------
	private boolean alreadyVoted(VoteVO vote) {
		return cacheService.alreadyVoted(vote.getAnswerId(), vote.getType(), vote.getUserId());
	}

	private boolean addVoteUser(VoteVO vote) {
		return cacheService.addVoteUser(vote.getAnswerId(), vote.getType(), vote.getUserId());
	}

	private boolean removeVoteUser(VoteVO vote) {
		return cacheService.removeVoteUser(vote.getAnswerId(), vote.getType(), vote.getUserId());
	}

}
