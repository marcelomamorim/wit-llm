/* @test    @summary Test for BufferedInputStream constructor with InputStream parameter.    @run main BufferedInputStreamWitupTest */

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.BufferedInputStream;

public class BufferedInputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidInput();
        testConstructorWithInvalidBufferSize();
    }

    private static void testConstructorWithValidInput() {
        InputStream input = new ByteArrayInputStream(new byte[] {1, 2, 3});
        BufferedInputStream bis = new BufferedInputStream(input);
        if (bis == null) {
            throw new AssertionError("BufferedInputStream instance is null");
        }
        System.out.println("testConstructorWithValidInput passed");
    }

    private static void testConstructorWithInvalidBufferSize() {
        InputStream input = new ByteArrayInputStream(new byte[] {1, 2, 3});
        try {
            // For JDK 11, the constructor with size is public, but passing 0 or negative should throw IllegalArgumentException
            BufferedInputStream bis = new BufferedInputStream(input, 0);
            throw new AssertionError("Expected IllegalArgumentException for buffer size 0");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected IllegalArgumentException for buffer size 0");
        }
        try {
            BufferedInputStream bis = new BufferedInputStream(input, -1);
            throw new AssertionError("Expected IllegalArgumentException for negative buffer size");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected IllegalArgumentException for negative buffer size");
        }
        System.out.println("testConstructorWithInvalidBufferSize passed");
    }
}
