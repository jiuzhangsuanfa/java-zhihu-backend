package com.jiuzhang.zhihu.job;

import com.jiuzhang.zhihu.service.IVoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 用户投票统计任务
 * @Author 九章算法
 */
@Slf4j
@Component
public class SummarizeVoteJob {

    @Autowired
    private IVoteService voteService;

    /**
     * 将缓存中的
     */
//    @Scheduled(cron="*/10 * * * * ?")
    public void execute() {
        log.info("开始同步点赞入库");


        log.info("点赞入库结束");
    }
}
