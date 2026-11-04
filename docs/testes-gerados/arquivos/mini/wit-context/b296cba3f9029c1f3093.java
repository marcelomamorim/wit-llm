/*
 * @test
 * @summary Test java.lang.String.substring(int) for exception and normal cases
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testSubstringBeginIndexNegative();
        testSubstringBeginIndexTooLarge();
        testSubstringBeginIndexZero();
        testSubstringNormal();
    }

    private static void testSubstringBeginIndexNegative() {
        String s = "abc";
        try {
            s.substring(-1);
            throw new Error("Expected StringIndexOutOfBoundsException for negative beginIndex");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testSubstringBeginIndexTooLarge() {
        String s = "abc";
        try {
            s.substring(4); // length is 3, so 4 > length
            throw new Error("Expected StringIndexOutOfBoundsException for beginIndex > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testSubstringBeginIndexZero() {
        String s = "abc";
        String sub = s.substring(0);
        if (sub != s) {
            throw new Error("substring(0) should return the same String instance");
        }
    }

    private static void testSubstringNormal() {
        String s = "abcdef";
        String sub = s.substring(2);
        if (!sub.equals("cdef")) {
            throw new Error("substring(2) should return 'cdef', got: " + sub);
        }
    }
}
