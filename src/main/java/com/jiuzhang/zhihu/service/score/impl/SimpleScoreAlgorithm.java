package com.jiuzhang.zhihu.service.score.impl;

import com.jiuzhang.zhihu.service.score.ScoreAlgorithmStrategy;

public class SimpleScoreAlgorithm implements ScoreAlgorithmStrategy {

    @Override
    public double score(int upCount, int totalCount) {
        double up = upCount;
        double total = totalCount;

        return up / total;
    }
}
