package com.jiuzhang.zhihu.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiuzhang.zhihu.common.enums.VoteTypeEnum;
import com.jiuzhang.zhihu.constant.Constants;
import com.jiuzhang.zhihu.entity.VoteStats;
import com.jiuzhang.zhihu.service.IVoteStatsService;
import com.jiuzhang.zhihu.service.vote.VoteStatsCacheService;
import com.jiuzhang.zhihu.util.SetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.jiuzhang.zhihu.constant.Constants.VOTED_ANSWERS;

/**
 * 用户投票统计任务
 * @Author 九章算法
 */
@Slf4j
@Component
public class VotePersistenceTask {

    @Autowired
    @Qualifier("voteStatsService")
    private IVoteStatsService voteStatsService;

    @Autowired
    private VoteStatsCacheService voteStatsCacheService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 将缓存中的刷入DB
     */
    @Scheduled(cron="*/5 * * * * ?")
    public void execute() {

        log.info("开始定时批量点赞入库");

        for (VoteTypeEnum typeEnum : VoteTypeEnum.values()) {

            // 投票被更改过的 answer ids
            String key_voted_answers = VOTED_ANSWERS + typeEnum.getCategory();
            Set<Long> votedAnswerIds = redisTemplate.opsForSet().members(key_voted_answers);

            // 计数 & 投票用户列表
            for (Long answerId : votedAnswerIds) {
                String countKey = Constants.ANSWER_VOTE_COUNT + answerId + VoteTypeEnum.UPVOTE.getCategory();
                String voterIdsKey = Constants.ANSWER_VOTER_SET + answerId + VoteTypeEnum.UPVOTE.getCategory();

                // 查询缓存中的 计数+投票ids
                Integer count = (Integer) redisTemplate.opsForValue().get(countKey);
                Set<String> voterIds = redisTemplate.opsForSet().members(voterIdsKey);

                // 更新统计数
                int type = typeEnum.getCategory();
                QueryWrapper<VoteStats> query = new QueryWrapper<>(new VoteStats(answerId, type));
                VoteStats upVoteStats = voteStatsService.getOne(query);

                // 如果记录不存在，则新建
                if (upVoteStats == null) {
                    upVoteStats = new VoteStats(answerId, type);
                }
                upVoteStats.setVoteCount(count);
                upVoteStats.setVoteUsers(SetUtil.serialize(voterIds));

                // 保存记录
                boolean rv = voteStatsService.saveOrUpdate(upVoteStats);
                if (rv) {
                    // 移除更新记录
                    redisTemplate.opsForSet().remove(VOTED_ANSWERS+type, answerId);
                    redisTemplate.delete(countKey);
                    redisTemplate.delete(voterIdsKey);
                    log.error("点赞定时批量入库成功");

                    // 重新加载 计数器
                    voteStatsCacheService.loadVoteStats(answerId, type);

                } else {
                    log.error("点赞定时批量入库失败");
                }

            } // for(;;)
        } // for(;;)

    }

}
