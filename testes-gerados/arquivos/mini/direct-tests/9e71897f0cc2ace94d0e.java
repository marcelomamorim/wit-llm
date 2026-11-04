/*
 * @test
 * @summary Test java.util.ArrayList set(int, E) method behavior
 * @run main java.util.ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Objects;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testSetReturnsOldValue();
        testSetReplacesElement();
        testSetIndexOutOfBounds();
    }

    private static void testSetReturnsOldValue() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        String old = list.set(1, "c");
        if (!"b".equals(old)) {
            throw new AssertionError("Expected old value 'b', got: " + old);
        }
    }

    private static void testSetReplacesElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.set(0, "y");
        String val = list.get(0);
        if (!"y".equals(val)) {
            throw new AssertionError("Expected element 'y' at index 0, got: " + val);
        }
    }

    private static void testSetIndexOutOfBounds() {
        ArrayList<String> list = new ArrayList<>();
        list.add("only");
        boolean thrown = false;
        try {
            list.set(1, "fail");
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for index 1");
        }
    }
}
