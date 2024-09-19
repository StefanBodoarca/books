package com.ro.util;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

public class Helper {

    public static Date tomorrow() {
        return DateUtils.addDays(new Date(), 1);
    }
}
