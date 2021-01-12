package com.jiuzhang.zhihu.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperationTypeEnum {

    CREATE("创建", 1),
    MODIFY("更新", 2),
    REMOVE("删除", 3),
    ;

    final String name;

    final int code;

    /**
     * 匹配枚举值
     *
     * @param name 名称
     * @return OssEnum
     */
    public static OperationTypeEnum of(String name) {
        if (name == null) {
            return null;
        }
        OperationTypeEnum[] values = OperationTypeEnum.values();
        for (OperationTypeEnum ossEnum : values) {
            if (ossEnum.name.equals(name)) {
                return ossEnum;
            }
        }
        return null;
    }

}
