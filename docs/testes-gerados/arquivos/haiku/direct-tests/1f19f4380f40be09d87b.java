/*
 * @test
 * @summary Verifies observable behavior of the LongStream builder that uses Streams.LongStreamBuilderImpl.accept(long)
 * @run main StreamsWitupTest
 */

import java.util.Arrays;
import java.util.stream.LongStream;

public class StreamsWitupTest {

    public static void main(String[] args) {
        testEmptyBuilder();
        testSingleElement();
        testMultipleElementsPreserveOrder();
        testExtremeValues();
        testAcceptAfterBuildEmptyThrows();
        testAcceptAfterBuildNonEmptyThrows();
    }

    static void testEmptyBuilder() {
        LongStream.Builder b = LongStream.builder();
        check(b.build().count() == 0L, "empty builder must produce empty stream");
    }

    static void testSingleElement() {
        LongStream.Builder b = LongStream.builder();
        b.accept(42L);
        long[] result = b.build().toArray();
        check(Arrays.equals(result, new long[]{42L}), "single element mismatch");
    }

    static void testMultipleElementsPreserveOrder() {
        LongStream.Builder b = LongStream.builder();
        b.accept(3L);
        b.accept(1L);
        b.accept(2L);
        b.accept(5L);
        long[] result = b.build().toArray();
        check(Arrays.equals(result, new long[]{3L, 1L, 2L, 5L}), "order not preserved");
    }

    static void testExtremeValues() {
        LongStream.Builder b = LongStream.builder();
        b.accept(Long.MIN_VALUE);
        b.accept(0L);
        b.accept(Long.MAX_VALUE);
        long[] result = b.build().toArray();
        check(Arrays.equals(result, new long[]{Long.MIN_VALUE, 0L, Long.MAX_VALUE}),
              "extreme values mismatch");
    }

    static void testAcceptAfterBuildEmptyThrows() {
        LongStream.Builder b = LongStream.builder();
        b.build();
        boolean thrown = false;
        try {
            b.accept(1L);
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "accept after build (empty) must throw IllegalStateException");
    }

    static void testAcceptAfterBuildNonEmptyThrows() {
        LongStream.Builder b = LongStream.builder();
        b.accept(1L);
        b.accept(2L);
        b.build();
        boolean thrown = false;
        try {
            b.accept(3L);
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "accept after build (non-empty) must throw IllegalStateException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
