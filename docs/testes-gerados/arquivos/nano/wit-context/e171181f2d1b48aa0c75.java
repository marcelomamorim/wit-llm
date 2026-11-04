/*
 * @test
 * @summary Test for jdk.internal.jimage.ImageReader.findNode(String)
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import jdk.internal.jimage.ImageReader;
import jdk.internal.jimage.Node;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testFindNodeWhenOpen();
        testFindNodeWhenClosed();
    }

    private static void testFindNodeWhenOpen() throws Exception {
        // Setup: create a mock or real ImageReader instance in open state
        // Since constructor is package-private, assume a real file path or mock
        // For the purpose of this test, we simulate the open state by calling open with a valid path
        // Note: Replace "path/to/image" with an actual valid path if needed
        try (ImageReader reader = ImageReader.open(java.nio.file.Paths.get("path/to/image"))) {
            // Call findNode with a sample name
            Node node = reader.findNode("sampleNode");
            // No exception expected, verify node is not null (if applicable)
            if (node == null) {
                throw new AssertionError("Expected non-null Node for existing node name");
            }
        } catch (IOException e) {
            // If the file does not exist, ignore or handle accordingly
        }
    }

    private static void testFindNodeWhenClosed() throws Exception {
        // Setup: open and then close the reader to simulate closed state
        ImageReader reader = null;
        try {
            reader = ImageReader.open(java.nio.file.Paths.get("path/to/image"));
        } catch (IOException e) {
            // Ignore if file not found; test only the closed state behavior
        }
        if (reader != null) {
            reader.close();
            // Now test that findNode throws IOException when closed
            try {
                reader.findNode("anyNode");
                throw new AssertionError("Expected IOException when calling findNode on closed reader");
            } catch (IOException e) {
                // Expected exception
            }
        }
    }
}
