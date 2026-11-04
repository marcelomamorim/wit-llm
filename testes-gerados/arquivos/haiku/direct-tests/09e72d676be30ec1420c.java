/*
 * @test
 * @summary Behavioral tests for FileTime.to(TimeUnit) conversions, null check and saturation
 * @run main FileTimeWitupTest
 */

import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class FileTimeWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void checkEquals(long expected, long actual, String msg) {
        if (expected != actual) {
            throw new RuntimeException(msg + ": expected " + expected + " but was " + actual);
        }
    }

    public static void main(String[] args) {
        // value/unit based construction, same unit
        checkEquals(7L, FileTime.from(7L, TimeUnit.DAYS).to(TimeUnit.DAYS), "same unit");

        // value/unit based construction, converted to a finer unit
        checkEquals(1000L, FileTime.from(1L, TimeUnit.SECONDS).to(TimeUnit.MILLISECONDS), "1s to ms");

        // negative value
        checkEquals(-1L, FileTime.from(-1L, TimeUnit.SECONDS).to(TimeUnit.SECONDS), "negative seconds");

        // millisecond factory
        checkEquals(1234L, FileTime.fromMillis(1234L).to(TimeUnit.MILLISECONDS), "fromMillis round trip");

        // Instant based construction, whole seconds
        checkEquals(5L, FileTime.from(Instant.ofEpochSecond(5L)).to(TimeUnit.SECONDS), "instant whole seconds");

        // Instant based construction, with nanos truncated to seconds
        checkEquals(5L, FileTime.from(Instant.ofEpochSecond(5L, 500_000_000L)).to(TimeUnit.SECONDS), "instant nanos to seconds");

        // Instant based construction, nanos converted to milliseconds
        checkEquals(5500L, FileTime.from(Instant.ofEpochSecond(5L, 500_000_000L)).to(TimeUnit.MILLISECONDS), "instant to ms");

        // Instant before epoch with fractional part
        checkEquals(-500L, FileTime.from(Instant.ofEpochSecond(-1L, 500_000_000L)).to(TimeUnit.MILLISECONDS), "negative instant to ms");

        // Saturation: Instant.MAX in nanoseconds overflows to Long.MAX_VALUE
        checkEquals(Long.MAX_VALUE, FileTime.from(Instant.MAX).to(TimeUnit.NANOSECONDS), "Instant.MAX saturation");

        // Saturation: Instant.MIN in nanoseconds overflows to Long.MIN_VALUE
        checkEquals(Long.MIN_VALUE, FileTime.from(Instant.MIN).to(TimeUnit.NANOSECONDS), "Instant.MIN saturation");

        // Null unit must be rejected with NullPointerException
        boolean npe = false;
        try {
            FileTime.from(1L, TimeUnit.SECONDS).to(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "to(null) must throw NullPointerException");

        // Null unit on Instant-based FileTime must also be rejected
        npe = false;
        try {
            FileTime.from(Instant.ofEpochSecond(1L)).to(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "to(null) on instant-based FileTime must throw NullPointerException");
    }
}
