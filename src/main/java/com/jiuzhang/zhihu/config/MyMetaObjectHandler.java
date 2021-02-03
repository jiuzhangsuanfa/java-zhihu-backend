package com.jiuzhang.zhihu.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // version设置初始值为1，之后每修改一次+1
        this.setFieldValByName("version",1,metaObject);   
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        
    }
}