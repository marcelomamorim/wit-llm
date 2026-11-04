/*
 * @test
 * @summary Test BasicImageReader.getResourceStream with valid and null ImageLocation
 * @run main BasicImageReaderWitupTest
 */

import java.io.InputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public class BasicImageReaderWitupTest {

    // Minimal stub for ImageLocation to allow compilation and testing
    static class ImageLocation {
        private final int offset;
        private final int length;

        ImageLocation(int offset, int length) {
            this.offset = offset;
            this.length = length;
        }

        public int getOffset() {
            return offset;
        }

        public int getLength() {
            return length;
        }
    }

    // Minimal subclass to expose getResource for testing
    static class TestBasicImageReader extends jdk.internal.jimage.BasicImageReader {

        private final byte[] resourceData;

        protected TestBasicImageReader(Path path, ByteOrder byteOrder, byte[] resourceData) throws IOException {
            super(path, byteOrder);
            this.resourceData = resourceData;
        }

        // Override getResource to return resourceData or throw if loc is null
        protected byte[] getResource(ImageLocation loc) {
            Objects.requireNonNull(loc);
            int length = loc.getLength();
            if (length > resourceData.length) {
                length = resourceData.length;
            }
            byte[] result = new byte[length];
            System.arraycopy(resourceData, 0, result, 0, length);
            return result;
        }
    }

    public static void main(String[] args) throws Exception {
        // Create a temporary empty file to satisfy constructor
        Path tempFile = Files.createTempFile("BasicImageReaderTest", ".img");
        tempFile.toFile().deleteOnExit();

        byte[] testData = new byte[] {1, 2, 3, 4, 5};
        TestBasicImageReader reader = new TestBasicImageReader(tempFile, ByteOrder.BIG_ENDIAN, testData);

        // Test 1: getResourceStream with valid ImageLocation
        ImageLocation loc = new ImageLocation(0, 3);
        InputStream is = reader.getResourceStream(loc);
        byte[] buffer = new byte[3];
        int read = is.read(buffer);
        if (read != 3) {
            throw new RuntimeException("Expected to read 3 bytes, got " + read);
        }
        if (buffer[0] != 1 || buffer[1] != 2 || buffer[2] != 3) {
            throw new RuntimeException("Data read does not match expected bytes");
        }

        // Test 2: getResourceStream with null ImageLocation should throw NullPointerException
        boolean npeThrown = false;
        try {
            reader.getResourceStream(null);
        } catch (NullPointerException npe) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException when passing null to getResourceStream");
        }

        System.out.println("BasicImageReaderWitupTest passed");
    }
}
