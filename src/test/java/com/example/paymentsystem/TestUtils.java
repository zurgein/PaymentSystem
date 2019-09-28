package com.example.paymentsystem;

import java.util.Calendar;
import java.util.Date;

public class TestUtils {

    public static Date createFutureDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);

        return calendar.getTime();
    }
}
