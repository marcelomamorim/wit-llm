/*
 * @test
 * @summary Verifies indexOf behavior of immutable lists returned by List.of, including the NullPointerException for null argument
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.List;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testFoundElementReturnsIndex();
        testFirstOccurrenceIsReturned();
        testMissingElementReturnsMinusOne();
        testEqualsBasedMatching();
        testNullArgumentThrowsNPE();
        testSingleElementList();
    }

    static void testFoundElementReturnsIndex() {
        List<Integer> l = List.of(1, 2, 3);
        check(l.indexOf(1) == 0, "indexOf(1) should be 0");
        check(l.indexOf(2) == 1, "indexOf(2) should be 1");
        check(l.indexOf(3) == 2, "indexOf(3) should be 2");
    }

    static void testFirstOccurrenceIsReturned() {
        List<Integer> l = List.of(4, 5, 4);
        check(l.indexOf(4) == 0, "indexOf should return first occurrence");
    }

    static void testMissingElementReturnsMinusOne() {
        List<Integer> l = List.of(1, 2, 3);
        check(l.indexOf(99) == -1, "indexOf of missing element should be -1");
    }

    static void testEqualsBasedMatching() {
        List<String> l = List.of("a", "b", "c");
        check(l.indexOf(new String("b")) == 1, "indexOf should use equals, not identity");
    }

    static void testNullArgumentThrowsNPE() {
        List<Integer> l = List.of(1, 2, 3);
        try {
            l.indexOf(null);
            throw new AssertionError("indexOf(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testSingleElementList() {
        List<String> l = List.of("x");
        check(l.indexOf("x") == 0, "single element list indexOf should be 0");
        check(l.indexOf("y") == -1, "single element list missing value should be -1");
        try {
            l.indexOf(null);
            throw new AssertionError("indexOf(null) on single element list should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
