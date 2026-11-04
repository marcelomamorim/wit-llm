/*
 * @test
 * @summary Test StringBuffer.insert(int, Object) for valid and invalid offsets
 * @run main java.lang.StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testInsertWithValidObject();
        testInsertWithNegativeOffset();
        testInsertWithOffsetGreaterThanLength();
    }

    private static void testInsertWithValidObject() {
        StringBuffer sb = new StringBuffer("HelloWorld");
        StringBuffer result = sb.insert(5, " ");
        if (!"Hello World".equals(sb.toString())) {
            throw new Error("Failed: insert did not produce expected string");
        }
        if (result != sb) {
            throw new Error("Failed: insert did not return this instance");
        }
    }

    private static void testInsertWithNegativeOffset() {
        StringBuffer sb = new StringBuffer("Hello");
        try {
            sb.insert(-1, "X");
            throw new Error("Failed: insert with negative offset did not throw StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testInsertWithOffsetGreaterThanLength() {
        StringBuffer sb = new StringBuffer("Hello");
        try {
            sb.insert(sb.length() + 1, "X");
            throw new Error("Failed: insert with offset > length did not throw StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }
    }
}
