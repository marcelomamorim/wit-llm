/*
 * @test
 * @summary Test PushbackInputStream.unread(byte[], int, int) behavior
 * @run main PushbackInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.PushbackInputStream;
import java.io.IOException;

public class PushbackInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testUnreadNormal();
        testUnreadBufferOverflow();
        testUnreadClosedStream();
    }

    private static void testUnreadNormal() throws IOException {
        byte[] input = {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(input);
        PushbackInputStream pbis = new PushbackInputStream(bais, 5);

        // Read two bytes
        int first = pbis.read();
        int second = pbis.read();

        if (first != 1 || second != 2) {
            throw new RuntimeException("Initial read failed: expected 1 and 2, got " + first + " and " + second);
        }

        // Unread bytes 2 and 3
        byte[] toUnread = {2, 3};
        pbis.unread(toUnread, 0, 2);

        // Now read again, should get 2 and 3
        int u1 = pbis.read();
        int u2 = pbis.read();

        if (u1 != 2 || u2 != 3) {
            throw new RuntimeException("Unread bytes not returned correctly: expected 2 and 3, got " + u1 + " and " + u2);
        }

        pbis.close();
    }

    private static void testUnreadBufferOverflow() throws IOException {
        byte[] input = {10, 20, 30};
        ByteArrayInputStream bais = new ByteArrayInputStream(input);
        PushbackInputStream pbis = new PushbackInputStream(bais, 2);

        // Attempt to unread more bytes than buffer size
        byte[] toUnread = {1, 2, 3};
        boolean threw = false;
        try {
            pbis.unread(toUnread, 0, 3);
        } catch (IOException e) {
            if (!"Push back buffer is full".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IOException message: " + e.getMessage());
            }
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IOException not thrown when unread buffer overflow");
        }

        pbis.close();
    }

    private static void testUnreadClosedStream() throws IOException {
        byte[] input = {1};
        ByteArrayInputStream bais = new ByteArrayInputStream(input);
        PushbackInputStream pbis = new PushbackInputStream(bais, 1);
        pbis.close();

        boolean threw = false;
        try {
            pbis.unread(new byte[]{1}, 0, 1);
        } catch (IOException e) {
            if (!"Stream closed".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IOException message on closed stream: " + e.getMessage());
            }
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IOException not thrown when unread on closed stream");
        }
    }
}
