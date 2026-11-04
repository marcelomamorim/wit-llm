/* @test    @summary Test StringBuffer.insert(int, double) method for various double values    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testInsertDoubleAtStart();
        testInsertDoubleAtMiddle();
        testInsertDoubleAtEnd();
        testInsertNegativeDouble();
        testInsertZeroDouble();
        testInsertLargeDouble();
    }

    static void testInsertDoubleAtStart() {
        StringBuffer sb = new StringBuffer("Hello");
        double value = 123.456;
        sb.insert(0, value);
        String expected = Double.toString(value) + "Hello";
        assert sb.toString().equals(expected) : "Failed at insert at start: " + sb;
    }

    static void testInsertDoubleAtMiddle() {
        StringBuffer sb = new StringBuffer("HelloWorld");
        double value = 789.012;
        int index = 5;
        sb.insert(index, value);
        String expected = "Hello" + Double.toString(value) + "World";
        assert sb.toString().equals(expected) : "Failed at insert at middle: " + sb;
    }

    static void testInsertDoubleAtEnd() {
        StringBuffer sb = new StringBuffer("Test");
        double value = 3.14159;
        sb.insert(sb.length(), value);
        String expected = "Test" + Double.toString(value);
        assert sb.toString().equals(expected) : "Failed at insert at end: " + sb;
    }

    static void testInsertNegativeDouble() {
        StringBuffer sb = new StringBuffer("Negative");
        double value = -987.654;
        sb.insert(8, value);
        String expected = "Negative" + Double.toString(value);
        assert sb.toString().equals(expected) : "Failed at insert negative double: " + sb;
    }

    static void testInsertZeroDouble() {
        StringBuffer sb = new StringBuffer("Zero");
        double value = 0.0;
        sb.insert(4, value);
        String expected = "Zero" + Double.toString(value);
        assert sb.toString().equals(expected) : "Failed at insert zero double: " + sb;
    }

    static void testInsertLargeDouble() {
        StringBuffer sb = new StringBuffer("Large");
        double value = 1.7976931348623157E308; // Max double
        sb.insert(5, value);
        String expected = "Large" + Double.toString(value);
        assert sb.toString().equals(expected) : "Failed at insert large double: " + sb;
    }
}
