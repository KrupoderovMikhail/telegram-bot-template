package com.KrupoderovMikhail.github.telegrambot.utils;

/**
 * @author Krupoderov Mikhail
 * @version 1.0
 */
public class StringUtils {
    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}
