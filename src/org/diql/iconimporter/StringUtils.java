package org.diql.iconimporter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by diql on 2016/12/9.
 */
public class StringUtils {

    public static boolean isEmpty(String str) {
        if (str == null || str.trim().equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 过滤所有非法字符.
     * @param source
     * @return
     */
    public static String reginToResourceName(String source) {
        if (isEmpty(source)) {
            return source;
        }
        String target = source.trim().toLowerCase();
        // 只留下小写字母，数字，下划线，首字符不为数字；
        // 先去除所有，小写字母，数字，下划线以外的字符；
        target = target.replaceAll("[^a-z0-9_]", "");
        // 检查是否为数字开头；
        if (target.matches("[0-9]+.*")) {
            Pattern p = Pattern.compile("[0-9]+");
            Matcher m = p.matcher(target);
            target = m.replaceFirst("");
        }
        return target;
    }

    public static void main(String[] args) {
        String test = "1123----hi bH%&*Hnglaoeg\nt2qt3hibg24_32thoiansjkoFNLAKDFIW";
        System.out.println("target:" + reginToResourceName(test));
    }
}
