package com.jiuzhang.zhihu.listener;

import com.jiuzhang.zhihu.entity.event.QuestionChangeEvent;
import com.jiuzhang.zhihu.service.search.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class QuestionChangeListener implements ApplicationListener<QuestionChangeEvent> {

    @Autowired
    private IndexService indexService;

    @Override
    public void onApplicationEvent(QuestionChangeEvent event) {
        Object source = event.getSource();
        indexService.index(10L);
    }
}
