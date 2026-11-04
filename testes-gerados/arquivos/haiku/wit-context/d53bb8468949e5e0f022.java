/*
 * @test
 * @summary Verify that PushbackInputStream.reset() always throws IOException with the documented mark/reset message
 * @run main PushbackInputStreamWitupTest
 */
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class PushbackInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testResetThrowsOnFreshStream();
        testResetThrowsAfterRead();
        testResetThrowsAfterUnread();
        testResetThrowsWithDefaultConstructor();
    }

    static void expectResetFailure(PushbackInputStream p) {
        try {
            p.reset();
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("reset() did not throw IOException");
    }

    static void testResetThrowsOnFreshStream() {
        byte[] data = {1, 2, 3};
        PushbackInputStream p = new PushbackInputStream(new ByteArrayInputStream(data), 2);
        expectResetFailure(p);
    }

    static void testResetThrowsAfterRead() throws IOException {
        byte[] data = {1, 2, 3};
        PushbackInputStream p = new PushbackInputStream(new ByteArrayInputStream(data), 2);
        if (p.read() != 1) {
            throw new RuntimeException("unexpected first byte");
        }
        expectResetFailure(p);
    }

    static void testResetThrowsAfterUnread() throws IOException {
        byte[] data = {1, 2, 3};
        PushbackInputStream p = new PushbackInputStream(new ByteArrayInputStream(data), 2);
        int b = p.read();
        p.unread(b);
        expectResetFailure(p);
    }

    static void testResetThrowsWithDefaultConstructor() {
        byte[] data = {4, 5};
        PushbackInputStream p = new PushbackInputStream(new ByteArrayInputStream(data));
        expectResetFailure(p);
    }
}
