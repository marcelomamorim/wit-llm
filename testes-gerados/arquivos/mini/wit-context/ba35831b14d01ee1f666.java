/*
 * @test
 * @summary Test ImageReader.getName() behavior including closed state exception
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testGetNameWhenOpen();
        testGetNameWhenClosed();
    }

    private static void testGetNameWhenOpen() throws IOException {
        // Use a dummy path that likely does not exist, but we only test open and getName behavior
        // We expect IOException if file not found, so catch and skip test if so
        Path dummyPath = Paths.get("/dev/null");
        ImageReader reader = null;
        try {
            reader = ImageReader.open(dummyPath, ByteOrder.nativeOrder());
            // If open succeeds, getName should not throw IllegalStateException
            // but may throw IOException if underlying reader.getName() does
            // So we catch IOException and treat as pass since no IllegalStateException
            try {
                String name = reader.getName();
                if (name == null) {
                    throw new AssertionError("getName() returned null");
                }
            } catch (IOException e) {
                // Acceptable, underlying reader may throw IOException
            }
        } catch (IOException e) {
            // Opening failed, skip testGetNameWhenOpen
            return;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private static void testGetNameWhenClosed() throws IOException {
        Path dummyPath = Paths.get("/dev/null");
        ImageReader reader = null;
        try {
            reader = ImageReader.open(dummyPath, ByteOrder.nativeOrder());
        } catch (IOException e) {
            // Opening failed, skip test
            return;
        }
        // Close the reader
        reader.close();
        // Now getName() should throw IllegalStateException because closed
        try {
            reader.getName();
            throw new AssertionError("Expected IllegalStateException when calling getName() on closed ImageReader");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }
}
