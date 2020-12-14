package com.jiuzhang.zhihu.entity.event;

import org.springframework.context.ApplicationEvent;

public class QuestionChangeEvent extends ApplicationEvent {

    private Long questionId;

    public QuestionChangeEvent(Object source) {
        super(source);
    }

    public QuestionChangeEvent(Object source, Long questionId) {
        super(source);
        this.questionId = questionId;
    }

}
