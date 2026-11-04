/* @test    @summary Test for OffsetDateTime.isBefore method handling null input and normal comparison    @run main OffsetDateTimeWitupTest */

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testIsBeforeWithNull();
        testIsBeforeWithValidDates();
    }

    static void testIsBeforeWithNull() {
        OffsetDateTime dateTime = OffsetDateTime.of(2023, 3, 15, 12, 0, 0, 0, ZoneOffset.UTC);
        try {
            dateTime.isBefore(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testIsBeforeWithValidDates() {
        OffsetDateTime dt1 = OffsetDateTime.of(2023, 3, 15, 12, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime dt2 = OffsetDateTime.of(2023, 3, 15, 12, 0, 0, 1, ZoneOffset.UTC);
        OffsetDateTime dt3 = OffsetDateTime.of(2023, 3, 15, 11, 59, 59, 999999999, ZoneOffset.UTC);
        OffsetDateTime dt4 = OffsetDateTime.of(2023, 3, 15, 12, 0, 0, 0, ZoneOffset.ofHours(1));

        // dt1 is before dt2
        if (dt1.isBefore(dt2)) {
            System.out.println("PASS: dt1 is before dt2");
        } else {
            System.out.println("FAIL: dt1 should be before dt2");
        }

        // dt2 is not before dt1
        if (!dt2.isBefore(dt1)) {
            System.out.println("PASS: dt2 is not before dt1");
        } else {
            System.out.println("FAIL: dt2 should not be before dt1");
        }

        // dt3 is before dt1
        if (dt3.isBefore(dt1)) {
            System.out.println("PASS: dt3 is before dt1");
        } else {
            System.out.println("FAIL: dt3 should be before dt1");
        }

        // dt4 is not before dt1 (different offset)
        if (!dt4.isBefore(dt1)) {
            System.out.println("PASS: dt4 is not before dt1");
        } else {
            System.out.println("FAIL: dt4 should not be before dt1");
        }
    }
}
