/*
 * @test
 * @summary Test ImageReader.open(Path, ByteOrder) method
 * @run main ImageReaderWitupTest
 */

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.nio.ByteOrder;
import java.io.IOException;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        testOpenWithNullPath();
        testOpenWithNullByteOrder();
        testOpenWithValidFile();
    }

    private static void testOpenWithNullPath() {
        try {
            ImageReader.open(null, ByteOrder.nativeOrder());
            throw new RuntimeException("Expected NullPointerException for null imagePath");
        } catch (NullPointerException npe) {
            // expected
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException", ioe);
        }
    }

    private static void testOpenWithNullByteOrder() throws IOException {
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("ImageReaderWitupTest", ".img");
            try {
                ImageReader.open(tempFile, null);
                throw new RuntimeException("Expected NullPointerException for null byteOrder");
            } catch (NullPointerException npe) {
                // expected
            }
        } finally {
            if (tempFile != null) {
                Files.deleteIfExists(tempFile);
            }
        }
    }

    private static void testOpenWithValidFile() throws IOException {
        Path tempFile = null;
        ImageReader reader = null;
        try {
            tempFile = Files.createTempFile("ImageReaderWitupTest", ".img");
            // The file is empty, but we test that open does not throw NPE or NullPointerException
            // It may throw IOException if SharedImageReader.open fails on empty file
            reader = ImageReader.open(tempFile, ByteOrder.nativeOrder());
            if (reader == null) {
                throw new RuntimeException("ImageReader.open returned null");
            }
            // Close reader to avoid resource leak
            reader.close();
        } catch (IOException ioe) {
            // IOException is allowed if SharedImageReader.open fails on empty file
            // Just print stack trace for information
            ioe.printStackTrace();
        } finally {
            if (tempFile != null) {
                Files.deleteIfExists(tempFile);
            }
        }
    }
}
