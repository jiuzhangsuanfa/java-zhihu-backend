package com.jiuzhang.zhihu.service.vote.helper.impl;

import com.jiuzhang.zhihu.entity.VoteStats;
import com.jiuzhang.zhihu.service.IVoteStatsService;
import com.jiuzhang.zhihu.service.vote.helper.CacheService;
import com.jiuzhang.zhihu.util.SetUtil;
import com.jiuzhang.zhihu.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IVoteStatsService voteStatsService;


    // ------------------------ 1. 被投票的答案 ------------------------
    @Override
    public Set<Long> getAnsweredIds(int type) {
        String votedAnswersKey = KeyBuilder.buildVotedAnswersKey(type);
        return redisTemplate.opsForSet().members(votedAnswersKey);
    }

    @Override
    public boolean addVotedAnswerId(Long answerId, int type) {
        return redisTemplate.opsForSet().add(KeyBuilder.buildVotedAnswersKey(type), answerId) > 0;
    }

    // ------------------------ 2. 投票统计 ------------------------
    @Override
    public boolean isVoteStatsLoaded(Long answerId, int type) {
        String key = KeyBuilder.buildVoteCountKey(answerId, type);
        return redisTemplate.hasKey(key);
    }

    @Override
    public void clearVoteStats(Long answerId, int type) {
        String votedAnswersKey = KeyBuilder.buildVotedAnswersKey(type);
        String countKey = KeyBuilder.buildVoteCountKey(answerId, type);
        String voterIdsKey = KeyBuilder.buildVoteUserSetKey(answerId, type);

        redisTemplate.opsForSet().remove(votedAnswersKey, answerId);
        redisTemplate.delete(countKey);
        redisTemplate.delete(voterIdsKey);
    }

    @Override
    public void loadVoteStats(Long answerId, int type) {

        boolean isLoaded = isVoteStatsLoaded(answerId, type);

        // 注：如果 投票统计 没有加载入cache，执行加载
        if (!isLoaded) {
            String keyCnt = KeyBuilder.buildVoteCountKey(answerId, type);
            String keySet = KeyBuilder.buildVoteUserSetKey(answerId, type);

            VoteStats voteStats = voteStatsService.queryByCondition(answerId, type);
            redisTemplate.opsForValue().set(keyCnt, voteStats.getVoteCount());

            if (StringUtil.isNotBlank(voteStats.getVoteUsers())) {
                Set<String> deserialized = SetUtil.deserialize(voteStats.getVoteUsers());
                redisTemplate.opsForSet().add(keySet, deserialized.toArray());
            }

        }
    }

    // ------------------------ 2.1 答案票数 ------------------------
    @Override
    public int getCount(Long answerId, int voteType) {
        String key = KeyBuilder.buildVoteCountKey(answerId, voteType);
        return (Integer) Optional.ofNullable( redisTemplate.opsForValue().get(key) ).orElse(0) ;
    }

    @Override
    public boolean incrCount(Long answerId, int type) {
        String key = KeyBuilder.buildVoteCountKey(answerId, type);
        return redisTemplate.opsForValue().increment(key) != null;
    }

    @Override
    public boolean decrCount(Long answerId, int type) {
        String key = KeyBuilder.buildVoteCountKey(answerId, type);
        return redisTemplate.opsForValue().decrement(key) != null;
    }

    // ------------------------ 2.2 答案投票者 ------------------------
    @Override
    public Set<String> getVoteUserSet(Long answerId, int type) {
        String keyUser = KeyBuilder.buildVoteUserSetKey(answerId, type);
        return redisTemplate.opsForSet().members(keyUser);
    }

    @Override
    public boolean alreadyVoted(Long answerId, int type, String userId) {
        String key = KeyBuilder.buildVoteUserSetKey(answerId, type);
        return redisTemplate.opsForSet().isMember(key, userId);
    }

    @Override
    public boolean addVoteUser(Long answerId, int type, String userId) {
        String key = KeyBuilder.buildVoteUserSetKey(answerId, type);
        return redisTemplate.opsForSet().add(key, userId) != null;
    }

    @Override
    public boolean removeVoteUser(Long answerId, int type, String userId) {
        String key = KeyBuilder.buildVoteUserSetKey(answerId, type);
        return redisTemplate.opsForSet().remove(key, userId) != null;
    }


    /**
     * Redis Key构造 内部类
     */
    public static class KeyBuilder {
        public static String buildVotedAnswersKey(int type) {
            return "zhihu:voted.answer.ids:" +type+ ":set";
        }

        public static String buildVoteCountKey(Long answerId, int type) {
            return "zhihu:answer.vote.count:" + answerId +":"+ type + ":int";
        }

        public static String buildVoteUserSetKey(Long answerId, int type) {
            return "zhihu:answer.voter.uids:" + answerId +":"+ type + ":set";
        }
    }
}
