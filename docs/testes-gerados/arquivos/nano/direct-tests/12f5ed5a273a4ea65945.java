/* @test    @summary Test Manifest.read(byte[], int, int) for correct reading behavior    @run main ManifestWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class ManifestWitupTest {

    public static void main(String[] args) throws IOException {
        testReadMethodReadsCorrectly();
        testReadMethodReturnsMinusOneAtEnd();
        testReadMethodHandlesEmptyBuffer();
    }

    static void testReadMethodReadsCorrectly() throws IOException {
        byte[] data = {65, 66, 67, 68, 69}; // ASCII for ABCDE
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        Manifest manifest = new Manifest();
        byte[] buffer = new byte[5];
        int readBytes = manifest.read(buffer, 0, buffer.length);
        assert readBytes == 5 : "Expected 5 bytes read";
        for (int i = 0; i < 5; i++) {
            assert buffer[i] == data[i] : "Byte mismatch at position " + i;
        }
    }

    static void testReadMethodReturnsMinusOneAtEnd() throws IOException {
        byte[] data = {65, 66}; // ASCII for AB
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        Manifest manifest = new Manifest();
        byte[] buffer = new byte[2];
        int firstRead = manifest.read(buffer, 0, buffer.length);
        assert firstRead == 2 : "Expected 2 bytes read";
        int secondRead = manifest.read(buffer, 0, buffer.length);
        assert secondRead == -1 : "Expected -1 at end of stream";
    }

    static void testReadMethodHandlesEmptyBuffer() throws IOException {
        byte[] data = {65, 66, 67}; // ASCII for ABC
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        Manifest manifest = new Manifest();
        byte[] buffer = new byte[0];
        int readBytes = manifest.read(buffer, 0, buffer.length);
        assert readBytes == 0 : "Expected 0 bytes read for empty buffer";
    }
}
