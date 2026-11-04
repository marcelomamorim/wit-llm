/*
 * @test
 * @summary Test StringBuffer.insert(int, Object) method
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testInsertWithNonNullObject();
        testInsertWithNullObject();
        testInsertAtBeginning();
        testInsertAtEnd();
        testInsertWithEmptyString();
    }

    private static void testInsertWithNonNullObject() {
        StringBuffer sb = new StringBuffer("HelloWorld");
        sb.insert(5, " ");
        sb.insert(6, Integer.valueOf(123));
        String result = sb.toString();
        if (!result.equals("Hello 123World")) {
            throw new AssertionError("Expected 'Hello 123World' but got '" + result + "'");
        }
    }

    private static void testInsertWithNullObject() {
        StringBuffer sb = new StringBuffer("abc");
        sb.insert(1, (Object) null);
        String result = sb.toString();
        if (!result.equals("anullbc")) {
            throw new AssertionError("Expected 'anullbc' but got '" + result + "'");
        }
    }

    private static void testInsertAtBeginning() {
        StringBuffer sb = new StringBuffer("world");
        sb.insert(0, "Hello ");
        String result = sb.toString();
        if (!result.equals("Hello world")) {
            throw new AssertionError("Expected 'Hello world' but got '" + result + "'");
        }
    }

    private static void testInsertAtEnd() {
        StringBuffer sb = new StringBuffer("Hello");
        sb.insert(sb.length(), " World");
        String result = sb.toString();
        if (!result.equals("Hello World")) {
            throw new AssertionError("Expected 'Hello World' but got '" + result + "'");
        }
    }

    private static void testInsertWithEmptyString() {
        StringBuffer sb = new StringBuffer("abc");
        sb.insert(2, "");
        String result = sb.toString();
        if (!result.equals("abc")) {
            throw new AssertionError("Expected 'abc' but got '" + result + "'");
        }
    }
}
