package com.jiuzhang.zhihu.listener;

import com.jiuzhang.zhihu.common.enums.OperationTypeEnum;
import com.jiuzhang.zhihu.entity.dto.QuestionDTO;
import com.jiuzhang.zhihu.entity.event.QuestionChangeEvent;
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
public class QuestionChangeListener implements ApplicationListener<QuestionChangeEvent> {

    @Autowired
    private IndexService indexService;

    @Override
    public void onApplicationEvent(QuestionChangeEvent event) {
        int ops = event.getOperation();
        Long qid = (Long) event.getSource();

        if (OperationTypeEnum.CREATE.getCode() == ops
            || OperationTypeEnum.MODIFY.getCode() == ops) {
            indexService.index(qid);

        } else if (OperationTypeEnum.REMOVE.getCode() == ops) {
            indexService.delete(qid);

        } else {
            log.error("invalid operation event type: " + ops);
        }
    }
}
