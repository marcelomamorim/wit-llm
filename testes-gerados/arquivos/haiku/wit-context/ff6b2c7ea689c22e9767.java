/*
 * @test
 * @summary Tests Collections.nCopies argument validation and observable list behavior
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testNegativeLengthThrows();
        testZeroLengthReturnsEmptyList();
        testPositiveLengthRepeatsElement();
        testNullElementIsAllowed();
        testResultSizeMatchesN();
        System.out.println("All tests passed");
    }

    static void testNegativeLengthThrows() {
        try {
            Collections.nCopies(-1, "x");
            throw new AssertionError("Expected IllegalArgumentException for n < 0");
        } catch (IllegalArgumentException expected) {
            if (!expected.getMessage().equals("List length = -1")) {
                throw new AssertionError("Unexpected message: " + expected.getMessage());
            }
        }
    }

    static void testZeroLengthReturnsEmptyList() {
        List<String> list = Collections.nCopies(0, "x");
        if (!list.isEmpty() || list.size() != 0) {
            throw new AssertionError("Expected empty list for n == 0");
        }
    }

    static void testPositiveLengthRepeatsElement() {
        List<String> list = Collections.nCopies(3, "ab");
        if (list.size() != 3) {
            throw new AssertionError("Expected size 3, got " + list.size());
        }
        for (String s : list) {
            if (!"ab".equals(s)) {
                throw new AssertionError("Unexpected element: " + s);
            }
        }
    }

    static void testNullElementIsAllowed() {
        List<Object> list = Collections.nCopies(2, null);
        if (list.size() != 2 || list.get(0) != null || list.get(1) != null) {
            throw new AssertionError("Expected two null elements");
        }
    }

    static void testResultSizeMatchesN() {
        int n = 17;
        List<Integer> list = Collections.nCopies(n, 5);
        if (list.size() != n) {
            throw new AssertionError("Expected size " + n + ", got " + list.size());
        }
        if (!list.contains(5) || list.contains(6)) {
            throw new AssertionError("Unexpected contents");
        }
    }
}
