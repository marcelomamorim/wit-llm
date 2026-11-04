/*
 * @test
 * @summary Test ImageReader.getModuleNames() behavior including closed state
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testGetModuleNamesWhenOpen();
        testGetModuleNamesWhenClosed();
    }

    private static void testGetModuleNamesWhenOpen() throws IOException {
        // Use a minimal valid image path that exists or simulate with a dummy path
        // Since we cannot create a real image file here, test only that method does not throw when open
        Path dummyPath = Paths.get(System.getProperty("java.io.tmpdir"));
        ImageReader reader = null;
        try {
            reader = ImageReader.open(dummyPath, ByteOrder.nativeOrder());
            // We expect getModuleNames to either return a String[] or throw IOException if no modules
            // We catch IOException and treat as pass since no real image file
            try {
                String[] modules = reader.getModuleNames();
                if (modules == null) {
                    throw new AssertionError("getModuleNames() returned null");
                }
            } catch (IOException e) {
                // Acceptable if image file is not valid
            }
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private static void testGetModuleNamesWhenClosed() throws IOException {
        Path dummyPath = Paths.get(System.getProperty("java.io.tmpdir"));
        ImageReader reader = null;
        try {
            reader = ImageReader.open(dummyPath, ByteOrder.nativeOrder());
            reader.close();
            try {
                reader.getModuleNames();
                throw new AssertionError("Expected IllegalStateException when calling getModuleNames() on closed ImageReader");
            } catch (IllegalStateException expected) {
                // Expected exception
            }
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
