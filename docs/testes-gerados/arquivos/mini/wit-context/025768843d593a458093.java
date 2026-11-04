/*
 * @test
 * @summary Test PushbackInputStream.unread(int) for exception and normal behavior
 * @run main PushbackInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class PushbackInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testUnreadThrowsWhenStreamClosed();
        testUnreadThrowsWhenBufferFull();
        testUnreadNormalBehavior();
    }

    private static void testUnreadThrowsWhenStreamClosed() {
        byte[] data = {1, 2, 3};
        PushbackInputStream pbis = new PushbackInputStream(new ByteArrayInputStream(data), 2);
        try {
            pbis.close();
            pbis.unread(10);
            throw new RuntimeException("Expected IOException when unread on closed stream");
        } catch (IOException e) {
            // Expected
        }
    }

    private static void testUnreadThrowsWhenBufferFull() throws IOException {
        byte[] data = {1};
        PushbackInputStream pbis = new PushbackInputStream(new ByteArrayInputStream(data), 1);
        // unread once fills the buffer (pos moves from 1 to 0)
        pbis.unread(100);
        try {
            pbis.unread(101);
            throw new RuntimeException("Expected IOException when unread on full buffer");
        } catch (IOException e) {
            // Expected
        }
    }

    private static void testUnreadNormalBehavior() throws IOException {
        byte[] data = {42};
        PushbackInputStream pbis = new PushbackInputStream(new ByteArrayInputStream(data), 2);
        int readByte = pbis.read();
        if (readByte != 42) {
            throw new RuntimeException("Unexpected byte read before unread: " + readByte);
        }
        pbis.unread(99);
        int unreadByte = pbis.read();
        if (unreadByte != 99) {
            throw new RuntimeException("Unread byte not returned by read(): " + unreadByte);
        }
    }
}
