package com.shoppinglist.util;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringUtil {

    public static String toTitleCase(String inputString) {

        if (inputString.isEmpty())
            return "";

        String[] newString = inputString.split("\\s");

        return Arrays.stream(newString)
                .map(s -> {
                    if (s.length() <= 1)
                        return s.toUpperCase();
                    return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
                })
                .collect(Collectors.joining(" "));
    }
}
