package com.jiuzhang.zhihu.service.vote.impl;

import com.jiuzhang.zhihu.entity.VoteStats;
import com.jiuzhang.zhihu.service.IVoteStatsService;
import com.jiuzhang.zhihu.service.vote.VoteStatsCacheService;
import com.jiuzhang.zhihu.util.SetUtil;
import com.jiuzhang.zhihu.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.jiuzhang.zhihu.constant.Constants.ANSWER_VOTER_SET;
import static com.jiuzhang.zhihu.constant.Constants.ANSWER_VOTE_COUNT;

@Service
public class VoteStatsCacheServiceImple implements VoteStatsCacheService {

    @Autowired
    private IVoteStatsService voteStatsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void loadVoteStats(Long answerId, int type) {
        String keyCnt = ANSWER_VOTE_COUNT + answerId + type;
        String keySet = ANSWER_VOTER_SET + answerId + type;

        boolean hasCnt = redisTemplate.hasKey(keyCnt);

        // 注：如果 计数器 或 投票者集合 没有加载入cache，执行加载
        if (!hasCnt) {
            VoteStats voteStats = voteStatsService.queryByCondition(answerId, type);
            redisTemplate.opsForValue().set(keyCnt, voteStats.getVoteCount());

            if (StringUtil.isNotBlank(voteStats.getVoteUsers())) {
                Set<String> deserialized = SetUtil.deserialize(voteStats.getVoteUsers());
                redisTemplate.opsForSet().add(keySet, deserialized);
            }
        }
    }
}
