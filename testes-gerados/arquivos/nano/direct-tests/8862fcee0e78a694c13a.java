/* @test    @summary Test FileTime.from(long, TimeUnit) method for various inputs    @run main FileTimeWitupTest */

import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

public class FileTimeWitupTest {

    public static void main(String[] args) {
        testFromWithPositiveValueAndSeconds();
        testFromWithZeroValueAndMilliseconds();
        testFromWithNegativeValueAndMinutes();
        testFromWithLargeValueAndHours();
        testFromWithNullTimeUnit();
    }

    static void testFromWithPositiveValueAndSeconds() {
        FileTime ft = FileTime.from(12345L, TimeUnit.SECONDS);
        assert ft != null : "FileTime should not be null";
        long expectedMillis = 12345L * 1000;
        assert ft.toMillis() == expectedMillis : "Expected milliseconds: " + expectedMillis + ", but got: " + ft.toMillis();
    }

    static void testFromWithZeroValueAndMilliseconds() {
        FileTime ft = FileTime.from(0L, TimeUnit.MILLISECONDS);
        assert ft != null : "FileTime should not be null";
        assert ft.toMillis() == 0L : "Expected milliseconds: 0, but got: " + ft.toMillis();
    }

    static void testFromWithNegativeValueAndMinutes() {
        FileTime ft = FileTime.from(-9876L, TimeUnit.MINUTES);
        assert ft != null : "FileTime should not be null";
        long expectedMillis = -9876L * 60 * 1000;
        assert ft.toMillis() == expectedMillis : "Expected milliseconds: " + expectedMillis + ", but got: " + ft.toMillis();
    }

    static void testFromWithLargeValueAndHours() {
        long largeValue = Long.MAX_VALUE / 3600;
        FileTime ft = FileTime.from(largeValue, TimeUnit.HOURS);
        assert ft != null : "FileTime should not be null";
        long expectedMillis = largeValue * 3600 * 1000;
        assert ft.toMillis() == expectedMillis : "Expected milliseconds: " + expectedMillis + ", but got: " + ft.toMillis();
    }

    static void testFromWithNullTimeUnit() {
        try {
            FileTime ft = FileTime.from(100L, null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            assert e.getMessage().equals("unit") : "Expected message 'unit', but got: " + e.getMessage();
        }
    }
}
