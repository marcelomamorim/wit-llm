/*
 * @test
 * @summary Behavioral tests for ArrayList.set via public API, including the SubList.set path shown in the source excerpt
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testSetReturnsOldValueAndStoresNew();
        testSetNullElement();
        testSetInvalidIndexThrows();
        testSubListSetWritesThroughToParent();
        testSubListSetAfterStructuralModificationThrows();
    }

    static void testSetReturnsOldValueAndStoresNew() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        check("a".equals(list.set(0, "x")), "set must return previous element");
        check("x".equals(list.get(0)), "set must store new element");
        check(list.size() == 3, "set must not change size");
        check("b".equals(list.get(1)), "set must not affect other indices");
    }

    static void testSetNullElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add("c");
        check("c".equals(list.set(0, null)), "set(null) must return old value");
        check(list.get(0) == null, "set must store null");
        check(list.set(0, "z") == null, "set must return null when previous element was null");
        check("z".equals(list.get(0)), "set must overwrite null");
    }

    static void testSetInvalidIndexThrows() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        expectIndexOutOfBounds(list, -1);
        expectIndexOutOfBounds(list, 2);
        expectIndexOutOfBounds(new ArrayList<String>(), 0);
        check(list.size() == 2, "failed set must not change size");
        check("a".equals(list.get(0)) && "b".equals(list.get(1)), "failed set must not change elements");
    }

    static void testSubListSetWritesThroughToParent() {
        ArrayList<String> base = new ArrayList<>();
        base.add("p");
        base.add("q");
        base.add("r");
        List<String> sub = base.subList(1, 3);
        check("q".equals(sub.set(0, "Q")), "subList set must return previous element");
        check("Q".equals(base.get(1)), "subList set must write through to parent");
    }

    static void testSubListSetAfterStructuralModificationThrows() {
        ArrayList<String> base = new ArrayList<>();
        base.add("p");
        base.add("q");
        List<String> sub = base.subList(0, 2);
        base.add("extra");
        boolean thrown = false;
        try {
            sub.set(0, "R");
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "subList set after parent structural change must throw ConcurrentModificationException");
    }

    static void expectIndexOutOfBounds(ArrayList<String> list, int index) {
        boolean thrown = false;
        try {
            list.set(index, "x");
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "set(" + index + ") must throw IndexOutOfBoundsException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
