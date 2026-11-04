/* @test    @summary Test for FileTime.to(TimeUnit) method to ensure correct conversion.    @run main FileTimeWitupTest */

import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.nio.file.attribute.FileTime;

public class FileTimeWitupTest {

    public static void main(String[] args) {
        testToWithSameUnit();
        testToWithDifferentUnits();
        testToWithNullUnit();
        testToWithInstantConversion();
        testToWithLongMinMax();
    }

    static void testToWithSameUnit() {
        FileTime ft = FileTime.from(1000L, TimeUnit.MILLISECONDS);
        long result = ft.to(TimeUnit.MILLISECONDS);
        assert result == 1000L : "Expected 1000, got " + result;
    }

    static void testToWithDifferentUnits() {
        FileTime ft = FileTime.from(1L, TimeUnit.SECONDS);
        long nanos = ft.to(TimeUnit.NANOSECONDS);
        assert nanos == 1_000_000_000L : "Expected 1,000,000,000, got " + nanos;
    }

    static void testToWithNullUnit() {
        try {
            FileTime ft = FileTime.from(0L, TimeUnit.MILLISECONDS);
            ft.to(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testToWithInstantConversion() {
        Instant instant = Instant.ofEpochSecond(10, 500_000_000); // 10.5 seconds
        FileTime ft = FileTime.from(instant);
        long seconds = ft.to(TimeUnit.SECONDS);
        long millis = ft.to(TimeUnit.MILLISECONDS);
        assert seconds == 10 : "Expected 10, got " + seconds;
        assert millis == 10500L : "Expected 10500, got " + millis;
    }

    static void testToWithLongMinMax() {
        // Create FileTime with large value to test Long.MIN_VALUE and Long.MAX_VALUE
        FileTime ftMin = FileTime.from(Long.MIN_VALUE, TimeUnit.MILLISECONDS);
        long resultMin = ftMin.to(TimeUnit.MILLISECONDS);
        assert resultMin == Long.MIN_VALUE : "Expected Long.MIN_VALUE, got " + resultMin;
        
        FileTime ftMax = FileTime.from(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        long resultMax = ftMax.to(TimeUnit.MILLISECONDS);
        assert resultMax == Long.MAX_VALUE : "Expected Long.MAX_VALUE, got " + resultMax;
    }
}
