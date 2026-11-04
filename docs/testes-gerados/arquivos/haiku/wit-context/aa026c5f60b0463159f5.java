/*
 * @test
 * @summary Verifies LongStream.findAny() observable results and IllegalStateException on reuse of a consumed or linked stream
 * @run main LongPipelineWitupTest
 */

import java.util.OptionalLong;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testFindAnyOnEmptyStream();
        testFindAnyOnNonEmptyStream();
        testFindAnyOnConsumedStreamThrowsIllegalStateException();
        testFindAnyOnLinkedStreamThrowsIllegalStateException();
    }

    static void testFindAnyOnEmptyStream() {
        OptionalLong result = LongStream.empty().findAny();
        check(result != null, "findAny returned null for empty stream");
        check(!result.isPresent(), "findAny on empty stream should be empty");
    }

    static void testFindAnyOnNonEmptyStream() {
        OptionalLong result = LongStream.of(1L, 2L, 3L).findAny();
        check(result != null, "findAny returned null for non-empty stream");
        check(result.isPresent(), "findAny on non-empty stream should be present");
        long v = result.getAsLong();
        check(v == 1L || v == 2L || v == 3L,
              "findAny returned an element not in the source: " + v);
    }

    static void testFindAnyOnConsumedStreamThrowsIllegalStateException() {
        LongStream s = LongStream.of(1L, 2L);
        s.findAny();
        try {
            s.findAny();
            throw new RuntimeException("expected IllegalStateException on reused stream");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    static void testFindAnyOnLinkedStreamThrowsIllegalStateException() {
        LongStream s = LongStream.of(1L, 2L);
        s.map(x -> x);
        try {
            s.findAny();
            throw new RuntimeException("expected IllegalStateException on linked stream");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
