/*
 * @test
 * @summary Test ArrayList.listIterator(int) method for valid and invalid indices
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ListIterator;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        // Test listIterator at index 0 (start)
        ListIterator<String> it0 = list.listIterator(0);
        if (it0.nextIndex() != 0) {
            throw new RuntimeException("listIterator(0) nextIndex() expected 0 but got " + it0.nextIndex());
        }

        // Test listIterator at index 1 (middle)
        ListIterator<String> it1 = list.listIterator(1);
        if (it1.nextIndex() != 1) {
            throw new RuntimeException("listIterator(1) nextIndex() expected 1 but got " + it1.nextIndex());
        }

        // Test listIterator at index size (end)
        ListIterator<String> itEnd = list.listIterator(list.size());
        if (itEnd.nextIndex() != list.size()) {
            throw new RuntimeException("listIterator(size) nextIndex() expected " + list.size() + " but got " + itEnd.nextIndex());
        }

        // Test listIterator with negative index - expect IndexOutOfBoundsException
        try {
            list.listIterator(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for listIterator(-1)");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // Test listIterator with index greater than size - expect IndexOutOfBoundsException
        try {
            list.listIterator(list.size() + 1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for listIterator(size+1)");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
