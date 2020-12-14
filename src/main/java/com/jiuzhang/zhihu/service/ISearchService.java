package com.jiuzhang.zhihu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jiuzhang.zhihu.entity.vo.QuestionVO;

public interface ISearchService {

    /**
     * 自定义分页
     *
     * @param page
     * @param question
     * @return
     */
    IPage<QuestionVO> selectQuestionPage(IPage<QuestionVO> page, QuestionVO question);
}
