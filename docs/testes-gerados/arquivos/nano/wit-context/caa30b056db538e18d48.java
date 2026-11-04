/*
 * @test
 * @summary Test for java.util.Collections.get(int)
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testGetWithValidIndex();
        testGetWithNegativeIndex();
        testGetWithIndexEqualToSize();
    }

    private static void testGetWithValidIndex() {
        java.util.List<Integer> list = java.util.Arrays.asList(10, 20, 30);
        int value = Collections.get(list, 1);
        if (value != 20) {
            throw new AssertionError("Expected 20, but got " + value);
        }
    }

    private static void testGetWithNegativeIndex() {
        java.util.List<Integer> list = java.util.Arrays.asList(10, 20, 30);
        try {
            Collections.get(list, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testGetWithIndexEqualToSize() {
        java.util.List<Integer> list = java.util.Arrays.asList(10, 20, 30);
        try {
            Collections.get(list, list.size());
            throw new AssertionError("Expected IndexOutOfBoundsException for index equal to size");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
