package com.jiuzhang.zhihu.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VoteActionEnum {

    SUBMIT("提交", 1),
    CANCEL("取消", 0)
    ;

    final String name;

    final int category;

    /**
     * 匹配枚举值
     *
     * @param name 名称
     * @return OssEnum
     */
    public static VoteActionEnum of(String name) {
        if (name == null) {
            return null;
        }
        VoteActionEnum[] values = VoteActionEnum.values();
        for (VoteActionEnum vaEnum : values) {
            if (vaEnum.name.equals(name)) {
                return vaEnum;
            }
        }
        return null;
    }

}
