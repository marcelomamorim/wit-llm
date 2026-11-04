/*
 * @test
 * @summary Tests FileTime.from(Instant) null handling and observable instant round-trip
 * @run main FileTimeWitupTest
 */

import java.nio.file.attribute.FileTime;
import java.time.Instant;

public class FileTimeWitupTest {

    public static void main(String[] args) {
        testNullInstantThrowsNPE();
        testToInstantReturnsSameInstant();
        testEpochInstant();
        testNegativeInstant();
        testOrderingFromInstants();
        testEqualInstantsCompareZero();
    }

    static void testNullInstantThrowsNPE() {
        try {
            FileTime.from((Instant) null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null instant");
    }

    static void testToInstantReturnsSameInstant() {
        Instant i = Instant.ofEpochSecond(1234567890L, 123456789L);
        FileTime ft = FileTime.from(i);
        if (!i.equals(ft.toInstant())) {
            throw new RuntimeException("toInstant() did not return the source instant");
        }
    }

    static void testEpochInstant() {
        FileTime ft = FileTime.from(Instant.EPOCH);
        if (!Instant.EPOCH.equals(ft.toInstant())) {
            throw new RuntimeException("Epoch instant not preserved");
        }
    }

    static void testNegativeInstant() {
        Instant i = Instant.ofEpochSecond(-1000L);
        FileTime ft = FileTime.from(i);
        if (!i.equals(ft.toInstant())) {
            throw new RuntimeException("Negative instant not preserved");
        }
    }

    static void testOrderingFromInstants() {
        FileTime earlier = FileTime.from(Instant.ofEpochSecond(1L));
        FileTime later = FileTime.from(Instant.ofEpochSecond(2L));
        if (earlier.compareTo(later) >= 0) {
            throw new RuntimeException("Earlier FileTime should compare less than later");
        }
        if (later.compareTo(earlier) <= 0) {
            throw new RuntimeException("Later FileTime should compare greater than earlier");
        }
    }

    static void testEqualInstantsCompareZero() {
        FileTime a = FileTime.from(Instant.ofEpochSecond(42L, 7L));
        FileTime b = FileTime.from(Instant.ofEpochSecond(42L, 7L));
        if (a.compareTo(b) != 0) {
            throw new RuntimeException("FileTimes from equal instants should compare as 0");
        }
    }
}
