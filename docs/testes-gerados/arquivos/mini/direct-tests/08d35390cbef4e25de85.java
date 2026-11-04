/*
 * @test
 * @summary Test ImageReader.getEntryNames() behavior
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
        testGetEntryNamesOnOpenClose();
    }

    private static void testGetEntryNamesOnOpenClose() throws Exception {
        // Create a temporary empty file to open as image
        Path tempFile = Files.createTempFile("imagereader-test", ".jimage");
        tempFile.toFile().deleteOnExit();

        ImageReader reader = null;
        try {
            reader = ImageReader.open(tempFile, ByteOrder.nativeOrder());
            // getEntryNames should return a non-null array (possibly empty)
            String[] entries = reader.getEntryNames();
            if (entries == null) {
                throw new AssertionError("getEntryNames() returned null");
            }
            // entries array length >= 0
            if (entries.length < 0) {
                throw new AssertionError("getEntryNames() returned array with negative length");
            }

            // Close reader
            reader.close();

            // After close, getEntryNames() should throw IllegalStateException
            boolean threw = false;
            try {
                reader.getEntryNames();
            } catch (IllegalStateException ise) {
                threw = true;
            }
            if (!threw) {
                throw new AssertionError("getEntryNames() did not throw IllegalStateException after close");
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
