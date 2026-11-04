/* @test    @summary Test StringBuilder.insert(int, Object) method for correct insertion and chaining    @run main StringBuilderWitupTest */

import java.lang.StringBuilder;

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertWithObject();
        testInsertWithNullObject();
        testInsertAtBeginning();
        testInsertAtEnd();
        testChainingInsert();
    }

    private static void testInsertWithObject() {
        StringBuilder sb = new StringBuilder("Hello");
        Object obj = 123;
        sb.insert(5, obj);
        String result = sb.toString();
        if (!"Hello123".equals(result)) {
            throw new AssertionError("Expected 'Hello123' but got: " + result);
        }
    }

    private static void testInsertWithNullObject() {
        StringBuilder sb = new StringBuilder("Test");
        Object obj = null;
        sb.insert(4, obj);
        String result = sb.toString();
        if (!"Testnull".equals(result)) {
            throw new AssertionError("Expected 'Testnull' but got: " + result);
        }
    }

    private static void testInsertAtBeginning() {
        StringBuilder sb = new StringBuilder("World");
        Object obj = "Hello ";
        sb.insert(0, obj);
        String result = sb.toString();
        if (!"Hello World".equals(result)) {
            throw new AssertionError("Expected 'Hello World' but got: " + result);
        }
    }

    private static void testInsertAtEnd() {
        StringBuilder sb = new StringBuilder("Good");
        Object obj = " Morning";
        sb.insert(sb.length(), obj);
        String result = sb.toString();
        if (!"Good Morning".equals(result)) {
            throw new AssertionError("Expected 'Good Morning' but got: " + result);
        }
    }

    private static void testChainingInsert() {
        StringBuilder sb = new StringBuilder("Start");
        Object obj1 = " Middle";
        Object obj2 = " End";
        StringBuilder resultSb = sb.insert(5, obj1).insert(sb.length(), obj2);
        String result = resultSb.toString();
        if (!"Start Middle End".equals(result)) {
            throw new AssertionError("Expected 'Start Middle End' but got: " + result);
        }
    }
}
