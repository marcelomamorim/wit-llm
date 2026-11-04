/*
 * @test
 * @summary Test java.util.ArrayList.set(int, E) for index bounds and correct replacement
 * @run main java.util.ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Objects;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testSetValidIndex();
        testSetIndexOutOfBounds();
    }

    private static void testSetValidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("zero");
        list.add("one");
        list.add("two");

        // Replace element at index 1
        String old = list.set(1, "ONE");
        if (!"one".equals(old)) {
            throw new AssertionError("Expected old value 'one', got: " + old);
        }
        if (!"ONE".equals(list.get(1))) {
            throw new AssertionError("Expected new value 'ONE' at index 1, got: " + list.get(1));
        }

        // Replace element at index 0
        old = list.set(0, "ZERO");
        if (!"zero".equals(old)) {
            throw new AssertionError("Expected old value 'zero', got: " + old);
        }
        if (!"ZERO".equals(list.get(0))) {
            throw new AssertionError("Expected new value 'ZERO' at index 0, got: " + list.get(0));
        }

        // Replace element at last index
        old = list.set(list.size() - 1, "TWO");
        if (!"two".equals(old)) {
            throw new AssertionError("Expected old value 'two', got: " + old);
        }
        if (!"TWO".equals(list.get(list.size() - 1))) {
            throw new AssertionError("Expected new value 'TWO' at last index, got: " + list.get(list.size() - 1));
        }
    }

    private static void testSetIndexOutOfBounds() {
        ArrayList<String> list = new ArrayList<>();
        list.add("only");

        // Test index < 0
        try {
            list.set(-1, "fail");
            throw new AssertionError("Expected IndexOutOfBoundsException for index -1");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // Test index == size (out of bounds)
        try {
            list.set(list.size(), "fail");
            throw new AssertionError("Expected IndexOutOfBoundsException for index == size");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // Test index > size
        try {
            list.set(list.size() + 1, "fail");
            throw new AssertionError("Expected IndexOutOfBoundsException for index > size");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
