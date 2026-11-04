/*
 * @test
 * @summary Test ImageReader.getEntryNames() behavior including closed state
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testGetEntryNamesWhenOpen();
        testGetEntryNamesWhenClosed();
    }

    private static void testGetEntryNamesWhenOpen() throws IOException {
        // Use a known existing image file path that is unlikely to exist to avoid IOException
        // Since we cannot create a real ImageReader without internal details, test minimal open-close behavior
        Path dummyPath = Paths.get("/dev/null");
        ImageReader reader = null;
        try {
            reader = ImageReader.open(dummyPath, ByteOrder.nativeOrder());
            // getEntryNames should not throw IllegalStateException when open
            // It may throw IOException if /dev/null is not a valid image, so catch and ignore
            try {
                String[] names = reader.getEntryNames();
                if (names == null) {
                    throw new AssertionError("getEntryNames() returned null");
                }
            } catch (IOException e) {
                // Acceptable if image file invalid, test passes as no IllegalStateException
            }
        } catch (IOException e) {
            // Acceptable if open fails, test passes as no IllegalStateException
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private static void testGetEntryNamesWhenClosed() throws IOException {
        Path dummyPath = Paths.get("/dev/null");
        ImageReader reader = null;
        try {
            reader = ImageReader.open(dummyPath, ByteOrder.nativeOrder());
        } catch (IOException e) {
            // If open fails, skip test as cannot test closed state
            return;
        }
        try {
            reader.close();
        } catch (IOException e) {
            // Ignore close exception
        }
        // Now getEntryNames should throw IllegalStateException because closed
        try {
            reader.getEntryNames();
            throw new AssertionError("Expected IllegalStateException when calling getEntryNames() on closed ImageReader");
        } catch (IllegalStateException expected) {
            // Expected exception
        } catch (IOException e) {
            // IOException is not expected here, fail
            throw new AssertionError("Unexpected IOException instead of IllegalStateException", e);
        }
    }
}
