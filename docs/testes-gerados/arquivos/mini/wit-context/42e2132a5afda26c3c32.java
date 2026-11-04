/*
 * @test
 * @summary Test StringBuffer.insert(int, char[]) for valid and invalid offsets
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testInsertValid();
        testInsertInvalidNegativeOffset();
        testInsertInvalidOffsetGreaterThanLength();
    }

    private static void testInsertValid() {
        StringBuffer sb = new StringBuffer("hello");
        char[] chars = {'w', 'o', 'r', 'l', 'd'};
        sb.insert(5, chars); // append at end
        if (!sb.toString().equals("helloworld")) {
            throw new Error("testInsertValid failed: expected 'helloworld' but got '" + sb.toString() + "'");
        }
        sb = new StringBuffer("hello");
        sb.insert(0, chars); // insert at start
        if (!sb.toString().equals("worldhello")) {
            throw new Error("testInsertValid failed: expected 'worldhello' but got '" + sb.toString() + "'");
        }
        sb = new StringBuffer("hello");
        sb.insert(3, chars); // insert in middle
        if (!sb.toString().equals("helworldlo")) {
            throw new Error("testInsertValid failed: expected 'helworldlo' but got '" + sb.toString() + "'");
        }
    }

    private static void testInsertInvalidNegativeOffset() {
        StringBuffer sb = new StringBuffer("test");
        char[] chars = {'a', 'b'};
        try {
            sb.insert(-1, chars);
            throw new Error("testInsertInvalidNegativeOffset failed: expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertInvalidOffsetGreaterThanLength() {
        StringBuffer sb = new StringBuffer("test");
        char[] chars = {'a', 'b'};
        try {
            sb.insert(sb.length() + 1, chars);
            throw new Error("testInsertInvalidOffsetGreaterThanLength failed: expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
