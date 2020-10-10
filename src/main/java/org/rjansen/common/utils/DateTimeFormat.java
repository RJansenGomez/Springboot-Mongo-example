package org.rjansen.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFormat {
    private DateTimeFormat() {

    }

    public static final String ZULU_DATE_PATTERN = "dd/MM/yyyy'T'HH:mm:ss:SS'Z'";
    public static final String FILTER_DATE_TIME = "dd/MM/yyyy HH:mm:ss";
    public static final String FILTER_DATE = "dd/MM/yyyy";

    /**
     * Accepts pattern dd/MM/yyyy HH:mm:ss
     * @param date with dd/MM/yyyy HH:mm:ss
     * @return LocalDateTime of the {@param date}
     */
    public static LocalDateTime parseStringToLocalDateTime(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(FILTER_DATE_TIME));
    }

    /**
     * Converts a Local date to String
     * @param date
     * @return String formatted to dd/MM/yyyy HH:mm:ss
     */
    public static String parseLocalDateTimeToString(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern(FILTER_DATE_TIME));
    }

    /**
     * Accepts pattern dd/MM/yyyy
     * @param date with dd/MM/yyyy
     * @return LocalDateTime of the {@param date}
     */
    public static LocalDate parseStringToLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(FILTER_DATE));
    }

    /**
     * Converts a Local date to String
     * @param date
     * @return String formatted to dd/MM/yyyy
     */
    public static String parseLocalDateToString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(FILTER_DATE));
    }

    /**
     * Converts a Local date time  to String
     * @param date
     * @return String formatted to dd/MM/yyyy'T'HH:mm:ss:SS'Z'
     */
    public static String parseLocalDateTimeToZuluString(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern(ZULU_DATE_PATTERN));
    }

    /**
     * Accepts pattern dd/MM/yyyy'T'HH:mm:ss:SS'Z'
     * @param date with dd/MM/yyyy'T'HH:mm:ss:SS'Z'
     * @return LocalDateTime of the {@param date}
     */
    public static LocalDateTime parseZuluStringToLocalDateTime(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(ZULU_DATE_PATTERN));
    }
}
