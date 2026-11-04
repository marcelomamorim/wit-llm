/* @test    @summary Test the unread(byte[] b) method of PushbackInputStream for correct behavior.    @run main PushbackInputStreamWitupTest */

import java.io.ByteArrayInputStream;
import java.io.PushbackInputStream;
import java.io.IOException;

public class PushbackInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testUnreadByteArray();
        testUnreadEmptyArray();
        testUnreadPartialArray();
    }

    private static void testUnreadByteArray() throws IOException {
        byte[] data = {10, 20, 30};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        PushbackInputStream pbis = new PushbackInputStream(bais, 3);
        byte[] buffer = new byte[3];
        int readBytes = pbis.read(buffer);
        if (readBytes != 3) {
            throw new AssertionError("Expected to read 3 bytes, but read " + readBytes);
        }
        pbis.unread(buffer);
        byte[] buffer2 = new byte[3];
        int readBytes2 = pbis.read(buffer2);
        for (int i = 0; i < 3; i++) {
            if (buffer2[i] != buffer[i]) {
                throw new AssertionError("Mismatch at byte " + i);
            }
        }
    }

    private static void testUnreadEmptyArray() throws IOException {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        PushbackInputStream pbis = new PushbackInputStream(bais, 3);
        byte[] emptyArray = {};
        pbis.unread(emptyArray);
        byte[] buffer = new byte[3];
        int readBytes = pbis.read(buffer);
        if (readBytes != 3) {
            throw new AssertionError("Expected to read 3 bytes, but read " + readBytes);
        }
        if (buffer[0] != 1 || buffer[1] != 2 || buffer[2] != 3) {
            throw new AssertionError("Data mismatch after unread of empty array");
        }
    }

    private static void testUnreadPartialArray() throws IOException {
        byte[] data = {5, 6, 7, 8};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        PushbackInputStream pbis = new PushbackInputStream(bais, 4);
        byte[] partial = {6, 7};
        pbis.read(); // read first byte
        pbis.unread(partial);
        byte[] buffer = new byte[4];
        int readBytes = pbis.read(buffer);
        if (readBytes != 4) {
            throw new AssertionError("Expected to read 4 bytes, but read " + readBytes);
        }
        if (buffer[0] != 5 || buffer[1] != 6 || buffer[2] != 7 || buffer[3] != 8) {
            throw new AssertionError("Data mismatch after partial unread");
        }
    }
}
