package com.jiuzhang.zhihu.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiuzhang.zhihu.common.enums.VoteTypeEnum;
import com.jiuzhang.zhihu.entity.VoteStats;
import com.jiuzhang.zhihu.service.IVoteStatsService;
import com.jiuzhang.zhihu.service.vote.helper.CacheService;
import com.jiuzhang.zhihu.util.SetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
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
    @Qualifier("voteStatsService")
    private IVoteStatsService voteStatsService;

    @Autowired
    private CacheService cacheService;

    /**
     * 定期将cache中的投票刷入DB
     */
    @Scheduled(cron="*/5 * * * * ?")
    public void execute() {

        log.info("开始定时批量点赞入库");

        for (VoteTypeEnum typeEnum : VoteTypeEnum.values()) {

            int type = typeEnum.getType();

            // 投票被更改过的 answer ids
            Set<Long> votedAnswerIds = cacheService.getAnsweredIds(typeEnum.getType());

            // 计数 & 投票用户列表
            for (Long answerId : votedAnswerIds) {

                Integer count = cacheService.getCount(answerId, type);
                Set<String> voterIds = cacheService.getVoteUserSet(answerId, type);

                // 保存投票统计
                boolean rv = createOrUpdateVoteStats(type, answerId, count, voterIds);

                if (rv) {
                    // 移除更新记录
                    cacheService.clearVoteStats(answerId, type);
                    // 重新加载 计数器
                    cacheService.loadVoteStats(answerId, type);
                    log.info("点赞定时批量入库成功");
                } else {
                    log.error("点赞定时批量入库失败");
                }

            } // for(;;)
        } // for(;;)
    }

    private boolean createOrUpdateVoteStats(int type, Long answerId, Integer count, Set<String> voterIds) {

        // 查询现有的 统计
        QueryWrapper<VoteStats> query = new QueryWrapper<>(new VoteStats(answerId, type));
        VoteStats upVoteStats = voteStatsService.getOne(query);

        // 如果记录不存在，则新建
        upVoteStats = (upVoteStats==null) ? new VoteStats(answerId, type) : upVoteStats;
        upVoteStats.setVoteCount(count);
        upVoteStats.setVoteUsers(SetUtil.serialize(voterIds));

        // 保存记录
        return voteStatsService.saveOrUpdate(upVoteStats);
    }

}
