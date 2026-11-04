/*
 * @test
 * @summary Test for PushbackInputStream.unread(int b) method, covering exception paths.
 * @run main PushbackInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class PushbackInputStreamWitupTest {

    public static void main(String[] args) {
        testUnreadWithNullStream();
        testUnreadWhenBufferFull();
    }

    private static void testUnreadWithNullStream() {
        PushbackInputStream pbi = null;
        try {
            pbi = new PushbackInputStream(new ByteArrayInputStream(new byte[0]));
            // Simulate null stream by setting in to null via reflection or by subclassing is complex; instead, test ensureOpen() indirectly
            // Since ensureOpen() checks in == null, we can simulate by subclassing or forcibly setting in to null.
            // For simplicity, we test the exception path by creating a subclass that overrides ensureOpen.
            class NullStreamPushbackInputStream extends PushbackInputStream {
                public NullStreamPushbackInputStream() {
                    super(new ByteArrayInputStream(new byte[0]));
                }
                @Override
                protected void ensureOpen() throws IOException {
                    throw new IOException("Stream closed");
                }
            }
            NullStreamPushbackInputStream nullStream = new NullStreamPushbackInputStream();
            try {
                nullStream.unread(1);
                System.out.println("FAIL: Expected IOException for null stream not thrown");
            } catch (IOException e) {
                if ("Stream closed".equals(e.getMessage())) {
                    System.out.println("PASS: IOException thrown as expected for null stream");
                } else {
                    System.out.println("FAIL: Unexpected message: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testUnreadWhenBufferFull() {
        try {
            byte[] data = {0x01, 0x02};
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            PushbackInputStream pbi = new PushbackInputStream(bais, 1);
            // Fill buffer to simulate full buffer
            pbi.buf[0] = 0x00;
            pbi.pos = 0; // buffer full
            try {
                pbi.unread(0x55);
                System.out.println("FAIL: Expected IOException for full buffer not thrown");
            } catch (IOException e) {
                if ("Push back buffer is full".equals(e.getMessage())) {
                    System.out.println("PASS: IOException thrown as expected when buffer is full");
                } else {
                    System.out.println("FAIL: Unexpected message: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
