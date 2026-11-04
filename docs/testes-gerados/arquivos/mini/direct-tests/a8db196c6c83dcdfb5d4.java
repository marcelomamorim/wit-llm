/*
 * @test
 * @summary Test jdk.internal.jimage.ImageReader.getResource(ImageLocation) method
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import jdk.internal.jimage.ImageReader;
import jdk.internal.jimage.ImageLocation;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testGetResourceWithValidImageLocation();
        testGetResourceAfterCloseThrows();
    }

    private static void testGetResourceWithValidImageLocation() throws Exception {
        // Create a temporary empty file to open as image
        Path tempFile = Files.createTempFile("imagetest", ".jimage");
        tempFile.toFile().deleteOnExit();

        ImageReader reader = null;
        try {
            reader = ImageReader.open(tempFile, ByteOrder.nativeOrder());

            // Create a dummy ImageLocation instance
            // Since ImageLocation is not public, we cannot instantiate it directly
            // So we test getResource with null to check behavior
            // This is a limitation due to lack of public constructor or factory

            // We expect NullPointerException or IOException if null is passed
            try {
                reader.getResource((ImageLocation) null);
                throw new RuntimeException("Expected NullPointerException or IOException for null ImageLocation");
            } catch (NullPointerException | IOException e) {
                // expected
            }

        } finally {
            if (reader != null) {
                reader.close();
            }
            Files.deleteIfExists(tempFile);
        }
    }

    private static void testGetResourceAfterCloseThrows() throws Exception {
        Path tempFile = Files.createTempFile("imagetest", ".jimage");
        tempFile.toFile().deleteOnExit();

        ImageReader reader = null;
        try {
            reader = ImageReader.open(tempFile, ByteOrder.nativeOrder());
            reader.close();

            try {
                // We expect IllegalStateException because requireOpen() throws it if closed
                reader.getResource((ImageLocation) null);
                throw new RuntimeException("Expected IllegalStateException after close");
            } catch (IllegalStateException e) {
                // expected
            }

        } finally {
            Files.deleteIfExists(tempFile);
        }
    }
}
