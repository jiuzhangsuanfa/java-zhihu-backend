package com.jiuzhang.zhihu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jiuzhang.zhihu.entity.vo.QuestionVO;
import com.jiuzhang.zhihu.service.ISearchService;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements ISearchService {

    @Override
    public IPage<QuestionVO> selectQuestionPage(IPage<QuestionVO> page, QuestionVO question) {
        return null;
    }
}
