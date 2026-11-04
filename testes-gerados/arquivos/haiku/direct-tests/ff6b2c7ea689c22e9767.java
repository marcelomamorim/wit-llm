/*
 * @test
 * @summary Basic observable behavior of Collections.nCopies, including the negative length check
 * @run main CollectionsWitupTest
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        List<String> l = Collections.nCopies(3, "a");
        check(l.size() == 3, "size");
        for (int i = 0; i < 3; i++) {
            check("a".equals(l.get(i)), "get " + i);
        }
        check(l.contains("a") && !l.contains("b"), "contains");

        check(Collections.nCopies(0, "x").isEmpty(), "zero length is empty");

        List<String> nul = Collections.nCopies(2, null);
        check(nul.size() == 2 && nul.get(0) == null && nul.get(1) == null, "null element");

        check(Collections.nCopies(2, "a").equals(Arrays.asList("a", "a")), "equals");
        check(Collections.nCopies(2, "a").hashCode() == Arrays.asList("a", "a").hashCode(), "hashCode");

        boolean thrown = false;
        try {
            Collections.nCopies(-1, "x");
        } catch (IllegalArgumentException e) {
            thrown = true;
            check("List length = -1".equals(e.getMessage()), "message");
        }
        check(thrown, "negative length must throw IllegalArgumentException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
