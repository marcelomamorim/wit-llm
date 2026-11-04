/*
 * @test
 * @summary Test for StringBuffer.replace to cover index out of bounds scenarios
 * @run main StringBufferWitupTest
 */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testReplaceWithStartGreaterThanEnd();
        testReplaceWithStartLessThanZero();
    }

    private static void testReplaceWithStartGreaterThanEnd() {
        StringBuffer sb = new StringBuffer("Hello World");
        try {
            sb.replace(6, 5, "Test");
            System.out.println("Failed: Expected StringIndexOutOfBoundsException for start > end");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Passed: Caught expected exception for start > end");
        }
    }

    private static void testReplaceWithStartLessThanZero() {
        StringBuffer sb = new StringBuffer("Hello World");
        try {
            sb.replace(-1, 5, "Test");
            System.out.println("Failed: Expected StringIndexOutOfBoundsException for start < 0");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Passed: Caught expected exception for start < 0");
        }
    }
}
