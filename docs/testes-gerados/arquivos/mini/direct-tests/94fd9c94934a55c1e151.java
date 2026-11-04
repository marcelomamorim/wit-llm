/*
 * @test
 * @summary Test BufferedOutputStream.write(int b) method behavior
 * @run main BufferedOutputStreamWitupTest
 */

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BufferedOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testWriteSingleByte();
        testWriteFillBufferAndFlush();
    }

    private static void testWriteSingleByte() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(baos, 4);

        bos.write(0x41); // 'A'
        bos.flush();

        byte[] result = baos.toByteArray();
        if (result.length != 1 || result[0] != 0x41) {
            throw new AssertionError("testWriteSingleByte failed: expected [65], got " + java.util.Arrays.toString(result));
        }
    }

    private static void testWriteFillBufferAndFlush() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(baos, 4);

        // Write 4 bytes to fill the buffer
        bos.write(0x10);
        bos.write(0x20);
        bos.write(0x30);
        bos.write(0x40);

        // Next write should trigger flushBuffer before storing
        bos.write(0x50);

        bos.flush();

        byte[] result = baos.toByteArray();

        // After first 4 writes, buffer flushes, so baos should have first 4 bytes
        // Then 5th byte is buffered and flushed on flush()
        if (result.length != 5) {
            throw new AssertionError("testWriteFillBufferAndFlush failed: expected length 5, got " + result.length);
        }

        if (result[0] != 0x10 || result[1] != 0x20 || result[2] != 0x30 || result[3] != 0x40 || result[4] != 0x50) {
            throw new AssertionError("testWriteFillBufferAndFlush failed: unexpected content " + java.util.Arrays.toString(result));
        }
    }
}
