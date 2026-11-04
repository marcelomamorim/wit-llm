/*
 * @test
 * @summary Witup-derived checks for ArrayList.lastIndexOf and SubList.lastIndexOf comodification behavior
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testBasicLastIndexOf();
        testSubListLastIndexOfWithoutChange();
        testEmptySubListNullAfterStructuralChange();
        testEmptySubListNonNullAfterStructuralChange();
        testNonEmptySubListAfterStructuralChange();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testBasicLastIndexOf() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("a");
        check(list.lastIndexOf("a") == 2, "expected last index 2 for 'a'");
        check(list.lastIndexOf("z") == -1, "expected -1 for absent element");
        check(list.lastIndexOf(null) == -1, "expected -1 for null when absent");
        list.add(null);
        check(list.lastIndexOf(null) == 3, "expected index 3 for null");
    }

    static void testSubListLastIndexOfWithoutChange() {
        List<String> base = new ArrayList<>();
        base.add("x");
        base.add("a");
        base.add("b");
        base.add("a");
        base.add("y");
        List<String> sub = base.subList(1, 4);
        check(sub.lastIndexOf("a") == 2, "expected relative index 2 in sublist");
        check(sub.lastIndexOf("y") == -1, "expected -1 for element outside sublist");
        List<String> sub2 = base.subList(1, 3);
        check(sub2.lastIndexOf("a") == 0, "expected relative index 0 in second sublist");
    }

    static void testEmptySubListNullAfterStructuralChange() {
        List<String> base = new ArrayList<>();
        base.add("a");
        List<String> sub = base.subList(0, 0);
        base.add("b");
        boolean thrown = false;
        try {
            sub.lastIndexOf(null);
        } catch (java.util.ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException for empty sublist with null after structural change");
    }

    static void testEmptySubListNonNullAfterStructuralChange() {
        List<String> base = new ArrayList<>();
        base.add("a");
        List<String> sub = base.subList(0, 0);
        base.add("b");
        boolean thrown = false;
        try {
            sub.lastIndexOf("a");
        } catch (java.util.ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException for empty sublist with non-null after structural change");
    }

    static void testNonEmptySubListAfterStructuralChange() {
        List<String> base = new ArrayList<>();
        base.add("a");
        base.add("b");
        base.add("c");
        List<String> sub = base.subList(0, 2);
        base.add("d");
        boolean thrown = false;
        try {
            sub.lastIndexOf("a");
        } catch (java.util.ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException for non-empty sublist after structural change");
    }
}
