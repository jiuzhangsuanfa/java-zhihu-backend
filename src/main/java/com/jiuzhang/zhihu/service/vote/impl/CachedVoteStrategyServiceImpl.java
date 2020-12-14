package com.jiuzhang.zhihu.service.vote.impl;

import com.jiuzhang.zhihu.common.enums.VoteActionEnum;
import com.jiuzhang.zhihu.common.enums.VoteTypeEnum;
import com.jiuzhang.zhihu.entity.Answer;
import com.jiuzhang.zhihu.entity.vo.VoteVO;
import com.jiuzhang.zhihu.service.IAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 基于缓存的投票服务策略类（高并发点赞）
 *
 * @author 九章算法
 * @since 2020-11-25
 */
@Service
public class CachedVoteStrategyServiceImpl extends AbstractVoteStrategyServiceImpl {

	public static final String PREFIX_VOTERS = "ZHIHU:ANSWER-VOTERS:";

    @Autowired
    private RedisTemplate redisTemplate;

	@Autowired
	private IAnswerService answerService;


	@Override
	protected int calcVoteCount(Long answerId, String userId, int voteType) {
		//

		return 0;
	}

	@Override
	protected void logVote(VoteVO vote) {
		// 更新redis 投票人SET
		if (VoteActionEnum.SUBMIT.getCategory() == vote.getAction()) {
			redisTemplate.opsForSet().add(PREFIX_VOTERS+vote.getAnswerId(), vote.getUserId());
		}
		if (VoteActionEnum.CANCEL.getCategory() == vote.getAction()) {
			redisTemplate.opsForSet().remove(PREFIX_VOTERS+vote.getAnswerId(), vote.getUserId());
		}
	}

	/**
	 * 改变answer的投票数
	 * @param answerId
	 * @param voteType
	 * @param count
	 */
    protected void changeVoteCount(Long answerId, int voteType, int voteAction, int count) {

        // 2. 投票记录入库
        redisTemplate.opsForValue().set("h", "gog");


    }


	@Override
	protected void submitVote(VoteVO vote) {
		// 是否已经投过票？
		if (alreadyVoted(vote)) {
			clearVoted();
			return;
		}

		//
	}

	private void clearVoted() {

	}

	private boolean alreadyVoted(VoteVO vote) {
		return false;
	}

	@Override
	protected void cancelVote(VoteVO vote) {
		deleteVote(vote);
	}

	private void deleteVote(VoteVO vote) {

	}

}
