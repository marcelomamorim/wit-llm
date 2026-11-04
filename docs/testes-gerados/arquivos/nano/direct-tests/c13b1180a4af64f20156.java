/*
 * @test
 * @summary Test for Instant.minusMillis(long)
 * @run main InstantWitupTest
 */

import java.time.Instant;

public class InstantWitupTest {

    public static void main(String[] args) {
        testMinusMillisNormal();
        testMinusMillisMinValue();
        testMinusMillisMaxValue();
    }

    static void testMinusMillisNormal() {
        Instant original = Instant.ofEpochMilli(1000L);
        Instant result = original.minusMillis(500L);
        long expectedEpochMilli = 500L;
        assert result.toEpochMilli() == expectedEpochMilli : "Expected epochMilli to be 500, but was " + result.toEpochMilli();
    }

    static void testMinusMillisMinValue() {
        Instant original = Instant.ofEpochMilli(0L);
        Instant result = original.minusMillis(Long.MIN_VALUE);
        long expectedEpochMilli = 1L; // since Long.MIN_VALUE + Long.MAX_VALUE + 1 = 1
        assert result.toEpochMilli() == expectedEpochMilli : "Expected epochMilli to be 1, but was " + result.toEpochMilli();
    }

    static void testMinusMillisMaxValue() {
        Instant original = Instant.ofEpochMilli(0L);
        Instant result = original.minusMillis(Long.MAX_VALUE);
        long expectedEpochMilli = -Long.MAX_VALUE;
        assert result.toEpochMilli() == expectedEpochMilli : "Expected epochMilli to be " + expectedEpochMilli + ", but was " + result.toEpochMilli();
    }
}
