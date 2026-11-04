/* @test    @summary Test getChildren method of ImageReader for exception case    @run main ImageReaderWitupTest */

import java.io.IOException;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws IOException {
        testGetChildrenThrowsException();
    }

    public static void testGetChildrenThrowsException() {
        // Create a mock or subclass to override getNameString() if needed
        // Since getChildren() throws IllegalArgumentException unconditionally,
        // calling it directly should throw.
        try {
            // Instantiate ImageReader with a dummy path or mock if possible
            // For the purpose of this test, we assume a dummy path.
            ImageReader reader = ImageReader.open(java.nio.file.Paths.get("dummyPath"));
            // Call getChildren() which should throw
            reader.getChildren();
            // If no exception, test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
            if (!e.getMessage().startsWith("not a directory")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        } catch (IOException e) {
            // If IOException occurs, the test setup is invalid
            throw new AssertionError("IOException during test setup: " + e.getMessage(), e);
        }
    }
}
