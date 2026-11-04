/* @test    @summary Tests for ArrayList.set method to ensure correct behavior and exception handling    @run main ArrayListWitupTest */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testSetValidIndex();
        testSetInvalidIndex();
    }

    static void testSetValidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        String oldValue = list.set(1, "c");
        if (!"b".equals(oldValue)) {
            throw new AssertionError("Expected old value 'b', but got: " + oldValue);
        }
        if (!"c".equals(list.get(1))) {
            throw new AssertionError("Expected new value 'c' at index 1, but got: " + list.get(1));
        }
    }

    static void testSetInvalidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        boolean exceptionThrown = false;
        try {
            list.set(5, "b");
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for invalid index");
        }
    }
}
