/*
 * @test
 * @summary Test java.util.ArrayList.remove(int) for index bounds and concurrent modification
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Objects;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testRemoveIndexOutOfBounds();
        testRemoveConcurrentModification();
        testRemoveValidIndex();
    }

    private static void testRemoveIndexOutOfBounds() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");

        // index < 0
        try {
            list.remove(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for index -1");
        } catch (IndexOutOfBoundsException expected) {
        }

        // index == size
        try {
            list.remove(list.size());
            throw new RuntimeException("Expected IndexOutOfBoundsException for index == size");
        } catch (IndexOutOfBoundsException expected) {
        }

        // index > size
        try {
            list.remove(list.size() + 1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for index > size");
        } catch (IndexOutOfBoundsException expected) {
        }
    }

    private static void testRemoveConcurrentModification() {
        // To test ConcurrentModificationException, we need to use a subList and modify the root list concurrently
        ArrayList<String> root = new ArrayList<>();
        root.add("x");
        root.add("y");
        root.add("z");

        List<String> subList = root.subList(0, 3);

        // Modify root list structurally to cause concurrent modification
        root.add("w");

        try {
            subList.remove(1); // should throw ConcurrentModificationException
            throw new RuntimeException("Expected ConcurrentModificationException due to concurrent modification");
        } catch (ConcurrentModificationException expected) {
        }
    }

    private static void testRemoveValidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("first");
        list.add("second");
        list.add("third");

        String removed = list.remove(1);
        if (!"second".equals(removed)) {
            throw new RuntimeException("Removed element mismatch: expected 'second', got '" + removed + "'");
        }

        if (list.size() != 2) {
            throw new RuntimeException("List size after removal should be 2, but was " + list.size());
        }

        if (list.get(0) == null || list.get(1) == null) {
            throw new RuntimeException("List elements should not be null after removal");
        }
    }
}
