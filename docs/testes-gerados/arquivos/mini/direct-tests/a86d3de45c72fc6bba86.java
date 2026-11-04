/*
 * @test
 * @summary Test ImageReader.getString(int) method behavior
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testGetStringOnOpenImage();
        testGetStringOnClosedImage();
    }

    private static void testGetStringOnOpenImage() throws Exception {
        // Create a temporary empty file to open as image
        Path tempFile = Files.createTempFile("imagereader-test", ".jimage");
        tempFile.toFile().deleteOnExit();

        ImageReader reader = null;
        try {
            reader = ImageReader.open(tempFile, ByteOrder.nativeOrder());
            // Call getString with offset 0, expecting no exception and a non-null result (may be empty string)
            String result = reader.getString(0);
            if (result == null) {
                throw new AssertionError("getString returned null");
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private static void testGetStringOnClosedImage() throws Exception {
        Path tempFile = Files.createTempFile("imagereader-test", ".jimage");
        tempFile.toFile().deleteOnExit();

        ImageReader reader = ImageReader.open(tempFile, ByteOrder.nativeOrder());
        reader.close();

        try {
            reader.getString(0);
            throw new AssertionError("Expected IllegalStateException when calling getString on closed ImageReader");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
}
