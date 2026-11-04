/*
 * @test
 * @summary Test for StringBuffer.insert(int, float) method, focusing on normal and exception cases.
 * @run main StringBufferWitupTest
 */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testInsertValidOffset();
        testInsertNegativeOffset();
        testInsertOffsetGreaterThanLength();
    }

    private static void testInsertValidOffset() {
        StringBuffer sb = new StringBuffer("Hello");
        try {
            sb.insert(5, 3.14f);
            if (!sb.toString().equals("Hello3.14")) {
                throw new AssertionError("Expected 'Hello3.14', but got: " + sb.toString());
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid offset: " + e);
        }
    }

    private static void testInsertNegativeOffset() {
        StringBuffer sb = new StringBuffer("Test");
        try {
            sb.insert(-1, 2.71f);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset, but no exception was thrown.");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    private static void testInsertOffsetGreaterThanLength() {
        StringBuffer sb = new StringBuffer("Data");
        try {
            sb.insert(10, 1.618f);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset > length, but no exception was thrown.");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
