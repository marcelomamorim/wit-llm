/*
 * @test
 * @summary Test for IsoChronology.date(TemporalAccessor)
 * @run main IsoChronologyWitupTest
 */

import java.time.chrono.IsoChronology;
import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;

public class IsoChronologyWitupTest {

    public static void main(String[] args) {
        testDateWithNull();
        testDateWithValidTemporal();
    }

    private static void testDateWithNull() {
        try {
            IsoChronology.instance().date(null);
            System.out.println("FAIL: Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null input");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }

    private static void testDateWithValidTemporal() {
        try {
            // Using LocalDate as a valid TemporalAccessor
            LocalDate date = LocalDate.of(2023, 3, 15);
            LocalDate result = IsoChronology.instance().date(date);
            if (result.equals(date)) {
                System.out.println("PASS: Valid TemporalAccessor returns correct LocalDate");
            } else {
                System.out.println("FAIL: Valid TemporalAccessor did not return expected LocalDate");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
