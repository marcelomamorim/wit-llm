/*
 * @test
 * @summary Test BufferedOutputStream.write(int b) for normal and boundary conditions.
 * @run main BufferedOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class BufferedOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testWriteNormal();
        testWriteBoundary();
        testWriteBufferFull();
    }

    static void testWriteNormal() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(baos);
        bos.write(65); // ASCII 'A'
        bos.flush();
        byte[] result = baos.toByteArray();
        if (result.length != 1 || result[0] != 65) {
            throw new AssertionError("testWriteNormal failed: expected byte 65, got " + result.length + " bytes");
        }
    }

    static void testWriteBoundary() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int bufferSize = 10;
        BufferedOutputStream bos = new BufferedOutputStream(baos, bufferSize);
        for (int i = 0; i < bufferSize; i++) {
            bos.write(i);
        }
        bos.flush();
        byte[] result = baos.toByteArray();
        if (result.length != bufferSize) {
            throw new AssertionError("testWriteBoundary failed: expected length " + bufferSize + ", got " + result.length);
        }
        for (int i = 0; i < bufferSize; i++) {
            if (result[i] != i) {
                throw new AssertionError("testWriteBoundary failed at index " + i + ": expected " + i + ", got " + result[i]);
            }
        }
    }

    static void testWriteBufferFull() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int bufferSize = 5;
        BufferedOutputStream bos = new BufferedOutputStream(baos, bufferSize);
        for (int i = 0; i < bufferSize; i++) {
            bos.write(i);
        }
        // Next write should trigger flushBuffer() internally
        bos.write(99);
        bos.flush();
        byte[] result = baos.toByteArray();
        if (result.length != bufferSize + 1) {
            throw new AssertionError("testWriteBufferFull failed: expected length " + (bufferSize + 1) + ", got " + result.length);
        }
        for (int i = 0; i < bufferSize; i++) {
            if (result[i] != i) {
                throw new AssertionError("testWriteBufferFull failed at index " + i + ": expected " + i + ", got " + result[i]);
            }
        }
        if (result[bufferSize] != 99) {
            throw new AssertionError("testWriteBufferFull failed at last byte: expected 99, got " + result[bufferSize]);
        }
    }
}
