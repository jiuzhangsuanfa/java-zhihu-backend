package com.jiuzhang.zhihu.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SetUtil {

    public static String serialize(Set<String> elems) {
        return String.join(",", elems);
    }

    public static Set<String> deserialize(String str) {
        String[] arr = StringUtil.splitTrim(str, ",");
        return new HashSet<>(Arrays.asList(arr));
    }
}
