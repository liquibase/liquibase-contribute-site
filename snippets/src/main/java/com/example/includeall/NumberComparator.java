package com.example.includeall;

import java.util.Comparator;

public class NumberComparator implements Comparator<String> {

    @Override
    public int compare(String path1, String path2) {
        Integer path1Number = extractFileNumber(path1);
        Integer path2Number = extractFileNumber(path2);

        return path1Number.compareTo(path2Number);
    }

    private Integer extractFileNumber(String path1) {
        return Integer.valueOf(path1.replaceFirst("-.*", ""));
    }
}
