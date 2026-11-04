/*
 * @test
 * @summary Test PushbackInputStream.unread(byte[], int, int) for exception and normal behavior
 * @run main PushbackInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class PushbackInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testUnreadThrowsWhenStreamClosed();
        testUnreadThrowsWhenLenGreaterThanPos();
        testUnreadCopiesBytesCorrectly();
    }

    private static void testUnreadThrowsWhenStreamClosed() {
        try {
            PushbackInputStream pbis = new PushbackInputStream(new ByteArrayInputStream(new byte[10]), 5);
            pbis.close();
            byte[] data = {1, 2, 3};
            pbis.unread(data, 0, 3);
            throw new RuntimeException("Expected IOException not thrown when stream closed");
        } catch (IOException e) {
            // Expected IOException due to closed stream
        }
    }

    private static void testUnreadThrowsWhenLenGreaterThanPos() throws IOException {
        PushbackInputStream pbis = new PushbackInputStream(new ByteArrayInputStream(new byte[10]), 3);
        byte[] data = {1, 2, 3, 4};
        try {
            pbis.unread(data, 0, 4); // len=4 > pos=3 initially
            throw new RuntimeException("Expected IOException not thrown when len > pos");
        } catch (IOException e) {
            // Expected IOException: Push back buffer is full
        }
    }

    private static void testUnreadCopiesBytesCorrectly() throws IOException {
        byte[] input = {10, 20, 30, 40, 50};
        ByteArrayInputStream bais = new ByteArrayInputStream(input);
        PushbackInputStream pbis = new PushbackInputStream(bais, 5);

        int firstByte = pbis.read(); // should read 10
        if (firstByte != 10) {
            throw new RuntimeException("Expected first byte 10, got " + firstByte);
        }

        byte[] toUnread = {99, 98};
        pbis.unread(toUnread, 0, 2);

        int read1 = pbis.read(); // should be 99 (unread first byte)
        int read2 = pbis.read(); // should be 98 (unread second byte)
        int read3 = pbis.read(); // should be 20 (next from original stream)

        if (read1 != 99 || read2 != 98 || read3 != 20) {
            throw new RuntimeException("Unread bytes not read correctly: got " + read1 + ", " + read2 + ", " + read3);
        }
    }
}
