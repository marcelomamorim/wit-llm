/*
 * @test
 * @summary PushbackInputStream.reset() always throws IOException with message "mark/reset not supported"
 * @run main PushbackInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class PushbackInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testResetThrowsIOExceptionWithDefaultBuffer();
        testResetThrowsIOExceptionWithCustomBuffer();
        testResetDoesNotAffectPushbackBuffer();
    }

    static void testResetThrowsIOExceptionWithDefaultBuffer() throws Exception {
        PushbackInputStream p = new PushbackInputStream(
                new ByteArrayInputStream(new byte[] {1, 2, 3}));
        try {
            p.reset();
            throw new RuntimeException("expected IOException from reset()");
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
    }

    static void testResetThrowsIOExceptionWithCustomBuffer() throws Exception {
        PushbackInputStream p = new PushbackInputStream(
                new ByteArrayInputStream(new byte[] {1, 2, 3}), 4);
        try {
            p.reset();
            throw new RuntimeException("expected IOException from reset()");
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
    }

    static void testResetDoesNotAffectPushbackBuffer() throws Exception {
        PushbackInputStream p = new PushbackInputStream(
                new ByteArrayInputStream(new byte[] {1, 2, 3}), 2);
        p.unread(9);
        try {
            p.reset();
            throw new RuntimeException("expected IOException from reset()");
        } catch (IOException e) {
            // expected
        }
        int b = p.read();
        if (b != 9) {
            throw new RuntimeException("expected pushed-back byte 9 but got " + b);
        }
    }
}
