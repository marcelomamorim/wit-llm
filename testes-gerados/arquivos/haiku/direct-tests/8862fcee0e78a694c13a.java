/*
 * @test
 * @summary Tests FileTime.from(long, TimeUnit) observable conversion and null unit handling
 * @run main FileTimeWitupTest
 */

import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

public class FileTimeWitupTest {

    public static void main(String[] args) {
        testSecondsEqualsMillis();
        testZeroAcrossUnits();
        testNegativeValueOrdering();
        testMillisecondsMatchFromMillis();
        testNullUnitThrowsNpe();
    }

    static void testSecondsEqualsMillis() {
        FileTime a = FileTime.from(5L, TimeUnit.SECONDS);
        FileTime b = FileTime.fromMillis(5000L);
        check(a.compareTo(b) == 0, "5 SECONDS should equal 5000 ms");
        check(b.compareTo(a) == 0, "comparison should be symmetric");
    }

    static void testZeroAcrossUnits() {
        FileTime a = FileTime.from(0L, TimeUnit.DAYS);
        FileTime b = FileTime.from(0L, TimeUnit.NANOSECONDS);
        check(a.compareTo(b) == 0, "zero in any unit should be the epoch");
    }

    static void testNegativeValueOrdering() {
        FileTime negative = FileTime.from(-1L, TimeUnit.SECONDS);
        FileTime epoch = FileTime.fromMillis(0L);
        FileTime positive = FileTime.from(1L, TimeUnit.SECONDS);
        check(negative.compareTo(epoch) < 0, "-1 SECONDS should precede epoch");
        check(epoch.compareTo(positive) < 0, "epoch should precede +1 SECONDS");
        check(negative.compareTo(positive) < 0, "negative should precede positive");
    }

    static void testMillisecondsMatchFromMillis() {
        FileTime a = FileTime.from(1234L, TimeUnit.MILLISECONDS);
        FileTime b = FileTime.fromMillis(1234L);
        check(a.compareTo(b) == 0, "MILLISECONDS unit should match fromMillis");
    }

    static void testNullUnitThrowsNpe() {
        boolean thrown = false;
        try {
            FileTime.from(1L, null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null unit should throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
