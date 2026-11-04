/*
 * @test
 * @summary Tests ArrayList.set return value, element replacement, size preservation and index checks
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testReturnsOldValueAndReplaces();
        testAcceptsNullElement();
        testSizeUnchangedAfterSet();
        testOutOfBoundsIndexThrows();
        testEmptyListRejectsAnyIndex();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testReturnsOldValueAndReplaces() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        String old = list.set(1, "B");
        check("b".equals(old), "expected old value b but was " + old);
        check("B".equals(list.get(1)), "expected new value B but was " + list.get(1));
        check("a".equals(list.get(0)), "index 0 changed");
        check("c".equals(list.get(2)), "index 2 changed");
    }

    static void testAcceptsNullElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        String old = list.set(0, null);
        check("x".equals(old), "expected old value x but was " + old);
        check(list.get(0) == null, "expected null element after set");
        String prev = list.set(0, "y");
        check(prev == null, "expected previous null but was " + prev);
        check("y".equals(list.get(0)), "expected y after set");
    }

    static void testSizeUnchangedAfterSet() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(20);
        list.set(0, 30);
        list.set(1, 40);
        check(list.size() == 2, "size changed to " + list.size());
        check(list.get(0) == 30 && list.get(1) == 40, "unexpected contents");
    }

    static void testOutOfBoundsIndexThrows() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        expectIndexException(list, -1);
        expectIndexException(list, 2);
        check(list.size() == 2, "size changed after failed set");
        check("a".equals(list.get(0)), "element 0 changed after failed set");
        check("b".equals(list.get(1)), "element 1 changed after failed set");
    }

    static void testEmptyListRejectsAnyIndex() {
        ArrayList<String> list = new ArrayList<>();
        expectIndexException(list, 0);
        check(list.size() == 0, "empty list grew after failed set");
    }

    static void expectIndexException(ArrayList<String> list, int index) {
        try {
            list.set(index, "z");
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new AssertionError("no IndexOutOfBoundsException for index " + index);
    }
}
