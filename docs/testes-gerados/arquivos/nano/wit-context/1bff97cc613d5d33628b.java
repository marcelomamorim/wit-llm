/*
 * @test
 * @summary Test for CipherInputStream.read() method, focusing on normal and exceptional behavior.
 * @run main CipherInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.crypto.CipherInputStream;

public class CipherInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testReadNormal();
        testReadEndOfStream();
        testReadWithEmptyBuffer();
    }

    static void testReadNormal() throws IOException {
        byte[] data = {10, 20, 30, 40};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        CipherInputStream cis = new CipherInputStream(bais);
        int result = cis.read();
        assert result == (data[0] & 0xff) : "Expected first byte to be " + (data[0] & 0xff) + " but got " + result;
        cis.close();
    }

    static void testReadEndOfStream() throws IOException {
        byte[] data = {1, 2};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        CipherInputStream cis = new CipherInputStream(bais);
        // Read all data
        cis.read();
        cis.read();
        // Next read should return -1
        int result = cis.read();
        assert result == -1 : "Expected end of stream (-1), but got " + result;
        cis.close();
    }

    static void testReadWithEmptyBuffer() throws IOException {
        byte[] data = {5, 6, 7};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        CipherInputStream cis = new CipherInputStream(bais);
        // Read first byte
        int firstByte = cis.read();
        assert firstByte == (data[0] & 0xff) : "Expected first byte " + (data[0] & 0xff) + " but got " + firstByte;
        // Read remaining bytes
        int secondByte = cis.read();
        int thirdByte = cis.read();
        int end = cis.read();
        assert secondByte == (data[1] & 0xff) : "Expected second byte " + (data[1] & 0xff) + " but got " + secondByte;
        assert thirdByte == (data[2] & 0xff) : "Expected third byte " + (data[2] & 0xff) + " but got " + thirdByte;
        assert end == -1 : "Expected end of stream (-1), but got " + end;
        cis.close();
    }
}
