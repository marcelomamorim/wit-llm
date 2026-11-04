/*
 * @test
 * @summary Test StringBuffer substring(int start, int end) method
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testSubstringBasic();
        testSubstringFull();
        testSubstringEmpty();
        testSubstringStartEqualsEnd();
        testSubstringNegativeIndices();
        testSubstringEndExceedsLength();
        testSubstringStartExceedsEnd();
    }

    private static void testSubstringBasic() {
        StringBuffer sb = new StringBuffer("Hello, World!");
        String sub = sb.substring(7, 12);
        if (!"World".equals(sub)) {
            throw new RuntimeException("Expected 'World' but got '" + sub + "'");
        }
    }

    private static void testSubstringFull() {
        StringBuffer sb = new StringBuffer("Hello");
        String sub = sb.substring(0, sb.length());
        if (!"Hello".equals(sub)) {
            throw new RuntimeException("Expected 'Hello' but got '" + sub + "'");
        }
    }

    private static void testSubstringEmpty() {
        StringBuffer sb = new StringBuffer("");
        String sub = sb.substring(0, 0);
        if (!"".equals(sub)) {
            throw new RuntimeException("Expected empty string but got '" + sub + "'");
        }
    }

    private static void testSubstringStartEqualsEnd() {
        StringBuffer sb = new StringBuffer("abc");
        String sub = sb.substring(2, 2);
        if (!"".equals(sub)) {
            throw new RuntimeException("Expected empty string but got '" + sub + "'");
        }
    }

    private static void testSubstringNegativeIndices() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.substring(-1, 2);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for negative start index");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        try {
            sb.substring(0, -1);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for negative end index");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testSubstringEndExceedsLength() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.substring(0, 4);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for end index > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testSubstringStartExceedsEnd() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.substring(2, 1);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for start > end");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
