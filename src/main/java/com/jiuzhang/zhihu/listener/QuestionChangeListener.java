package com.jiuzhang.zhihu.listener;

import com.jiuzhang.zhihu.entity.event.QuestionAddedEvent;
import com.jiuzhang.zhihu.entity.event.QuestionGenericEvent;
import com.jiuzhang.zhihu.entity.event.QuestionRemovedEvent;
import com.jiuzhang.zhihu.entity.event.QuestionUpdatedEvent;
import com.jiuzhang.zhihu.service.search.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 问题更新处理事件监听器
 */
@Slf4j
@Component
public class QuestionChangeListener implements ApplicationListener<QuestionGenericEvent> {

    @Autowired
    private IndexService indexService;

    @Override
    public void onApplicationEvent(QuestionGenericEvent event) {
        if (event instanceof QuestionAddedEvent) {
            this.onQuestionAddedEvent((QuestionAddedEvent)event);
        } else if (event instanceof QuestionUpdatedEvent) {
            this.onQuestionUpdatedEvent((QuestionUpdatedEvent)event);
        } else if (event instanceof QuestionRemovedEvent) {
            this.onQuestionRemovedEvent((QuestionRemovedEvent)event);
        }
    }

    private void onQuestionAddedEvent(QuestionAddedEvent event) {
        indexService.index((Long) event.getSource());
    }

    private void onQuestionUpdatedEvent(QuestionUpdatedEvent event) {
        indexService.index((Long) event.getSource());
    }

    private void onQuestionRemovedEvent(QuestionRemovedEvent event) {
        indexService.delete((Long) event.getSource());
    }
}
