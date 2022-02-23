package com.registerWithJWT.registerWithJWT.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeProvider {


    private static final DateTimeFormatter SIMPLE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter SIMPLE_DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter SIMPLE_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    private DateTimeProvider() {
        // prohibit instantiation
    }

    public static ZonedDateTime currentSystemTime() {
        return ZonedDateTime.now(ZoneId.systemDefault());
    }

    public static ZonedDateTime currentProgramTime() {
        return ZonedDateTime.now(ZoneId.systemDefault());
    }

    public static ZonedDateTime parseDate(final String dateString) {
        final LocalDate simpleDate = LocalDate.parse(dateString, SIMPLE_DATE_FORMAT);
        return simpleDate.atStartOfDay(ZoneId.systemDefault());
    }

    public static ZonedDateTime parseDateTime(final String dateTimeString) {
        final LocalDateTime simpleDateTime = LocalDateTime.parse(dateTimeString, SIMPLE_DATETIME_FORMAT);
        return ZonedDateTime.of(simpleDateTime, ZoneId.systemDefault());
    }

    public static String formatDate(final ZonedDateTime zonedDateTime) {
        return zonedDateTime.format(SIMPLE_DATE_FORMAT);
    }

    public static String formatTime(final ZonedDateTime zonedDateTime) {
        return zonedDateTime.format(SIMPLE_TIME_FORMAT);
    }

    public static String formatDateTime(final ZonedDateTime zonedDateTime) {
        return zonedDateTime.format(SIMPLE_DATETIME_FORMAT);
    }

    public static String formatDateTime(final ZonedDateTime zonedDateTime, DateTimeFormatter formatter) {
        return zonedDateTime.format(formatter);
    }
}
