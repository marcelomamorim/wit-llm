/*
 * @test
 * @summary Test StringBuffer.insert(int, String) for valid and invalid offsets
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testInsertValid();
        testInsertNullString();
        testInsertInvalidOffsets();
    }

    private static void testInsertValid() {
        StringBuffer sb = new StringBuffer("HelloWorld");
        StringBuffer result = sb.insert(5, " ");
        if (!"Hello World".equals(sb.toString())) {
            throw new AssertionError("Insert at middle failed: " + sb.toString());
        }
        if (result != sb) {
            throw new AssertionError("Insert should return this instance");
        }
    }

    private static void testInsertNullString() {
        StringBuffer sb = new StringBuffer("abc");
        StringBuffer result = sb.insert(1, (String) null);
        // In JDK 11, inserting null String inserts "null" string
        if (!"anullbc".equals(sb.toString())) {
            throw new AssertionError("Insert null string failed: " + sb.toString());
        }
        if (result != sb) {
            throw new AssertionError("Insert should return this instance");
        }
    }

    private static void testInsertInvalidOffsets() {
        StringBuffer sb = new StringBuffer("test");
        // offset < 0 should throw StringIndexOutOfBoundsException
        try {
            sb.insert(-1, "fail");
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset -1");
        } catch (StringIndexOutOfBoundsException expected) {
        }

        // offset > length should throw StringIndexOutOfBoundsException
        try {
            sb.insert(sb.length() + 1, "fail");
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset > length");
        } catch (StringIndexOutOfBoundsException expected) {
        }
    }
}
