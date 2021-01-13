package com.jiuzhang.zhihu.service.vote;

import com.jiuzhang.zhihu.entity.vo.VoteVO;

import java.util.Set;

public interface CacheService {

    // ------------------------ 1. 被投票的答案 ------------------------
    Set<Long> getAnsweredIds(int type);

    boolean addVotedAnswerId(Long answerId, int type);


    // ------------------------ 2. 投票统计 ------------------------
    boolean isVoteStatsLoaded(Long answerId, int type);

    void clearVoteStats(Long answerId, int type);

    void loadVoteStats(Long answerId, int type);

    // ------------------------ 2.1 答案票数 ------------------------
    int getCount(Long answerId, int voteType);

    boolean incrCount(Long answerId, int type);

    boolean decrCount(Long answerId, int type);

    // ------------------------ 2.2 答案投票者 ------------------------
    Set<String> getVoteUserSet(Long answerId, int type);

    boolean alreadyVoted(Long answerId, int type, String userId);

    boolean addVoteUser(Long answerId, int type, String userId);

    boolean removeVoteUser(Long answerId, int type, String userId);

}
