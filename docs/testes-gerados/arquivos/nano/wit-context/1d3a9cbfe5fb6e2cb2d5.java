/*
 * @test
 * @summary Test for getImagePath() method in ImageReader class.
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws IOException {
        testGetImagePathWhenOpen();
        testGetImagePathWhenClosed();
    }

    private static void testGetImagePathWhenOpen() throws IOException {
        Path dummyPath = Paths.get("/dummy/path");
        // Mock or create a real SharedImageReader instance if possible
        // For this test, assume a real or mocked instance is available
        // Since the constructor is private, we simulate the scenario by
        // opening a real file or using a test double if available
        // Here, we assume a real file path; in real tests, use a test resource
        Path imagePath = dummyPath;
        try (ImageReader reader = ImageReader.open(imagePath)) {
            Path result = reader.getImagePath();
            if (result == null) {
                throw new AssertionError("Expected non-null Path when open");
            }
        }
    }

    private static void testGetImagePathWhenClosed() throws IOException {
        Path dummyPath = Paths.get("/dummy/path");
        try (ImageReader reader = ImageReader.open(dummyPath)) {
            // Close the reader to simulate closed state
            reader.close();
            try {
                reader.getImagePath();
                throw new AssertionError("Expected IOException when calling getImagePath() after close");
            } catch (IOException e) {
                // Expected exception
                if (!e.getMessage().equals("image file already closed")) {
                    throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
                }
            }
        }
    }
}
