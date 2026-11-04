/*
 * @test
 * @summary Test ImageReader.getRootDirectory() behavior
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.nio.ByteOrder;
import jdk.internal.jimage.ImageReader;
import jdk.internal.jimage.Directory;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a temporary empty file to open as image
        Path tempFile = Files.createTempFile("imagereader-test", ".jimage");
        tempFile.toFile().deleteOnExit();

        // Open ImageReader with native byte order
        ImageReader reader = null;
        try {
            reader = ImageReader.open(tempFile, ByteOrder.nativeOrder());

            // getRootDirectory should not throw IOException if open
            Directory root = reader.getRootDirectory();
            if (root == null) {
                throw new RuntimeException("getRootDirectory returned null");
            }

            // Close the reader
            reader.close();

            // After close, getRootDirectory should throw IOException
            boolean threw = false;
            try {
                reader.getRootDirectory();
            } catch (IOException e) {
                threw = true;
            }
            if (!threw) {
                throw new RuntimeException("Expected IOException after close");
            }

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
            Files.deleteIfExists(tempFile);
        }
    }
}
