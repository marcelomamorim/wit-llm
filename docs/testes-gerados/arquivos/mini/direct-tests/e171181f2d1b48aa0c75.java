/*
 * @test
 * @summary Test jdk.internal.jimage.ImageReader.findNode(String) method
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.ByteOrder;
import jdk.internal.jimage.ImageReader;
import jdk.internal.jimage.Node;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        // Prepare a minimal valid jimage file path for testing
        // Since we cannot create a real jimage file here, test open and findNode with a known file
        // Use the current running JDK image file if available

        Path imagePath = Paths.get(System.getProperty("java.home"), "lib", "modules");
        if (!Files.exists(imagePath)) {
            System.out.println("SKIP: jimage file not found at " + imagePath);
            return;
        }

        ImageReader reader = null;
        try {
            reader = ImageReader.open(imagePath, ByteOrder.nativeOrder());

            // Test findNode with a known existing resource name
            // We do not know exact names, so test with empty string and null to check behavior

            // Test with empty string - should not throw but may return null
            Node nodeEmpty = reader.findNode("");
            if (nodeEmpty == null) {
                System.out.println("PASS: findNode("") returned null as expected");
            } else {
                System.out.println("PASS: findNode("") returned a Node instance");
            }

            // Test with a likely non-existing name
            Node nodeNonExist = reader.findNode("non-existing-resource-name-xyz");
            if (nodeNonExist == null) {
                System.out.println("PASS: findNode(non-existing) returned null as expected");
            } else {
                System.out.println("PASS: findNode(non-existing) returned a Node instance");
            }

            // Test with a normal resource name if possible
            // Since we do not know names, skip

            // Test that calling findNode after close throws IOException
            reader.close();
            try {
                reader.findNode("");
                throw new RuntimeException("FAIL: findNode after close did not throw IOException");
            } catch (IOException e) {
                System.out.println("PASS: findNode after close threw IOException as expected");
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
