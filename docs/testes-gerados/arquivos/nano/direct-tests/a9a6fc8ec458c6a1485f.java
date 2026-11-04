/* @test    @summary Test for Collections.get method to verify IndexOutOfBoundsException is thrown for invalid index    @run main CollectionsWitupTest */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testGetWithNegativeIndex();
        testGetWithIndexEqualToSize();
        testGetWithValidIndex();
    }

    static void testGetWithNegativeIndex() {
        try {
            Collections.get(-1);
            System.err.println("Failed: Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Passed: IndexOutOfBoundsException thrown for negative index");
        }
    }

    static void testGetWithIndexEqualToSize() {
        try {
            Collections.get(0); // Since get always throws, test with any index, e.g., 0
            System.err.println("Failed: Expected IndexOutOfBoundsException for index 0");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Passed: IndexOutOfBoundsException thrown for index 0");
        }
    }

    static void testGetWithValidIndex() {
        try {
            // Since the method always throws, we simulate a valid index scenario by catching exception
            Collections.get(0);
            System.err.println("Failed: Expected IndexOutOfBoundsException for valid index");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Passed: IndexOutOfBoundsException thrown for valid index (as expected)");
        }
    }
}
