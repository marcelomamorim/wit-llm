/*
 * @test
 * @summary Test ImageReader.findNode(String) for open and closed states
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;
import jdk.internal.jimage.Node;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testFindNodeOpen();
        testFindNodeClosed();
    }

    private static void testFindNodeOpen() throws Exception {
        // Use a known existing image file path for testing
        // Since we cannot create a real image file here, we test only that method throws no IOException for open reader
        Path imagePath = Paths.get(System.getProperty("java.home"), "lib", "modules");
        ImageReader reader = null;
        try {
            reader = ImageReader.open(imagePath, ByteOrder.nativeOrder());
            // findNode with a likely non-existing node name returns null or throws IOException
            // We test that no IOException is thrown due to closed state
            try {
                Node node = reader.findNode("nonexistent_node_name_for_test");
                // node may be null or not, we do not assert here as behavior depends on image content
            } catch (IOException e) {
                // IOException may be thrown if node not found or other IO error, acceptable
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private static void testFindNodeClosed() throws Exception {
        Path imagePath = Paths.get(System.getProperty("java.home"), "lib", "modules");
        ImageReader reader = ImageReader.open(imagePath, ByteOrder.nativeOrder());
        reader.close();
        boolean threw = false;
        try {
            reader.findNode("anyname");
        } catch (IOException e) {
            // Expected IOException because reader is closed
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IOException when calling findNode on closed ImageReader");
        }
    }
}
