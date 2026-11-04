/*
 * @test
 * @summary Test for StringBuffer.insert(int, double) method, focusing on exception behavior.
 * @run main StringBufferWitupTest
 */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testInsertWithInvalidOffset();
        testInsertWithValidOffset();
    }

    private static void testInsertWithInvalidOffset() {
        StringBuffer sb = new StringBuffer("test");
        double value = 1.23;
        try {
            sb.insert(-1, value);
            System.out.println("Failed: Expected StringIndexOutOfBoundsException for negative offset.");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Passed: Caught expected exception for negative offset.");
        }
        try {
            sb.insert(sb.length() + 1, value);
            System.out.println("Failed: Expected StringIndexOutOfBoundsException for offset beyond length.");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Passed: Caught expected exception for offset beyond length.");
        }
    }

    private static void testInsertWithValidOffset() {
        StringBuffer sb = new StringBuffer("hello");
        double value = 3.14;
        try {
            sb.insert(5, value); // insert at end
            if (sb.toString().contains("3.14")) {
                System.out.println("Passed: Inserted double at valid offset.");
            } else {
                System.out.println("Failed: Double not inserted correctly.");
            }
        } catch (Exception e) {
            System.out.println("Failed: Unexpected exception: " + e);
        }
        try {
            sb.insert(0, value); // insert at start
            if (sb.toString().startsWith("3.14")) {
                System.out.println("Passed: Inserted double at start.");
            } else {
                System.out.println("Failed: Double not inserted at start.");
            }
        } catch (Exception e) {
            System.out.println("Failed: Unexpected exception: " + e);
        }
    }
}
