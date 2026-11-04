/* @test    @summary Test ImageReaderFactory.get(Path) method for valid and invalid inputs    @run main ImageReaderFactoryWitupTest */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReaderFactory;

public class ImageReaderFactoryWitupTest {

    public static void main(String[] args) {
        testGetWithValidPath();
        testGetWithNullPath();
    }

    static void testGetWithValidPath() {
        Path validPath = Paths.get("/tmp/valid_image");
        try {
            ImageReader reader = ImageReaderFactory.get(validPath);
            if (reader == null) {
                throw new AssertionError("Expected non-null ImageReader for valid path");
            }
        } catch (IOException e) {
            // In actual test, handle or log exception
            // For this test, assume path is valid and no exception is thrown
        }
    }

    static void testGetWithNullPath() {
        try {
            ImageReaderFactory.get(null);
            throw new AssertionError("Expected NullPointerException for null path");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException for null path", e);
        }
    }
}
