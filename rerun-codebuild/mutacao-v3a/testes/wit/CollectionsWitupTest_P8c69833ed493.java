/*
 * @test
 * @summary Tests for java.util.Collections.nCopies(int, Object)
 * @run main CollectionsWitupTest_P8c69833ed493
 */

import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest_P8c69833ed493 {

    public static void main(String[] args) {
        testNCopiesWithNegativeN();
        testNCopiesWithZeroN();
        testNCopiesWithPositiveN();
    }

    private static void testNCopiesWithNegativeN() {
        try {
            List<Object> result = Collections.nCopies(-1, "test");
            // Expect exception, if not, test fails
            System.err.println("Failed: Expected IllegalArgumentException for n < 0");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: IllegalArgumentException thrown for n < 0");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type: " + e);
        }
    }

    private static void testNCopiesWithZeroN() {
        List<Object> result = Collections.nCopies(0, "test");
        if (result != null && result.isEmpty()) {
            System.out.println("Passed: nCopies with n=0 returns empty list");
        } else {
            System.err.println("Failed: nCopies with n=0 did not return empty list");
        }
    }

    private static void testNCopiesWithPositiveN() {
        int n = 3;
        String obj = "test";
        List<String> result = Collections.nCopies(n, obj);
        if (result.size() == n && result.get(0).equals(obj)) {
            boolean allEqual = true;
            for (String s : result) {
                if (!s.equals(obj)) {
                    allEqual = false;
                    break;
                }
            }
            if (allEqual) {
                System.out.println("Passed: nCopies with positive n returns list with repeated objects");
            } else {
                System.err.println("Failed: Not all objects in list are equal to the original");
            }
        } else {
            System.err.println("Failed: nCopies with positive n did not produce expected list");
        }
    }
}
