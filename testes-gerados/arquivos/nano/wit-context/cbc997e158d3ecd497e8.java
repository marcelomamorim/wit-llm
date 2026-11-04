/* @test    @summary Test for OffsetDateTime.toInstant() method to verify behavior and exception handling    @run main OffsetDateTimeWitupTest */

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.Instant;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testToInstantWithNullOffset();
        testToInstantWithValidOffset();
    }

    private static void testToInstantWithNullOffset() {
        OffsetDateTime odt = null;
        try {
            odt.toInstant();
            System.out.println("FAIL: Expected NullPointerException for null OffsetDateTime");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null OffsetDateTime");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testToInstantWithValidOffset() {
        OffsetDateTime odt = OffsetDateTime.of(2023, 3, 15, 12, 0, 0, 0, ZoneOffset.ofHours(2));
        try {
            Instant instant = odt.toInstant();
            if (instant != null) {
                System.out.println("PASS: toInstant() returned: " + instant);
            } else {
                System.out.println("FAIL: toInstant() returned null");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
