/*
 * @test
 * @summary Basic tests for StringBuffer.insert(int, String) method
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testInsertAtBeginning();
        testInsertAtMiddle();
        testInsertAtEnd();
        testInsertNullString();
        testInsertEmptyString();
    }

    private static void testInsertAtBeginning() {
        StringBuffer sb = new StringBuffer("world");
        sb.insert(0, "hello ");
        String result = sb.toString();
        if (!"hello world".equals(result)) {
            throw new RuntimeException("testInsertAtBeginning failed: got '" + result + "'");
        }
    }

    private static void testInsertAtMiddle() {
        StringBuffer sb = new StringBuffer("helloworld");
        sb.insert(5, " ");
        String result = sb.toString();
        if (!"hello world".equals(result)) {
            throw new RuntimeException("testInsertAtMiddle failed: got '" + result + "'");
        }
    }

    private static void testInsertAtEnd() {
        StringBuffer sb = new StringBuffer("hello");
        sb.insert(sb.length(), " world");
        String result = sb.toString();
        if (!"hello world".equals(result)) {
            throw new RuntimeException("testInsertAtEnd failed: got '" + result + "'");
        }
    }

    private static void testInsertNullString() {
        StringBuffer sb = new StringBuffer("hello");
        sb.insert(5, (String) null);
        String result = sb.toString();
        if (!"hellonull".equals(result)) {
            throw new RuntimeException("testInsertNullString failed: got '" + result + "'");
        }
    }

    private static void testInsertEmptyString() {
        StringBuffer sb = new StringBuffer("hello");
        sb.insert(3, "");
        String result = sb.toString();
        if (!"hello".equals(result)) {
            throw new RuntimeException("testInsertEmptyString failed: got '" + result + "'");
        }
    }
}
