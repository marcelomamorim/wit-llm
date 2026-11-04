/*
 * @test
 * @summary Test for ByteArrayOutputStream.toString(int) method, including edge cases.
 * @run main ByteArrayOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;

public class ByteArrayOutputStreamWitupTest {

    public static void main(String[] args) {
        testToStringWithValidData();
        testToStringWithEmptyBuffer();
        testToStringWithNegativeCount();
        testToStringWithCountExceedingBuffer();
    }

    static void testToStringWithValidData() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] data = {65, 66, 67, 68}; // ASCII for ABCD
        try {
            baos.write(data);
            String result = baos.toString(0);
            if (!"ABCD".equals(result)) {
                throw new AssertionError("Expected 'ABCD', got: " + result);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testToStringWithValidData", e);
        }
    }

    static void testToStringWithEmptyBuffer() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            String result = baos.toString(0);
            if (!"".equals(result)) {
                throw new AssertionError("Expected empty string, got: " + result);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testToStringWithEmptyBuffer", e);
        }
    }

    static void testToStringWithNegativeCount() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] data = {65, 66, 67};
        try {
            baos.write(data);
            // Simulate invalid count by calling toString with a negative hibyte
            baos.toString(-1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative hibyte");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testToStringWithNegativeCount", e);
        }
    }

    static void testToStringWithCountExceedingBuffer() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] data = {65, 66};
        try {
            baos.write(data);
            // Use hibyte=0, count=3 (exceeds buffer length)
            baos.toString(0);
            // Should throw StringIndexOutOfBoundsException
            throw new AssertionError("Expected StringIndexOutOfBoundsException for count exceeding buffer length");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testToStringWithCountExceedingBuffer", e);
        }
    }
}
