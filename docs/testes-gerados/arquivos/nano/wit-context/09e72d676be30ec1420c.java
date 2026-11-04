/*
 * @test
 * @summary Test for FileTime.to(TimeUnit) method, including null argument handling.
 * @run main FileTimeWitupTest
 */

import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

public class FileTimeWitupTest {

    public static void main(String[] args) {
        testToWithNullUnit();
        testToWithValidUnitAndInstant();
        testToWithInstantConversion();
    }

    private static void testToWithNullUnit() {
        FileTime ft = FileTime.fromMillis(1000L);
        try {
            ft.to(null);
            System.err.println("FAIL: Expected NullPointerException for null unit");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null unit");
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception type: " + t);
        }
    }

    private static void testToWithValidUnitAndInstant() {
        long epochSeconds = 1609459200L; // 2021-01-01T00:00:00Z
        FileTime ft = FileTime.from(epochSeconds, TimeUnit.SECONDS);
        try {
            long result = ft.to(TimeUnit.SECONDS);
            if (result == epochSeconds) {
                System.out.println("PASS: to(TimeUnit.SECONDS) returns expected value");
            } else {
                System.err.println("FAIL: to(TimeUnit.SECONDS) returned " + result + ", expected " + epochSeconds);
            }
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testToWithInstantConversion() {
        // Create a FileTime with an instant, no explicit unit
        java.time.Instant instant = java.time.Instant.ofEpochSecond(12345L, 6789);
        // Use from(Instant) factory method
        FileTime ft = FileTime.from(instant);
        try {
            long resultSeconds = ft.to(TimeUnit.SECONDS);
            long expectedSeconds = instant.getEpochSecond();
            if (resultSeconds == expectedSeconds) {
                System.out.println("PASS: to(TimeUnit.SECONDS) matches instant epoch seconds");
            } else {
                System.err.println("FAIL: to(TimeUnit.SECONDS) returned " + resultSeconds + ", expected " + expectedSeconds);
            }
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception: " + t);
        }
    }
}
