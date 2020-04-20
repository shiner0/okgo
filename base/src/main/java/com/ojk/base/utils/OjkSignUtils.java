package com.ojk.base.utils;

import com.lzy.okgo.model.HttpParams;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class OjkSignUtils {

    static Random random = new Random();

    public static void signature(HttpParams params) {
        params.put("noncestr", getRndStr(6 + random.nextInt(8)));
        Map<String, String> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : params.urlParamsMap.entrySet()) {
            map.put(entry.getKey(), entry.getValue().get(0));
        }
        for (Map.Entry<String, String> entry : getRankKey(map).entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        sb.delete(sb.length() - 1, sb.length());
        params.put("sign", OjkMd5Utils.Md5(sb.toString() + "&Bsa="+ OjkCommonUtils.signature).toUpperCase());
    }

    public static String getRndStr(int length) {
        StringBuilder sb = new StringBuilder();
        char ch;
        for (int i = 0; i < length; i++) {
            ch = OjkCommonUtils.randomNum.charAt(random.nextInt(OjkCommonUtils.randomNum.length()));
            sb.append(ch);
        }
        return sb.toString();
    }

    public static Map<String, String> getRankKey(Map<String, String> map) {
        Comparator<String> comparator = (lhs, rhs) -> lhs.compareTo(rhs);
        Map<String, String> treeMap = new TreeMap<>(comparator);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (!OjkTextUtils.isNull(entry.getValue())) {
                treeMap.put(entry.getKey(), entry.getValue());
            }
        }
        return treeMap;
    }


}
