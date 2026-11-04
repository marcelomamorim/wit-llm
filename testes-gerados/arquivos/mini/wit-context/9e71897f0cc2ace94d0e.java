/*
 * @test
 * @summary Test java.util.ArrayList.set(int, E) method for index bounds and concurrent modification
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Objects;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testSetValidIndex();
        testSetIndexOutOfBounds();
        testSetConcurrentModification();
    }

    private static void testSetValidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("zero");
        list.add("one");
        list.add("two");

        // set element at valid index
        String old = list.set(1, "ONE");
        if (!"one".equals(old)) {
            throw new AssertionError("Expected old value 'one', got: " + old);
        }
        if (!"ONE".equals(list.get(1))) {
            throw new AssertionError("Expected new value 'ONE' at index 1, got: " + list.get(1));
        }
    }

    private static void testSetIndexOutOfBounds() {
        ArrayList<String> list = new ArrayList<>();
        list.add("only");

        // test index < 0
        try {
            list.set(-1, "fail");
            throw new AssertionError("Expected IndexOutOfBoundsException for index -1");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // test index >= size
        try {
            list.set(1, "fail");
            throw new AssertionError("Expected IndexOutOfBoundsException for index 1");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testSetConcurrentModification() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");

        // Create a subList to trigger concurrent modification on root
        java.util.List<String> subList = list.subList(0, 2);

        // Modify root list structurally to change modCount
        list.add("c");

        try {
            // This should throw ConcurrentModificationException because modCount changed
            subList.set(0, "x");
            throw new AssertionError("Expected ConcurrentModificationException due to concurrent modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
