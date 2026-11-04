/*
 * @test
 * @summary Test java.util.ArrayList.get(int) for index bounds and concurrent modification
 * @run main java.util.ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Objects;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testGetIndexOutOfBounds();
        testGetConcurrentModification();
        testGetValidIndex();
    }

    private static void testGetIndexOutOfBounds() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");

        // index < 0
        try {
            list.get(-1);
            throw new Error("Expected IndexOutOfBoundsException for index -1");
        } catch (IndexOutOfBoundsException expected) {
        }

        // index == size
        try {
            list.get(list.size());
            throw new Error("Expected IndexOutOfBoundsException for index == size");
        } catch (IndexOutOfBoundsException expected) {
        }

        // index > size
        try {
            list.get(list.size() + 1);
            throw new Error("Expected IndexOutOfBoundsException for index > size");
        } catch (IndexOutOfBoundsException expected) {
        }
    }

    private static void testGetConcurrentModification() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");

        // Create a subList to trigger concurrent modification detection
        List<String> subList = list.subList(0, list.size());

        // Modify the root list structurally
        list.add("z");

        try {
            subList.get(0); // should throw ConcurrentModificationException
            throw new Error("Expected ConcurrentModificationException after structural modification");
        } catch (ConcurrentModificationException expected) {
        }
    }

    private static void testGetValidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("first");
        list.add("second");

        // Valid indices
        if (!"first".equals(list.get(0))) {
            throw new Error("Expected 'first' at index 0");
        }
        if (!"second".equals(list.get(1))) {
            throw new Error("Expected 'second' at index 1");
        }
    }
}
