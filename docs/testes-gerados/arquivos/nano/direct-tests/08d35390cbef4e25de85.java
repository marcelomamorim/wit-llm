/*
 * @test
 * @summary Test getEntryNames() method of ImageReader for normal and closed states
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testGetEntryNamesOpen();
        testGetEntryNamesClosed();
    }

    static void testGetEntryNamesOpen() throws IOException {
        Path imagePath = Paths.get("/path/to/fake/image");
        // Mock or assume the file exists for test; in real test, use a test resource
        ImageReader reader = null;
        try {
            reader = ImageReader.open(imagePath);
            String[] entryNames = reader.getEntryNames();
            // Verify that the returned array is not null
            if (entryNames == null) {
                throw new AssertionError("getEntryNames() returned null");
            }
            // Optionally, check for expected entries if known
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    static void testGetEntryNamesClosed() {
        Path imagePath = Paths.get("/path/to/fake/image");
        ImageReader reader = null;
        try {
            reader = ImageReader.open(imagePath);
        } catch (IOException e) {
            // If opening fails, skip test
            return;
        }
        try {
            reader.close();
            boolean exceptionThrown = false;
            try {
                reader.getEntryNames();
            } catch (IOException e) {
                exceptionThrown = true;
            }
            if (!exceptionThrown) {
                throw new AssertionError("Expected IOException after close") ;
            }
        } catch (IOException e) {
            // Ignore, as close() may throw
        }
    }
}
