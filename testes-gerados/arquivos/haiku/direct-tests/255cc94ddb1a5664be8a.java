/*
 * @test
 * @summary Verify ArrayList.addAll(Collection) appends elements in iteration order and reports structural change
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testAppendsElementsInOrder();
        testNonEmptyCollectionReturnsTrue();
        testEmptyCollectionReturnsFalseAndLeavesListUnchanged();
        testSelfAdditionDuplicatesContents();
        testNullElementsArePreserved();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAppendsElementsInOrder() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.addAll(Arrays.asList("c", "d"));
        List<String> expected = Arrays.asList("a", "b", "c", "d");
        check(list.equals(expected), "addAll must append elements in iteration order, got " + list);
    }

    static void testNonEmptyCollectionReturnsTrue() {
        ArrayList<Integer> list = new ArrayList<>();
        boolean changed = list.addAll(Arrays.asList(1, 2, 3));
        check(changed, "addAll with non-empty collection must return true");
        check(list.size() == 3, "size must be 3 after addAll, was " + list.size());
    }

    static void testEmptyCollectionReturnsFalseAndLeavesListUnchanged() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2));
        boolean changed = list.addAll(new ArrayList<Integer>());
        check(!changed, "addAll with empty collection must return false");
        check(list.size() == 2, "size must remain 2, was " + list.size());
        check(list.equals(Arrays.asList(1, 2)), "contents must be unchanged, got " + list);
    }

    static void testSelfAdditionDuplicatesContents() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2));
        boolean changed = list.addAll(list);
        check(changed, "self addAll with non-empty list must return true");
        check(list.equals(Arrays.asList(1, 2, 1, 2)), "self addAll must duplicate contents, got " + list);
    }

    static void testNullElementsArePreserved() {
        ArrayList<String> list = new ArrayList<>();
        list.addAll(Arrays.asList("x", null));
        check(list.size() == 2, "size must be 2, was " + list.size());
        check("x".equals(list.get(0)), "first element must be x");
        check(list.get(1) == null, "second element must be null");
    }
}
