package no.njm.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;

public class DefaultMethods {

    private static final Logger log = LoggerFactory.getLogger(DefaultMethods.class);
    private static final DateTimeFormatter READABLE_FORMAT;

    static {
        READABLE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    }

    public static void main(String[] args) {
        SimpleTime timeUtil = new SimpleTimeUtil();
        log.debug("Initial LocalDateTime is {}", timeUtil.currentLocalDateTime());

        timeUtil.setDate(25, 6, 2014);
        log.debug("Changed LocalDate to {}", timeUtil.currentLocalDateTime());

        timeUtil.setTime(22, 10, 30);
        log.debug("Changed LocalTime to {}", timeUtil.currentLocalDateTime());

        log.debug("Zoned ISO 8601 LocalDateTime is {}", timeUtil.zonedDateTime("Asia/Tokyo"));

        log.debug("Formatted LocalDateTime is {}", timeUtil.currentLocalDateTime().format(READABLE_FORMAT));
    }
}
