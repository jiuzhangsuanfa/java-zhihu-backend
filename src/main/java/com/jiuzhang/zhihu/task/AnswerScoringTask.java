package com.jiuzhang.zhihu.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiuzhang.zhihu.common.enums.VoteTypeEnum;
import com.jiuzhang.zhihu.entity.Answer;
import com.jiuzhang.zhihu.entity.Question;
import com.jiuzhang.zhihu.entity.vo.QuestionVO;
import com.jiuzhang.zhihu.service.IAnswerService;
import com.jiuzhang.zhihu.service.IQuestionService;
import com.jiuzhang.zhihu.service.score.ScoreAlgorithmStrategy;
import com.jiuzhang.zhihu.service.vote.IVoteStrategyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 按投票计数对计算所有答案的排名
 * @Author 九章算法
 */
@Slf4j
@Component
public class AnswerScoringTask {

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private IAnswerService answerService;

    @Autowired
    private IVoteStrategyService voteService;

    @Autowired
    private ScoreAlgorithmStrategy scoreAlgorithm;


//    @Scheduled(cron="*/10 * * * * ?")
    public void execute() {
        log.info("开始执行answer排名");

        int current = 0, size = 50;
        QuestionVO question = new QuestionVO();
        while (true) {
            IPage<QuestionVO> page = questionService.selectQuestionPage(new Page<>(current, size), question);
            if (page.getSize() <= 0) {
                break;
            }

            for (Question record : page.getRecords()) {
                QueryWrapper qw = new QueryWrapper();
                List<Answer> answers = answerService.list(qw);
                for (Answer answer : answers) {
                    score(answer);
                }
            }
        }

        log.info("answer排名结束");
    }

    private void score(Answer answer) {
        int upVoteCount = voteService.getCount(answer.getId(), VoteTypeEnum.UPVOTE.getType());
        int downVoteCount = voteService.getCount(answer.getId(), VoteTypeEnum.DOWNVOTE.getType());

        double score = scoreAlgorithm.score(upVoteCount, upVoteCount+downVoteCount);
        answer.setScore(score);
        answerService.updateById(answer);
    }

}
