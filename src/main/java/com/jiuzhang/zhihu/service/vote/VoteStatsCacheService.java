package com.jiuzhang.zhihu.service.vote;

public interface VoteStatsCacheService {

    void loadVoteStats(Long answerId, int type);
}
