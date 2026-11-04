/*
 * @test
 * @summary Verifies bounds checks and result contents of Collections.nCopies(...).subList(...)
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        List<String> copies = Collections.nCopies(5, "x");

        // Valid range: size and contents
        List<String> sub = copies.subList(1, 4);
        check(sub.size() == 3, "size of subList(1,4) expected 3 but was " + sub.size());
        for (String s : sub) {
            check("x".equals(s), "unexpected element " + s);
        }

        // Empty range
        check(copies.subList(2, 2).isEmpty(), "subList(2,2) should be empty");

        // Full range
        check(copies.subList(0, 5).size() == 5, "subList(0,5) should have size 5");

        // Negative fromIndex
        expectIndexOutOfBounds(copies, -1, 2);

        // toIndex greater than size
        expectIndexOutOfBounds(copies, 0, 6);

        // fromIndex greater than toIndex
        expectIllegalArgument(copies, 3, 2);
    }

    private static void expectIndexOutOfBounds(List<String> list, int from, int to) {
        try {
            list.subList(from, to);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("IndexOutOfBoundsException expected for subList(" + from + "," + to + ")");
    }

    private static void expectIllegalArgument(List<String> list, int from, int to) {
        try {
            list.subList(from, to);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for subList(" + from + "," + to + ")");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
