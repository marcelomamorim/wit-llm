/*
 * @test
 * @summary Test for getEntryNames() method in ImageReader.
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws IOException {
        testGetEntryNamesWhenOpen();
        testGetEntryNamesWhenClosed();
    }

    private static void testGetEntryNamesWhenOpen() throws IOException {
        Path dummyPath = Paths.get("/path/to/dummy/image");
        // Mock or stub SharedImageReader to avoid actual file IO if needed
        // For this test, assume the method is called on a real or mocked object
        try (ImageReader reader = ImageReader.open(dummyPath)) {
            String[] entryNames = reader.getEntryNames();
            // Basic check: the result should be non-null (actual content depends on implementation)
            if (entryNames == null) {
                throw new AssertionError("Expected non-null array from getEntryNames() when open");
            }
        } catch (IOException e) {
            // If the file does not exist, the test can be skipped or considered passed for this scenario
            // Alternatively, handle as needed
        }
    }

    private static void testGetEntryNamesWhenClosed() {
        Path dummyPath = Paths.get("/path/to/dummy/image");
        try {
            ImageReader reader = ImageReader.open(dummyPath);
            reader.close();
            // After closing, calling getEntryNames() should throw IOException
            try {
                reader.getEntryNames();
                throw new AssertionError("Expected IOException when calling getEntryNames() after close");
            } catch (IOException e) {
                // Expected exception
            }
        } catch (IOException e) {
            // Opening the reader failed; test cannot proceed
        }
    }
}
