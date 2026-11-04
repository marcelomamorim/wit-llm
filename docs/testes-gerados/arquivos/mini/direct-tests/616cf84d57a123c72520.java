/*
 * @test
 * @summary Test ImageReader.getStrings() method behavior
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import jdk.internal.jimage.ImageReader;
import jdk.internal.jimage.ImageStringsReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testGetStringsOnOpenImage();
        testGetStringsOnClosedImage();
    }

    private static void testGetStringsOnOpenImage() throws Exception {
        // Create a temporary empty file to open as image
        Path tempFile = Files.createTempFile("imagereader-test", ".jimage");
        tempFile.toFile().deleteOnExit();

        ImageReader reader = null;
        try {
            reader = ImageReader.open(tempFile, ByteOrder.nativeOrder());
            ImageStringsReader stringsReader = reader.getStrings();
            if (stringsReader == null) {
                throw new AssertionError("getStrings() returned null on open image");
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private static void testGetStringsOnClosedImage() throws Exception {
        Path tempFile = Files.createTempFile("imagereader-test", ".jimage");
        tempFile.toFile().deleteOnExit();

        ImageReader reader = ImageReader.open(tempFile, ByteOrder.nativeOrder());
        reader.close();

        try {
            reader.getStrings();
            throw new AssertionError("Expected IllegalStateException when calling getStrings() on closed ImageReader");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
}
