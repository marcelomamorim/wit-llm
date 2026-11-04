/*
 * @test
 * @summary Test java.util.ArrayList.hashCode() for fail-fast ConcurrentModificationException and correctness
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testHashCodeBasic();
        testHashCodeFailFast();
    }

    private static void testHashCodeBasic() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        int hash1 = list.hashCode();
        int hash2 = list.hashCode();
        if (hash1 != hash2) {
            throw new AssertionError("hashCode() should be consistent on same list content");
        }
    }

    private static void testHashCodeFailFast() {
        final ArrayList<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(20);
        list.add(30);

        // Create a subList to trigger internal SubList.hashCode
        java.util.List<Integer> subList = list.subList(0, 2);

        // Modify the backing list structurally after creating subList
        list.add(40);

        // The subList.hashCode() should throw ConcurrentModificationException due to fail-fast
        try {
            subList.hashCode();
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // Expected exception
        }
    }
}
