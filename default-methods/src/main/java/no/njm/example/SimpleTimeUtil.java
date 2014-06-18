package no.njm.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SimpleTimeUtil implements SimpleTime {

    private LocalDateTime localDateTime;

    public SimpleTimeUtil() {
        localDateTime = LocalDateTime.now();
    }

    @Override
    public void setTime(int hour, int minute, int second) {
        LocalDate date = LocalDate.from(localDateTime);
        LocalTime time = LocalTime.of(hour, minute, second);
        localDateTime = LocalDateTime.of(date, time);
    }

    @Override
    public void setDate(int day, int month, int year) {
        LocalDate date = LocalDate.of(year, month, day);
        LocalTime time = LocalTime.from(localDateTime);
        localDateTime = LocalDateTime.of(date, time);
    }

    @Override
    public LocalDateTime currentLocalDateTime() {
        return localDateTime;
    }
}
