/* @test    @summary Test insert method of StringBuffer for valid and invalid inputs    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testInsertValid();
        testInsertInvalid();
    }

    private static void testInsertValid() {
        StringBuffer sb = new StringBuffer("start");
        sb.insert(5, 123);
        if (!"start123".equals(sb.toString())) {
            throw new AssertionError("Expected 'start123', but got: " + sb.toString());
        }
    }

    private static void testInsertInvalid() {
        StringBuffer sb = new StringBuffer("test");
        try {
            sb.insert(-1, 456);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        try {
            sb.insert(100, 789);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset beyond length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
