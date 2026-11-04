/*
 * @test
 * @summary Tests for java.io.PushbackInputStream
 * @run main PushbackInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class PushbackInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testUnreadWithNullInputStream();
        testUnreadWithBufferFull();
    }

    private static void testUnreadWithNullInputStream() {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        PushbackInputStream pbis = new PushbackInputStream(bais, 2);
        try {
            // Simulate null in stream by closing and then attempting to read
            pbis.close();
            pbis.unread(data, 0, 1);
            System.out.println("FAIL: Expected IOException for closed stream not thrown.");
        } catch (IOException e) {
            System.out.println("PASS: Caught expected IOException for null/closed stream.");
        }
    }

    private static void testUnreadWithBufferFull() {
        byte[] data = {10, 20, 30};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        PushbackInputStream pbis = new PushbackInputStream(bais, 2);
        try {
            // Fill buffer to capacity
            pbis.unread(new byte[]{1, 2}, 0, 2);
            // Now buffer is full, next unread should throw
            pbis.unread(new byte[]{3}, 0, 1);
            System.out.println("FAIL: Expected IOException for buffer full not thrown.");
        } catch (IOException e) {
            System.out.println("PASS: Caught expected IOException for buffer full.");
        }
    }
}
