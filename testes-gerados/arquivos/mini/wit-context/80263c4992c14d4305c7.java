/*
 * @test
 * @summary Test PushbackInputStream.unread(byte[]) for correct exception and normal behavior
 * @run main PushbackInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class PushbackInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testUnreadNormal();
        testUnreadThrowsIOExceptionWhenStreamClosed();
        testUnreadThrowsIOExceptionWhenBufferOverflow();
    }

    private static void testUnreadNormal() throws IOException {
        byte[] input = {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(input);
        PushbackInputStream pbis = new PushbackInputStream(bais, 5);

        // Read some bytes
        int first = pbis.read(); // should be 1
        if (first != 1) {
            throw new AssertionError("Expected first byte 1 but got " + first);
        }

        // Unread a byte array smaller or equal to buffer size
        byte[] toUnread = {9, 8};
        pbis.unread(toUnread);

        // Now reading should return the unread bytes first
        int u1 = pbis.read();
        int u2 = pbis.read();
        if (u1 != 9 || u2 != 8) {
            throw new AssertionError("Unread bytes not returned correctly: " + u1 + ", " + u2);
        }
    }

    private static void testUnreadThrowsIOExceptionWhenStreamClosed() {
        byte[] input = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(input);
        PushbackInputStream pbis = new PushbackInputStream(bais, 3);

        try {
            pbis.close();
            pbis.unread(new byte[] {1});
            throw new AssertionError("Expected IOException when unread on closed stream");
        } catch (IOException e) {
            // Expected
        }
    }

    private static void testUnreadThrowsIOExceptionWhenBufferOverflow() throws IOException {
        byte[] input = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(input);
        PushbackInputStream pbis = new PushbackInputStream(bais, 2); // buffer size 2

        // Unread one byte to fill part of buffer
        pbis.unread(new byte[] {10});

        // Attempt to unread two bytes which exceed buffer capacity (pos would be 1, b.length=2 > pos=1)
        try {
            pbis.unread(new byte[] {20, 30});
            throw new AssertionError("Expected IOException due to pushback buffer overflow");
        } catch (IOException e) {
            // Expected
        }
    }
}
