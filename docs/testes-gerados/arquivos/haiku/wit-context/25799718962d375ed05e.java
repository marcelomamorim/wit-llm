/*
 * @test
 * @summary Verifies public findFirst() behavior: first element, empty result, null element rejection and reuse of a consumed stream
 * @run main ReferencePipelineWitupTest
 */

import java.util.Optional;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testFindFirstReturnsFirstElement();
        testFindFirstOnEmptyStreamReturnsEmpty();
        testFindFirstWithNullElementThrowsNPE();
        testReusingConsumedStreamThrowsIllegalStateException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testFindFirstReturnsFirstElement() {
        Optional<String> result = Stream.of("a", "b", "c").findFirst();
        check(result.isPresent(), "expected a present Optional");
        check("a".equals(result.get()), "expected first element 'a' but was " + result.get());
    }

    static void testFindFirstOnEmptyStreamReturnsEmpty() {
        Optional<String> result = Stream.<String>empty().findFirst();
        check(!result.isPresent(), "expected empty Optional for empty stream");
    }

    static void testFindFirstWithNullElementThrowsNPE() {
        try {
            Stream.of((String) null).findFirst();
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null first element");
    }

    static void testReusingConsumedStreamThrowsIllegalStateException() {
        Stream<String> stream = Stream.of("x", "y");
        Optional<String> first = stream.findFirst();
        check("x".equals(first.get()), "expected first element 'x'");
        try {
            stream.findFirst();
        } catch (IllegalStateException expected) {
            return;
        }
        throw new RuntimeException("expected IllegalStateException when reusing a consumed stream");
    }
}
