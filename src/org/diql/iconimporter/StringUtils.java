package org.diql.iconimporter;

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
}
