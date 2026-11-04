/* @test    @summary Test PosterOutputStream.write(int b) for normal and exceptional behavior    @run main PosterOutputStreamWitupTest */

import java.io.*;
import sun.net.www.http.PosterOutputStream;

public class PosterOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testWriteNormal();
        testWriteAfterClose();
        testWriteMemoryError();
    }

    private static void testWriteNormal() {
        PosterOutputStream pos = new PosterOutputStream();
        try {
            pos.write(65); // ASCII 'A'
            if (pos.size() != 1 || pos.toByteArray()[0] != 65) {
                throw new AssertionError("Failed: write normal case");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testWriteNormal", e);
        }
    }

    private static void testWriteAfterClose() throws IOException {
        PosterOutputStream pos = new PosterOutputStream();
        pos.close();
        int initialSize = pos.size();
        pos.write(66); // ASCII 'B'
        if (pos.size() != initialSize) {
            throw new AssertionError("Failed: write after close should not increase size");
        }
    }

    private static void testWriteMemoryError() {
        PosterOutputStream pos = new PosterOutputStream();
        // Simulate OutOfMemoryError by overriding write method
        PosterOutputStream faulty = new PosterOutputStream() {
            @Override
            public synchronized void write(int b) {
                throw new OutOfMemoryError("Simulated OOM");
            }
        };
        try {
            faulty.write(67); // ASCII 'C'
            throw new AssertionError("Expected OutOfMemoryError not thrown");
        } catch (OutOfMemoryError e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testWriteMemoryError", e);
        }
    }
}
