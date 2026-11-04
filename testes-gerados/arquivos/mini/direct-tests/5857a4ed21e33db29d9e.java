/*
 * @test
 * @summary Test BasicImageReader.getResource(String) behavior
 * @run main BasicImageReaderWitupTest
 */

import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import jdk.internal.jimage.BasicImageReader;

public class BasicImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testGetResourceNullName();
        testGetResourceNotFound();
        testGetResourceFound();
    }

    private static void testGetResourceNullName() {
        BasicImageReader reader = new DummyBasicImageReader();
        try {
            reader.getResource(null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException npe) {
            // expected
        } finally {
            closeQuietly(reader);
        }
    }

    private static void testGetResourceNotFound() {
        BasicImageReader reader = new DummyBasicImageReader();
        try {
            byte[] result = reader.getResource("nonexistent");
            if (result != null) {
                throw new RuntimeException("Expected null for resource not found");
            }
        } finally {
            closeQuietly(reader);
        }
    }

    private static void testGetResourceFound() {
        BasicImageReader reader = new DummyBasicImageReader();
        try {
            byte[] result = reader.getResource("exists");
            if (result == null) {
                throw new RuntimeException("Expected non-null byte[] for existing resource");
            }
            if (result.length != 3 || result[0] != 1 || result[1] != 2 || result[2] != 3) {
                throw new RuntimeException("Unexpected content in resource byte[]");
            }
        } finally {
            closeQuietly(reader);
        }
    }

    private static void closeQuietly(BasicImageReader reader) {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (Exception e) {
            // ignore
        }
    }

    // Dummy subclass to override findLocation and getResource(ImageLocation)
    private static class DummyBasicImageReader extends BasicImageReader {

        DummyBasicImageReader() {
            super(Paths.get("/dummy/path"), ByteOrder.BIG_ENDIAN);
        }

        @Override
        protected ImageLocation findLocation(String name) {
            Objects.requireNonNull(name);
            if ("exists".equals(name)) {
                return new ImageLocation();
            } else {
                return null;
            }
        }

        @Override
        protected byte[] getResource(ImageLocation location) {
            if (location == null) {
                return null;
            }
            return new byte[] {1, 2, 3};
        }
    }

    // Minimal stub for ImageLocation to allow compilation
    private static class ImageLocation {
    }
}
