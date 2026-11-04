/*
 * @test
 * @summary Test BufferedOutputStream.write(int b) for correct buffer handling.
 * @run main BufferedOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class BufferedOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testWriteSingleByte();
        testWriteMultipleBytes();
        testBufferFlushOnFull();
    }

    static void testWriteSingleByte() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(baos, 4);
        bos.write(65); // 'A'
        bos.flush();
        byte[] result = baos.toByteArray();
        assert result.length == 1 : "Expected 1 byte, got " + result.length;
        assert result[0] == 65 : "Expected byte 65, got " + result[0];
        bos.close();
    }

    static void testWriteMultipleBytes() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(baos, 4);
        byte[] data = {66, 67, 68}; // 'B', 'C', 'D'
        for (byte b : data) {
            bos.write(b);
        }
        bos.flush();
        byte[] result = baos.toByteArray();
        assert result.length == 3 : "Expected 3 bytes, got " + result.length;
        assert result[0] == 66 && result[1] == 67 && result[2] == 68 : "Bytes do not match expected values";
        bos.close();
    }

    static void testBufferFlushOnFull() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(baos, 2);
        bos.write(70); // 'F'
        bos.write(71); // 'G'
        // Buffer should flush here
        bos.write(72); // 'H'
        bos.flush();
        byte[] result = baos.toByteArray();
        // Should contain 'F', 'G', 'H'
        assert result.length == 3 : "Expected 3 bytes, got " + result.length;
        assert result[0] == 70 && result[1] == 71 && result[2] == 72 : "Bytes do not match expected values";
        bos.close();
    }
}
