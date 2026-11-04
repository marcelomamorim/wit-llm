/* @test    @summary Test for StringUTF16.checkBoundsBeginEnd with valid and invalid bounds    @run main StringUTF16WitupTest */

import java.lang.StringUTF16;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testCheckBoundsBeginEndValid();
        testCheckBoundsBeginEndInvalidNegativeBegin();
        testCheckBoundsBeginEndInvalidBeginGreaterThanEnd();
        testCheckBoundsBeginEndInvalidEndExceedsLength();
    }

    static void testCheckBoundsBeginEndValid() {
        byte[] data = new byte[10];
        try {
            StringUTF16.checkBoundsBeginEnd(0, 5, data);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid bounds", e);
        }
    }

    static void testCheckBoundsBeginEndInvalidNegativeBegin() {
        byte[] data = new byte[10];
        try {
            StringUTF16.checkBoundsBeginEnd(-1, 5, data);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative begin");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testCheckBoundsBeginEndInvalidBeginGreaterThanEnd() {
        byte[] data = new byte[10];
        try {
            StringUTF16.checkBoundsBeginEnd(6, 5, data);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for begin > end");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testCheckBoundsBeginEndInvalidEndExceedsLength() {
        byte[] data = new byte[10];
        try {
            StringUTF16.checkBoundsBeginEnd(0, 11, data);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for end > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
