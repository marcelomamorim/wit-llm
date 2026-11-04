/* @test    @summary Test HijrahChronology.date(int, int, int) method for valid and invalid inputs    @run main HijrahChronologyWitupTest */

import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;

public class HijrahChronologyWitupTest {

    public static void main(String[] args) {
        testValidDate();
        testInvalidMonth();
        testInvalidDay();
    }

    static void testValidDate() {
        HijrahChronology chrono = HijrahChronology.INSTANCE;
        HijrahDate date = chrono.date(1443, 9, 15);
        assert date != null : "Valid date should not be null";
        assert date.getEra().getValue() == 1 : "Era value should be 1";
        assert date.getProlepticYear() == 1443 : "Proleptic year mismatch";
        assert date.getMonthValue() == 9 : "Month mismatch";
        assert date.getDayOfMonth() == 15 : "Day of month mismatch";
    }

    static void testInvalidMonth() {
        HijrahChronology chrono = HijrahChronology.INSTANCE;
        try {
            chrono.date(1443, 13, 10);
            assert false : "Expected DateTimeException for invalid month";
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    static void testInvalidDay() {
        HijrahChronology chrono = HijrahChronology.INSTANCE;
        try {
            chrono.date(1443, 9, 32);
            assert false : "Expected DateTimeException for invalid day";
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }
}
