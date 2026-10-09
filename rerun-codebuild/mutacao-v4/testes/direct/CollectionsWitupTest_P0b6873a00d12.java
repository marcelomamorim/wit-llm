/* @test
   @summary Test for Collections.nCopies method with valid and invalid inputs.
   @run main CollectionsWitupTest_P0b6873a00d12
 */
import java.util.List;
import java.util.Collections;
public class CollectionsWitupTest_P0b6873a00d12 {
    public static void main(String[] args) {
        testNCopiesWithPositiveNumber();
        testNCopiesWithZero();
        testNCopiesWithNegativeNumber();
    }

    static void testNCopiesWithPositiveNumber() {
        int n = 3;
        String o = "test";
        List<String> list = Collections.nCopies(n, o);
        if (list.size() != n) {
            throw new AssertionError("Expected size " + n + " but got " + list.size());
        }
        for (String item : list) {
            if (!item.equals(o)) {
                throw new AssertionError("Expected item to be '" + o + "' but got '" + item + "'");
            }
        }
    }

    static void testNCopiesWithZero() {
        int n = 0;
        String o = "zero";
        List<String> list = Collections.nCopies(n, o);
        if (list.size() != 0) {
            throw new AssertionError("Expected size 0 but got " + list.size());
        }
    }

    static void testNCopiesWithNegativeNumber() {
        int n = -1;
        String o = "negative";
        try {
            Collections.nCopies(n, o);
            throw new AssertionError("Expected IllegalArgumentException for n = " + n);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
