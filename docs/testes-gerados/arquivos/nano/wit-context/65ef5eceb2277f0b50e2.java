/* @test    @summary Test getUTF8String method for sun.security.util.DerInputStream    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testGetUTF8String_Normal();
        testGetUTF8String_BufferReadReturnsMinusOne() throws IOException;
        testGetUTF8String_BufferReadThrowsIOException() throws IOException;
    }

    private static void testGetUTF8String_Normal() throws IOException {
        byte[] data = {0x0C, 0x03, 0x66, 0x6F, 0x6F}; // Tag=12 (UTF8String), Length=3, Content="foo"
        DerInputStream dis = new DerInputStream(data);
        String result = dis.getUTF8String();
        if (!"foo".equals(result)) {
            throw new AssertionError("Expected 'foo', got: " + result);
        }
    }

    private static void testGetUTF8String_BufferReadReturnsMinusOne() throws IOException {
        byte[] data = {0x0C, 0x01, 0x00}; // Tag=12, Length=1, Content=0x00
        DerInputStream dis = new DerInputStream(data) {
            @Override
            protected int read() throws IOException {
                return -1; // Simulate end of stream
            }
        };
        try {
            dis.getUTF8String();
            throw new AssertionError("Expected IOException due to read() returning -1");
        } catch (IOException e) {
            // Expected exception
        }
    }

    private static void testGetUTF8String_BufferReadThrowsIOException() throws IOException {
        byte[] data = {0x0C, 0x01, 0x00}; // Tag=12, Length=1, Content=0x00
        DerInputStream dis = new DerInputStream(data) {
            @Override
            protected int read() throws IOException {
                throw new IOException("Read error");
            }
        };
        try {
            dis.getUTF8String();
            throw new AssertionError("Expected IOException due to read() throwing IOException");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
