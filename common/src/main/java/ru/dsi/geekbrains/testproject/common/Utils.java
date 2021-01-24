package ru.dsi.geekbrains.testproject.common;

public class Utils {
    public static Long toLongOrNull(String value){
        Long result = null;
        try {
            result = Long.valueOf(value);
        }catch (NumberFormatException ignore){}
        return result;
    }

    public static Integer toIntegerOrNull(String value){
        Integer result = null;
        try {
            result = Integer.valueOf(value);
        }catch (NumberFormatException ignore){}
        return result;
    }
}
