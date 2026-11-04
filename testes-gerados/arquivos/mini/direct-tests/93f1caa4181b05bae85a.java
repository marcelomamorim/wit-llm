/*
 * @test
 * @summary Test java.util.Base64 read() method behavior
 * @run main Base64WitupTest
 */

import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.io.InputStream;

public class Base64WitupTest {

    public static void main(String[] args) throws Exception {
        testReadReturnsByte();
        testReadReturnsMinusOneAtEnd();
    }

    static void testReadReturnsByte() throws IOException {
        // Prepare a Base64 decoder input stream with known data
        Decoder decoder = Base64.getDecoder();
        byte[] encoded = "SGVsbG8=".getBytes("US-ASCII"); // "Hello" in Base64
        InputStream in = decoder.wrap(new java.io.ByteArrayInputStream(encoded));

        int firstByte = in.read();
        if (firstByte == -1) {
            throw new RuntimeException("read() returned -1 unexpectedly on valid input");
        }
        // The first byte of "Hello" is 'H' = 72
        if (firstByte != 'H') {
            throw new RuntimeException("read() returned wrong byte: " + firstByte);
        }

        // Read remaining bytes to ensure stream works
        byte[] rest = new byte[4];
        int readCount = in.read(rest);
        if (readCount != 4) {
            throw new RuntimeException("Expected to read 4 bytes, got " + readCount);
        }
        String s = new String(new byte[] {(byte)firstByte, rest[0], rest[1], rest[2], rest[3]}, "US-ASCII");
        if (!"Hello".equals(s)) {
            throw new RuntimeException("Decoded string mismatch: " + s);
        }

        // After reading all bytes, next read() returns -1
        int eof = in.read();
        if (eof != -1) {
            throw new RuntimeException("Expected -1 at end of stream, got " + eof);
        }

        in.close();
    }

    static void testReadReturnsMinusOneAtEnd() throws IOException {
        // Empty input stream should return -1 immediately
        Decoder decoder = Base64.getDecoder();
        InputStream in = decoder.wrap(new java.io.ByteArrayInputStream(new byte[0]));

        int r = in.read();
        if (r != -1) {
            throw new RuntimeException("Expected -1 on empty input, got " + r);
        }

        in.close();
    }
}
