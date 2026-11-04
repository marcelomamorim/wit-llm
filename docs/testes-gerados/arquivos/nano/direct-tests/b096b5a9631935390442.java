/* @test    @summary Test the read() method of PushbackInputStream for correct byte reading and buffer behavior    @run main PushbackInputStreamWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class PushbackInputStreamWitupTest {

    public static void main(String[] args) {
        testReadWithBuffer();
        testReadAfterBufferExhausted();
        testReadWithEmptyBuffer();
    }

    static void testReadWithBuffer() {
        byte[] data = {10, 20, 30};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        PushbackInputStream pbis = new PushbackInputStream(bais, 2);
        try {
            int byte1 = pbis.read();
            int byte2 = pbis.read();
            int byte3 = pbis.read();
            assert byte1 == 10 : "Expected 10, got " + byte1;
            assert byte2 == 20 : "Expected 20, got " + byte2;
            assert byte3 == 30 : "Expected 30, got " + byte3;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void testReadAfterBufferExhausted() {
        byte[] data = {1, 2};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        PushbackInputStream pbis = new PushbackInputStream(bais, 1);
        try {
            int first = pbis.read();
            int second = pbis.read();
            int third = pbis.read();
            assert first == 1 : "Expected 1, got " + first;
            assert second == 2 : "Expected 2, got " + second;
            assert third == -1 : "Expected -1 (EOF), got " + third;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void testReadWithEmptyBuffer() {
        byte[] data = {5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        PushbackInputStream pbis = new PushbackInputStream(bais, 0);
        try {
            int result = pbis.read();
            assert result == 5 : "Expected 5, got " + result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
