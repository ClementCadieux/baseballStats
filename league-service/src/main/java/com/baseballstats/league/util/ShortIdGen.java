package com.baseballstats.league.util;

import org.apache.commons.lang3.RandomStringUtils;

public class ShortIdGen {

    private static final int LENGTH_ID = 9;

    public static Integer getShortId() {
        String shortIdString = RandomStringUtils.randomNumeric(LENGTH_ID);

        Integer shortId = Integer.valueOf(shortIdString);
        return shortId;
    }
}
