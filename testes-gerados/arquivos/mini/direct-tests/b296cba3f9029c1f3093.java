/*
 * @test
 * @summary Test java.lang.String substring(int beginIndex) method
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testSubstringValidBeginIndex();
        testSubstringBeginIndexZeroReturnsSameInstance();
        testSubstringBeginIndexNegativeThrows();
        testSubstringBeginIndexGreaterThanLengthThrows();
    }

    private static void testSubstringValidBeginIndex() {
        String s = "Hello, world!";
        String sub = s.substring(7);
        if (!sub.equals("world!")) {
            throw new Error("Expected substring 'world!' but got '" + sub + "'");
        }
    }

    private static void testSubstringBeginIndexZeroReturnsSameInstance() {
        String s = "test";
        String sub = s.substring(0);
        if (sub != s) {
            throw new Error("Expected substring(0) to return the same instance");
        }
    }

    private static void testSubstringBeginIndexNegativeThrows() {
        String s = "abc";
        try {
            s.substring(-1);
            throw new Error("Expected StringIndexOutOfBoundsException for negative beginIndex");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testSubstringBeginIndexGreaterThanLengthThrows() {
        String s = "abc";
        try {
            s.substring(4);
            throw new Error("Expected StringIndexOutOfBoundsException for beginIndex > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
