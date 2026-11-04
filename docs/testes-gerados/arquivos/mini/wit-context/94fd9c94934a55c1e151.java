/*
 * @test
 * @summary Test BufferedOutputStream.write(int) for buffer flush and normal write
 * @run main BufferedOutputStreamWitupTest
 */

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BufferedOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testWriteSingleByte();
        testWriteTriggersFlushBuffer();
    }

    private static void testWriteSingleByte() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(baos, 4);
        bos.write(0x41); // 'A'
        bos.flush();
        byte[] result = baos.toByteArray();
        if (result.length != 1 || result[0] != 0x41) {
            throw new AssertionError("Expected single byte 'A' after flush, got: " + new String(result));
        }
    }

    private static void testWriteTriggersFlushBuffer() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(baos, 2);
        // Fill buffer to capacity
        bos.write(0x41); // 'A'
        bos.write(0x42); // 'B'
        // Next write should trigger flushBuffer internally
        bos.write(0x43); // 'C'
        bos.flush();
        byte[] result = baos.toByteArray();
        // After flush, buffer should contain 'C' only, and output stream should have 'A','B'
        if (result.length != 2 || result[0] != 0x41 || result[1] != 0x42) {
            throw new AssertionError("Expected flushed bytes 'A','B', got: " + new String(result));
        }
    }
}
