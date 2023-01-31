package com.here.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Configuration
public class DateTimeConfig {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

//    @Bean
//    public Formatter<LocalDate> localDateFormatter() {
//        return new Formatter<LocalDate>() {
//            @Override
//            public LocalDate parse(String text, Locale locale) {
//                return LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyyMMdd", locale));
//            }
//
//            @Override
//            public String print(LocalDate object, Locale locale) {
//                return DateTimeFormatter.ISO_DATE.format(object);
//            }
//        };
//    }
//
//
//    @Bean
//    public Formatter<LocalDateTime> localDateTimeFormatter() {
//        return new Formatter<LocalDateTime>() {
//            @Override
//            public LocalDateTime parse(String text, Locale locale) {
//                return LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyyMMddHHmmss", locale));
//            }
//
//            @Override
//            public String print(LocalDateTime object, Locale locale) {
//                return DateTimeFormatter.ISO_DATE_TIME.format(object);
//            }
//        };
//    }

    @Bean
    public Formatter<Date> DateFormatter() {
        return new Formatter<Date>() {
            @Override
            public Date parse(String text, Locale locale) throws ParseException {
                SimpleDateFormat dt = new SimpleDateFormat(DATE_TIME_FORMAT, locale);
                return dt.parse(text);
            }

            @Override
            public String print(Date object, Locale locale) {
                return new SimpleDateFormat(DATE_TIME_FORMAT).format(object);
            }
        };
    }
}
