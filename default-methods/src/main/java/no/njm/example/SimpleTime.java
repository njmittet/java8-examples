package no.njm.example;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Default methods enable you to add new functionality to the interfaces of your
 * libraries and ensure binary compatibility with code written (implementing)
 * for older versions of those interfaces.
 */
public interface SimpleTime {

    /**
     * Static methods is added to Java 8 interfaces in order to assist default methods
     * and reduce the need for utility classes.
     *
     * @param zoneId A string identifying the time zone, eg. "Asia/Tokyo"
     * @return A ZoneId instance
     */
    static ZoneId getZoneId(String zoneId) {
        try {
            return ZoneId.of(zoneId);
        } catch (DateTimeException e) {
            return ZoneId.systemDefault();
        }
    }

    void setTime(int hour, int minute, int second);

    void setDate(int day, int month, int year);

    public LocalDateTime currentLocalDateTime();

    default ZonedDateTime zonedDateTime(String zoneString) {
        return ZonedDateTime.of(currentLocalDateTime(), getZoneId(zoneString));
    }
}
