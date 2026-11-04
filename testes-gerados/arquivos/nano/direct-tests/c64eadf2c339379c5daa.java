/* @test    @summary Test OffsetDateTime.parse with custom formatter    @run main OffsetDateTimeWitupTest */

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testParseWithFormatter();
    }

    public static void testParseWithFormatter() {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd HH:mm:ss")
            .appendOffset("Z", "+00:00")
            .toFormatter();
        String dateStr = "2023-03-15 12:30:45+02:00";
        OffsetDateTime odt = OffsetDateTime.parse(dateStr, formatter);
        assert odt.getYear() == 2023 : "Year mismatch";
        assert odt.getMonthValue() == 3 : "Month mismatch";
        assert odt.getDayOfMonth() == 15 : "Day mismatch";
        assert odt.getHour() == 12 : "Hour mismatch";
        assert odt.getMinute() == 30 : "Minute mismatch";
        assert odt.getSecond() == 45 : "Second mismatch";
        assert odt.getOffset().getTotalSeconds() == 7200 : "Offset seconds mismatch";
    }
}
