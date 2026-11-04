/* @test    @summary Test ArrayList.remove(int index) method for correct removal and exception handling    @run main ArrayListWitupTest */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testRemoveValidIndex();
        testRemoveInvalidIndex();
    }

    static void testRemoveValidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        String removed = list.remove(1);
        if (!"b".equals(removed)) {
            throw new AssertionError("Expected removed element to be 'b', but was: " + removed);
        }
        if (list.size() != 2) {
            throw new AssertionError("Expected list size 2 after removal, but was: " + list.size());
        }
        if (!list.get(0).equals("a") || !list.get(1).equals("c")) {
            throw new AssertionError("List elements after removal are incorrect: " + list);
        }
    }

    static void testRemoveInvalidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        boolean exceptionThrown = false;
        try {
            list.remove(5);
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for invalid index");
        }
    }
}
