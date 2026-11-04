/* @test    @summary Test insert method of StringBuilder for valid and invalid inputs    @run main StringBuilderWitupTest */

import java.lang.StringBuilder;

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertValid();
        testInsertOffsetNegative();
        testInsertOffsetExceedsLength();
    }

    private static void testInsertValid() {
        StringBuilder sb = new StringBuilder("start");
        char[] chars = {'l', 'e'};
        sb.insert(4, chars);
        if (!"starle".equals(sb.toString())) {
            throw new AssertionError("Expected 'starle', but got: " + sb.toString());
        }
    }

    private static void testInsertOffsetNegative() {
        StringBuilder sb = new StringBuilder("test");
        char[] chars = {'x'};
        try {
            sb.insert(-1, chars);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertOffsetExceedsLength() {
        StringBuilder sb = new StringBuilder("test");
        char[] chars = {'x'};
        try {
            sb.insert(10, chars);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset beyond length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
