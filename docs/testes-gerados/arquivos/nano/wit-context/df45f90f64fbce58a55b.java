/*
 * @test
 * @summary Tests for StringBuffer.insert(int, Object) method, including exception paths.
 * @run main StringBufferWitupTest
 */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testInsertWithValidOffset();
        testInsertWithNegativeOffset();
        testInsertWithOffsetExceedingLength();
    }

    private static void testInsertWithValidOffset() {
        StringBuffer sb = new StringBuffer("Hello");
        Object obj = "World";
        try {
            sb.insert(5, obj);
            if (!sb.toString().equals("HelloWorld")) {
                throw new AssertionError("Expected 'HelloWorld', got: " + sb.toString());
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid offset: " + e);
        }
    }

    private static void testInsertWithNegativeOffset() {
        StringBuffer sb = new StringBuffer("Hello");
        Object obj = "World";
        try {
            sb.insert(-1, obj);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    private static void testInsertWithOffsetExceedingLength() {
        StringBuffer sb = new StringBuffer("Hello");
        Object obj = "World";
        try {
            sb.insert(10, obj);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset exceeding length");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
