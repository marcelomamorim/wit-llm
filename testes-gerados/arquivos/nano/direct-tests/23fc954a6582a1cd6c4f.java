/* @test    @summary Test ArrayList.remove(int index) method for correct removal and return of element at specified index.    @run main ArrayListWitupTest */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testRemoveAtValidIndex();
        testRemoveAtInvalidIndex();
    }

    private static void testRemoveAtValidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        String removed = list.remove(1);
        if (!"b".equals(removed)) {
            throw new AssertionError("Expected removed element to be 'b', but was: " + removed);
        }
        if (list.size() != 2) {
            throw new AssertionError("Expected list size to be 2 after removal, but was: " + list.size());
        }
        if (!list.get(0).equals("a") || !list.get(1).equals("c")) {
            throw new AssertionError("List elements after removal are incorrect: " + list);
        }
    }

    private static void testRemoveAtInvalidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        boolean exceptionThrown = false;
        try {
            list.remove(5);
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException when removing at invalid index");
        }
    }
}
