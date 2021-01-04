package com.jiuzhang.zhihu.service.vote.impl;

import com.jiuzhang.zhihu.entity.VoteStats;
import com.jiuzhang.zhihu.service.IVoteStatsService;
import com.jiuzhang.zhihu.service.vote.VoteStatsCacheService;
import com.jiuzhang.zhihu.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
        boolean hasSet = redisTemplate.hasKey(keySet);

        // 注：如果
        if (!hasSet || !hasCnt) {
            VoteStats voteStats = voteStatsService.queryByCondition(answerId, type);
            redisTemplate.opsForValue().set(keyCnt, voteStats.getVoteCount());

//            if () {
                redisTemplate.opsForSet().add(hasSet, StringUtil.splitTrim(voteStats.getVoteUsers(), ",") );
//            }
        }
    }
}
