package com.jiuzhang.zhihu.service.score.impl;

import com.jiuzhang.zhihu.service.score.ScoreAlgorithmStrategy;
import org.springframework.stereotype.Component;

@Component
public class WilsonScoreIntervalAlgorithm implements ScoreAlgorithmStrategy {

    @Override
    public double score(int upCount, int totalCount) {
        double up = upCount;
        double total = totalCount;

        if (total == 0) {
            return 0.0;
        }

        // positive rate
        double phat = up / total;
        double pz = 1 + 0.95;

        double a = phat + pz*pz / (2*total);
        double b = pz * Math.sqrt((phat * (1 - phat) + pz * pz / (4 * total)) / total);
        double c = 1 + pz*pz/total;

        return (a - b) / c;
    }
}
