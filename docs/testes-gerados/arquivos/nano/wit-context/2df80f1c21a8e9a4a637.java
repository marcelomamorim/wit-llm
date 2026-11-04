/* @test    @summary Tests for OffsetDateTime.toEpochSecond() method.
 * @run main OffsetDateTimeWitupTest
 */

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.LocalDate;
import java.time.LocalTime;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testToEpochSecondWithValidOffset();
        testToEpochSecondWithNullOffset();
    }

    private static void testToEpochSecondWithValidOffset() {
        try {
            OffsetDateTime odt = OffsetDateTime.of(LocalDate.of(2023, 3, 15), LocalTime.of(12, 0), ZoneOffset.ofHours(2));
            long epochSecond = odt.toEpochSecond();
            System.out.println("testToEpochSecondWithValidOffset: " + epochSecond);
        } catch (Exception e) {
            System.err.println("testToEpochSecondWithValidOffset failed: " + e);
        }
    }

    private static void testToEpochSecondWithNullOffset() {
        try {
            OffsetDateTime odt = OffsetDateTime.of(LocalDate.of(2023, 3, 15), LocalTime.of(12, 0), null);
            long epochSecond = odt.toEpochSecond();
            System.err.println("testToEpochSecondWithNullOffset: Expected exception, but got: " + epochSecond);
        } catch (NullPointerException e) {
            System.out.println("testToEpochSecondWithNullOffset: Caught expected NullPointerException");
        } catch (Exception e) {
            System.err.println("testToEpochSecondWithNullOffset: Caught unexpected exception: " + e);
        }
    }
}
