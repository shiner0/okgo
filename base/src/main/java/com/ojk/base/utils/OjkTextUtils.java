package com.ojk.base.utils;

import androidx.annotation.Nullable;

public class OjkTextUtils {

    public static boolean isNull(@Nullable String str) {
        return str == null || str.length() == 0 || "null".equals(str);
    }

}
