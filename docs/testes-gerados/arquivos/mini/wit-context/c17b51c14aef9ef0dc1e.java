/*
 * @test
 * @summary Test java.util.Collections.subList(int, int) for exception and normal cases
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testSubListExceptions();
        testSubListNormal();
    }

    private static void testSubListExceptions() {
        // fromIndex < 0 -> IndexOutOfBoundsException
        try {
            Collections.<String>nCopies(5, "x").subList(-1, 3);
            throw new RuntimeException("Expected IndexOutOfBoundsException for fromIndex < 0");
        } catch (IndexOutOfBoundsException expected) {
        }

        // toIndex > n -> IndexOutOfBoundsException
        try {
            Collections.<String>nCopies(5, "x").subList(1, 6);
            throw new RuntimeException("Expected IndexOutOfBoundsException for toIndex > n");
        } catch (IndexOutOfBoundsException expected) {
        }

        // fromIndex > toIndex -> IllegalArgumentException
        try {
            Collections.<String>nCopies(5, "x").subList(4, 3);
            throw new RuntimeException("Expected IllegalArgumentException for fromIndex > toIndex");
        } catch (IllegalArgumentException expected) {
        }
    }

    private static void testSubListNormal() {
        List<String> list = Collections.<String>nCopies(5, "y");
        List<String> sub = list.subList(1, 4);
        if (sub.size() != 3) {
            throw new RuntimeException("subList size expected 3 but was " + sub.size());
        }
        for (String s : sub) {
            if (!"y".equals(s)) {
                throw new RuntimeException("subList element expected 'y' but was " + s);
            }
        }
    }
}
