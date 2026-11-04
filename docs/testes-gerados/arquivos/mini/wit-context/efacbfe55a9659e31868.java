/*
 * @test
 * @summary Test java.util.ArrayList.listIterator(int) for valid and invalid indices
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

        // Test valid indices: 0 to size (inclusive)
        for (int i = 0; i <= list.size(); i++) {
            ListIterator<String> it = list.listIterator(i);
            if (i < list.size()) {
                // The iterator should start at index i
                int startIndex = it.nextIndex();
                if (startIndex != i) {
                    throw new AssertionError("listIterator(" + i + ") returned iterator with nextIndex " + startIndex);
                }
            } else {
                // i == size, iterator at end
                if (it.hasNext()) {
                    throw new AssertionError("listIterator(" + i + ") at end should have no next element");
                }
            }
        }

        // Test invalid indices: negative and greater than size
        int[] invalidIndices = { -1, -100, list.size() + 1, list.size() + 10 };
        for (int idx : invalidIndices) {
            try {
                list.listIterator(idx);
                throw new AssertionError("Expected IndexOutOfBoundsException for index " + idx);
            } catch (IndexOutOfBoundsException expected) {
                // Expected exception
            }
        }
    }
}
