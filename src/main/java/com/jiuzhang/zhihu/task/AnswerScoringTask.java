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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 按投票计数对计算所有答案的排名
 * @Author 九章算法
 */
@Slf4j
@Component
public class AnswerScoringTask {

    public static final int SIZE = 50;

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

        int count = 0;
        int current = 0;
        while (true) {
            IPage<Question> questions = queryQuestionPage(current);
            if (questions.getSize() <= 0) {
                break;
            }

            // 计算 各问题所属答案的评分
            for (Question q : questions.getRecords()) {
                List<Answer> answers = queryAnswerList(q.getId());
                for (Answer answer : answers) {
                    if (score(answer) ) count++;
                }
            }

            current++;
        }

        log.info("answer score计算结束，重新计算" +count+ "条问题的评分。");
    }


    /**
     * 计算答案评分
     * @param answer
     */
    private boolean score(Answer answer) {
        int upVoteCount = voteService.getCount(answer.getId(), VoteTypeEnum.UPVOTE.getType());
        int downVoteCount = voteService.getCount(answer.getId(), VoteTypeEnum.DOWNVOTE.getType());

        double score = scoreAlgorithm.score(upVoteCount, upVoteCount+downVoteCount);
        answer.setScore(score);
        return answerService.updateById(answer);
    }

    // ------------------------------- 工具方法 -------------------------------
    private IPage<Question> queryQuestionPage(int current) {
        IPage<Question> page = new Page<>(current, SIZE);
        return questionService.page(page, new QueryWrapper<>(new QuestionVO()));
    }

    private List<Answer> queryAnswerList(Long questionId) {
        Answer answerQuery = new Answer();
        answerQuery.setQuestionId(questionId);
        return answerService.list(new QueryWrapper<>(answerQuery));
    }

}
