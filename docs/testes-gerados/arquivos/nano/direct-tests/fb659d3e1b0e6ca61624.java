/*
 * @test
 * @summary Test ByteArrayOutputStream's toString(int hibyte) method for correct string conversion.
 * @run main ByteArrayOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;

public class ByteArrayOutputStreamWitupTest {

    public static void main(String[] args) {
        testToStringWithHibyte();
        testToStringWithHibyteEmptyBuffer();
        testToStringWithHibytePartialBuffer();
    }

    private static void testToStringWithHibyte() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String testString = "Hello, World!";
        try {
            baos.write(testString.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception during write", e);
        }
        String result = baos.toString(0);
        if (!result.equals(testString)) {
            throw new AssertionError("Expected: '" + testString + "', but got: '" + result + "'");
        }
    }

    private static void testToStringWithHibyteEmptyBuffer() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String result = baos.toString(0);
        if (!result.isEmpty()) {
            throw new AssertionError("Expected empty string, but got: '" + result + "'");
        }
    }

    private static void testToStringWithHibytePartialBuffer() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String testString = "Test";
        try {
            baos.write(testString.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception during write", e);
        }
        String result = baos.toString(0);
        if (!result.equals(testString)) {
            throw new AssertionError("Expected: '" + testString + "', but got: '" + result + "'");
        }
    }
}
