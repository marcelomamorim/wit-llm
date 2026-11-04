/*
 * @test
 * @summary White-box-free behavioral checks for ArrayList.addAll(Collection)
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testAppendsAtEnd();
        testEmptyCollectionReturnsFalse();
        testNullCollectionThrowsNPE();
        testSelfAddDuplicatesElements();
        testPreservesNullElements();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAppendsAtEnd() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b"));
        boolean changed = list.addAll(Arrays.asList("c", "d"));
        check(changed, "addAll with non-empty collection should return true");
        check(list.equals(Arrays.asList("a", "b", "c", "d")),
              "addAll should append elements at the end in order");
    }

    static void testEmptyCollectionReturnsFalse() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("x"));
        boolean changed = list.addAll(new ArrayList<String>());
        check(!changed, "addAll with empty collection should return false");
        check(list.equals(Arrays.asList("x")), "list should be unchanged by empty addAll");
    }

    static void testNullCollectionThrowsNPE() {
        ArrayList<String> list = new ArrayList<>();
        List<String> nullList = null;
        boolean thrown = false;
        try {
            list.addAll(nullList);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "addAll(null) should throw NullPointerException");
        check(list.isEmpty(), "list should not be modified when addAll(null) fails");
    }

    static void testSelfAddDuplicatesElements() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b"));
        list.addAll(list);
        check(list.equals(Arrays.asList("a", "b", "a", "b")),
              "addAll(self) should append a copy of the current elements");
    }

    static void testPreservesNullElements() {
        ArrayList<String> list = new ArrayList<>();
        List<String> source = Arrays.asList("a", null);
        list.addAll(source);
        check(list.size() == 2, "addAll should add all elements including null");
        check(list.get(1) == null, "null element should be preserved");
        check(list.equals(source), "contents should match source collection");
    }
}
