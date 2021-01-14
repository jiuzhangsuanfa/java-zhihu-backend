package com.jiuzhang.zhihu.entity.event;

import org.springframework.context.ApplicationEvent;

public class QuestionGenericEvent extends ApplicationEvent {

    private int operation;

    public QuestionGenericEvent(Object source) {
        super(source);
    }

    public QuestionGenericEvent(Object source, int operation) {
        super(source);
        this.operation = operation;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }
}
