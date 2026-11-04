/*
 * @test
 * @summary Test for Collections.nCopies(int, Object) method, focusing on exception for invalid input.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testNCopiesNegative();
        testNCopiesZero();
        testNCopiesPositive();
    }

    private static void testNCopiesNegative() {
        try {
            List<Object> list = Collections.nCopies(-1, "test");
            System.err.println("Expected IllegalArgumentException for n = -1, but got: " + list);
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: IllegalArgumentException thrown for n = -1");
        }
    }

    private static void testNCopiesZero() {
        List<Object> list = Collections.nCopies(0, "test");
        if (list.isEmpty()) {
            System.out.println("Passed: Empty list returned for n = 0");
        } else {
            System.err.println("Failed: List not empty for n = 0");
        }
    }

    private static void testNCopiesPositive() {
        List<String> list = Collections.nCopies(3, "abc");
        if (list.size() == 3 && list.get(0).equals("abc") && list.get(1).equals("abc") && list.get(2).equals("abc")) {
            System.out.println("Passed: List with 3 copies created successfully");
        } else {
            System.err.println("Failed: List content or size mismatch for n = 3");
        }
    }
}
