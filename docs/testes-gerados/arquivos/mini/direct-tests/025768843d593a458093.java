/*
 * @test
 * @summary Test PushbackInputStream.unread(int) behavior including buffer full and normal unread
 * @run main PushbackInputStreamWitupTest
 */

import java.io.PushbackInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class PushbackInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testUnreadNormal();
        testUnreadBufferFull();
        testUnreadAfterClose();
    }

    private static void testUnreadNormal() throws IOException {
        byte[] data = {1, 2, 3};
        PushbackInputStream pbis = new PushbackInputStream(new ByteArrayInputStream(data), 2);
        // unread one byte should succeed
        pbis.unread(100);
        // unread another byte should succeed
        pbis.unread(101);
        // read should return 101 first (last unread)
        int first = pbis.read();
        if (first != 101) {
            throw new RuntimeException("Expected 101 but got " + first);
        }
        // read should return 100 next
        int second = pbis.read();
        if (second != 100) {
            throw new RuntimeException("Expected 100 but got " + second);
        }
        pbis.close();
    }

    private static void testUnreadBufferFull() throws IOException {
        byte[] data = {1};
        PushbackInputStream pbis = new PushbackInputStream(new ByteArrayInputStream(data), 1);
        // unread one byte fills the buffer
        pbis.unread(50);
        try {
            pbis.unread(51);
            throw new RuntimeException("Expected IOException for full pushback buffer");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("Push back buffer is full")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
        pbis.close();
    }

    private static void testUnreadAfterClose() throws IOException {
        byte[] data = {1};
        PushbackInputStream pbis = new PushbackInputStream(new ByteArrayInputStream(data), 1);
        pbis.close();
        try {
            pbis.unread(10);
            throw new RuntimeException("Expected IOException for unread after close");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("Stream closed")) {
                throw new RuntimeException("Unexpected IOException message after close: " + msg);
            }
        }
    }
}
