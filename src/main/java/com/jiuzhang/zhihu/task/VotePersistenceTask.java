package com.jiuzhang.zhihu.task;

import com.jiuzhang.zhihu.entity.Answer;
import com.jiuzhang.zhihu.entity.VoteStats;
import com.jiuzhang.zhihu.service.IAnswerService;
import com.jiuzhang.zhihu.service.IVoteStatsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 用户投票统计任务
 * @Author 九章算法
 */
@Slf4j
@Component
public class VotePersistenceTask {

    @Autowired
    private IAnswerService answerService;

    @Autowired
    @Qualifier("voteService")
    private IVoteStatsService voteService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 将缓存中的刷入DB
     */
//    @Scheduled(cron="*/10 * * * * ?")
    public void execute() {
        log.info("开始同步点赞入库");

        // 投票被更改过的 answer ids
        Set<String> votedAnswerIds = redisTemplate.opsForSet().members("voted_answer_ids");

        // 计数 & 投票用户列表
        for (String aId : votedAnswerIds) {

            Integer upCount = (Integer) redisTemplate.opsForValue().get("answer_vote_count:" + "up:" + aId);
            Set<String> upVoterIds = redisTemplate.opsForSet().members("answer_voter_ids:up:" + aId);
            Integer downCount = (Integer) redisTemplate.opsForValue().get("answer_vote_count:" + "down:" + aId);
            Set<String> downVoterIds = redisTemplate.opsForSet().members("answer_voter_ids:down:" + aId);

            VoteStats upVoteStats = voteService.getOne(null);
            upVoteStats.setVoteCount(upCount);
            upVoteStats.setVoteUsers(upVoterIds.toString());

            VoteStats downVoteStats = voteService.getOne(null);
            downVoteStats.setVoteCount(downCount);
            downVoteStats.setVoteUsers(downVoterIds.toString());
        }

        log.info("点赞入库结束");
    }
}
