package com.jiuzhang.zhihu.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VoteTypeEnum {

    UPVOTE("赞", 1),
    DOWNVOTE("踩", 2)
    ;

    final String name;

    final int type;

    /**
     * 匹配枚举值
     *
     * @param name 名称
     * @return OssEnum
     */
    public static VoteTypeEnum of(String name) {
        if (name == null) {
            return null;
        }
        VoteTypeEnum[] values = VoteTypeEnum.values();
        for (VoteTypeEnum ossEnum : values) {
            if (ossEnum.name.equals(name)) {
                return ossEnum;
            }
        }
        return null;
    }

}
