package org.homecorporation.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeUtil {
    public static final ZoneId ZONE_ID_KYIV = ZoneId.of("Europe/Kyiv");

    public static ZonedDateTime getDateTimeAt(ZoneId zoneId) {
        return Instant.now().atZone(zoneId);
    }
}
