/*
 * @test
 * @summary Test for reset() method in PushbackInputStream
 * @run main PushbackInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class PushbackInputStreamWitupTest {

    public static void main(String[] args) {
        testResetThrowsIOException();
    }

    public static void testResetThrowsIOException() {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
        PushbackInputStream pushbackStream = new PushbackInputStream(byteStream);
        try {
            pushbackStream.reset();
            System.out.println("reset() did not throw IOException as expected.");
        } catch (IOException e) {
            if ("mark/reset not supported".equals(e.getMessage())) {
                System.out.println("reset() correctly threw IOException.");
            } else {
                System.out.println("reset() threw IOException with unexpected message: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("reset() threw unexpected exception: " + e);
        }
    }
}
