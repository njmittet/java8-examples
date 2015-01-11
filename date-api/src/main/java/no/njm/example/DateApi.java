package no.njm.example;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The new java.time Date API is comparable with the Joda-Time library, but not the same.
 */
public class DateApi {

    private static final Logger log = LoggerFactory.getLogger(DateApi.class);

    public static void main(String[] args) {
        clock();
        timeZone();
        localTime();
        localDate();
        localDateTime();
    }

    /**
     * Clock provides access to the current date and time. Clocks are aware of a timezone and may be used instead of
     * System.currentTimeMillis() to retrieve the current milliseconds.
     * Such an instantaneous point on the time-line is also represented by the class Instant.
     */
    private static void clock() {
        Clock clock = Clock.systemDefaultZone();
        long millis = clock.millis();
        log.debug(String.valueOf(millis)); // Epoch
        Instant instant = clock.instant();
        Date legacyDate = Date.from(instant);   // Legacy java.util.Date
        log.debug("Old-style date is {}", legacyDate);
    }

    /**
     * Timezones are represented by a ZoneId.
     * Timezones define the offsets which are important to convert between
     * instants and local dates and times.
     */
    private static void timeZone() {
        ZoneId europe = ZoneId.of("Europe/Berlin");
        ZoneId brazil = ZoneId.of("Brazil/East");
        log.debug(europe.getRules().toString());
        log.debug(brazil.getRules().toString());
    }

    /**
     * LocalTime represents a time without a timezone, e.g. 10pm or 17:30:15.
     */
    private static void localTime() {
        ZoneId europe = ZoneId.of("Europe/Berlin");
        ZoneId brazil = ZoneId.of("Brazil/East");

        long hours = ChronoUnit.HOURS.between(LocalTime.now(europe), LocalTime.now(brazil));
        long minutes = ChronoUnit.MINUTES.between(LocalTime.now(europe), LocalTime.now(brazil));

        log.debug("Hours between {} and {} is {}", europe.getId(), brazil.getId(), hours);
        log.debug("Minutes between {} and {} is {}", europe.getId(), brazil.getId(), minutes);


        // LocalTime comes with various factory methods
        LocalTime late = LocalTime.of(23, 59, 59);
        log.debug("Default formatted LocalTime {}", late);

        // Formatting. DateTimeFormatter is immutable and thread-safe.
        DateTimeFormatter shortGerman = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
                                                         .withLocale(Locale.GERMAN);
        LocalTime early = LocalTime.parse("07:30", shortGerman);
        log.debug("German formatted LocalTime {}", early);
    }

    /**
     * LocalDate represents a distinct date, e.g. 2014-03-11. It's immutable and works like LocalTime.
     */
    private static void localDate() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
        LocalDate yesterday = tomorrow.minusDays(2);
        log.debug("Yesterday was the {}. day of the week", yesterday.getDayOfWeek().getValue());

        LocalDate nationalDay = LocalDate.of(2015, Month.MAY, 17);
        log.debug("17. mai is on a {}", nationalDay.getDayOfWeek()
                                                   .getDisplayName(TextStyle.FULL, Locale.ENGLISH));

        // Formatting
        DateTimeFormatter mediumGerman = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                                                          .withLocale(Locale.GERMAN);

        LocalDate christmas = LocalDate.parse("24.12.2014", mediumGerman);
        log.debug("German formatted christmas {}", christmas);
    }

    /**
     * LocalDateTime combines date and time.
     */
    private static void localDateTime() {
        LocalDateTime lastMinute = LocalDateTime.of(2014, Month.DECEMBER, 31, 23, 59, 59);

        DayOfWeek dayOfWeek = lastMinute.getDayOfWeek();
        log.debug("dayOfWeek is {}", dayOfWeek);

        Month month = lastMinute.getMonth();
        log.debug("month {}", month);

        long minuteOfDay = lastMinute.getLong(ChronoField.MINUTE_OF_DAY);
        System.out.println(minuteOfDay);
        log.debug("minuteOfDay is {}", minuteOfDay);

        // With the additional information of a timezone, a datetime can be converted to an instant
        Instant now = lastMinute.atZone(ZoneId.systemDefault())
                                .toInstant();
        log.debug("Epoch now is {}", now.toEpochMilli());

        // Formatting
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy - HH:mm");
        LocalDateTime parsed = LocalDateTime.parse("Nov 03, 2014 - 07:13", formatter);
        log.debug("parsed dateTime is {}", formatter.format(parsed));
    }
}
