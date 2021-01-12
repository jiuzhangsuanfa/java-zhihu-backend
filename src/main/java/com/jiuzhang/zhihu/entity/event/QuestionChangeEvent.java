package com.jiuzhang.zhihu.entity.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

public class QuestionChangeEvent extends ApplicationEvent {

    private int operation;

    public QuestionChangeEvent(Object source) {
        super(source);
    }

    public QuestionChangeEvent(Object source, int operation) {
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
