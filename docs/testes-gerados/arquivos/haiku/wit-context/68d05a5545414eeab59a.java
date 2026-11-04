/*
 * @test
 * @summary Verify lastIndexOf behavior of immutable lists obtained through the public List.of factories
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.List;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testFoundElementReturnsLastIndex();
        testMissingElementReturnsMinusOne();
        testSingleElementList();
        testEmptyListReturnsMinusOne();
        testEqualsBasedMatching();
        testNullArgumentThrowsNpe();
        testNullArgumentOnEmptyListThrowsNpe();
    }

    static void testFoundElementReturnsLastIndex() {
        List<Integer> list = List.of(1, 2, 3, 2);
        check(list.lastIndexOf(2) == 3, "duplicate element must return last index");
        check(list.lastIndexOf(1) == 0, "unique first element must return its index");
        check(list.lastIndexOf(3) == 2, "middle element must return its index");
    }

    static void testMissingElementReturnsMinusOne() {
        List<Integer> list = List.of(1, 2, 3, 2);
        check(list.lastIndexOf(4) == -1, "absent element must return -1");
    }

    static void testSingleElementList() {
        List<Integer> list = List.of(7);
        check(list.lastIndexOf(7) == 0, "single matching element must return 0");
        check(list.lastIndexOf(8) == -1, "single non-matching element must return -1");
    }

    static void testEmptyListReturnsMinusOne() {
        List<String> list = List.of();
        check(list.lastIndexOf("a") == -1, "empty list must return -1");
    }

    static void testEqualsBasedMatching() {
        List<String> list = List.of(new String("a"), new String("b"), new String("a"));
        check(list.lastIndexOf("a") == 2, "matching must use equals, returning last equal index");
    }

    static void testNullArgumentThrowsNpe() {
        List<Integer> list = List.of(1, 2, 3);
        boolean thrown = false;
        try {
            list.lastIndexOf(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "lastIndexOf(null) must throw NullPointerException");
    }

    static void testNullArgumentOnEmptyListThrowsNpe() {
        List<Integer> list = List.of();
        boolean thrown = false;
        try {
            list.lastIndexOf(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "lastIndexOf(null) on empty list must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
