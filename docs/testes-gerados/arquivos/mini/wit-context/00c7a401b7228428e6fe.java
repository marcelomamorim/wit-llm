/*
 * @test
 * @summary Test BasicImageReader.getResourceStream(ImageLocation) for null argument and normal usage
 * @run main BasicImageReaderWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import jdk.internal.jimage.BasicImageReader;

public class BasicImageReaderWitupTest {

    // Minimal stub class to allow compilation and testing
    static class ImageLocation {
        // no fields or methods needed for this test
    }

    // Subclass to expose protected constructor for testing
    static class TestBasicImageReader extends BasicImageReader {
        protected TestBasicImageReader(Path path, ByteOrder byteOrder) throws Exception {
            super(path, byteOrder);
        }

        // Expose getResource to return a fixed byte array for testing getResourceStream
        byte[] getResource(ImageLocation loc) {
            // Return fixed content for testing
            return new byte[] {1, 2, 3, 4};
        }

        // Override getResourceStream to call super method
        @Override
        public InputStream getResourceStream(ImageLocation loc) {
            return super.getResourceStream(loc);
        }
    }

    public static void main(String[] args) throws Exception {
        testNullLocationThrowsNPE();
        testGetResourceStreamReturnsByteArrayInputStream();
        System.out.println("BasicImageReaderWitupTest passed");
    }

    private static void testNullLocationThrowsNPE() throws Exception {
        Path dummyPath = Paths.get("/tmp/dummy");
        TestBasicImageReader reader = new TestBasicImageReader(dummyPath, ByteOrder.BIG_ENDIAN);
        try {
            reader.getResourceStream(null);
            throw new RuntimeException("Expected NullPointerException for null loc");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testGetResourceStreamReturnsByteArrayInputStream() throws Exception {
        Path dummyPath = Paths.get("/tmp/dummy");
        TestBasicImageReader reader = new TestBasicImageReader(dummyPath, ByteOrder.BIG_ENDIAN);
        ImageLocation loc = new ImageLocation();
        InputStream is = reader.getResourceStream(loc);
        if (!(is instanceof ByteArrayInputStream)) {
            throw new RuntimeException("Expected ByteArrayInputStream");
        }
        int firstByte = is.read();
        if (firstByte != 1) {
            throw new RuntimeException("Unexpected first byte in stream: " + firstByte);
        }
        is.close();
    }
}
