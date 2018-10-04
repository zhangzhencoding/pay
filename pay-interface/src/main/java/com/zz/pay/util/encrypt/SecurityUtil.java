package com.zz.pay.util.encrypt;

import java.util.*;
import java.util.Map.Entry;

public class SecurityUtil {
    public SecurityUtil() {
    }

    public static String authentication(Map<String, Object> srcData) {
        if (null == srcData) {
            throw new IllegalArgumentException("请输入要加密的内容");
        } else {
            List<Entry<String, Object>> list = new ArrayList(srcData.entrySet());
            Collections.sort(list, new Comparator<Entry<String, Object>>() {
                public int compare(Entry<String, Object> o1, Entry<String, Object> o2) {
                    return ((String)o1.getKey()).compareTo((String)o2.getKey());
                }
            });
            StringBuffer srcSb = new StringBuffer();
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                Entry<String, Object> srcAtom = (Entry)var3.next();
                srcSb.append(String.valueOf(srcAtom.getValue()));
            }

            System.out.println("身份验证加密前字符串：" + srcSb.toString());
            String token = MD5Util.MD5(srcSb.toString());
            return token;
        }
    }
}