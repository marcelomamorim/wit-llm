/*
 * @test
 * @summary Test java.util.Collections.subList(int fromIndex, int toIndex) method
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testValidSubList();
        testFromIndexNegative();
        testToIndexGreaterThanSize();
        testFromIndexGreaterThanToIndex();
    }

    private static void testValidSubList() {
        Collections.CopiesList<String> copiesList = new Collections.CopiesList<>(5, "a");
        List<String> sub = copiesList.subList(1, 4);
        if (sub.size() != 3) {
            throw new AssertionError("Expected subList size 3 but got " + sub.size());
        }
        for (String s : sub) {
            if (!"a".equals(s)) {
                throw new AssertionError("Expected element 'a' but got " + s);
            }
        }
    }

    private static void testFromIndexNegative() {
        Collections.CopiesList<String> copiesList = new Collections.CopiesList<>(5, "a");
        try {
            copiesList.subList(-1, 3);
            throw new AssertionError("Expected IndexOutOfBoundsException for fromIndex < 0");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testToIndexGreaterThanSize() {
        Collections.CopiesList<String> copiesList = new Collections.CopiesList<>(5, "a");
        try {
            copiesList.subList(1, 6);
            throw new AssertionError("Expected IndexOutOfBoundsException for toIndex > size");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testFromIndexGreaterThanToIndex() {
        Collections.CopiesList<String> copiesList = new Collections.CopiesList<>(5, "a");
        try {
            copiesList.subList(4, 3);
            throw new AssertionError("Expected IllegalArgumentException for fromIndex > toIndex");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
