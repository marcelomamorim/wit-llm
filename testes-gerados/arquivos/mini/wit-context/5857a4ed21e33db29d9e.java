/*
 * @test
 * @summary Test BasicImageReader.getResource(String) for null input and normal behavior
 * @run main BasicImageReaderWitupTest
 */

import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import jdk.internal.jimage.BasicImageReader;

public class BasicImageReaderWitupTest {
    public static void main(String[] args) throws IOException {
        testGetResourceNullName();
        testGetResourceNonexistentName();
    }

    private static void testGetResourceNullName() throws IOException {
        BasicImageReader reader = null;
        try {
            // Use a dummy path and byte order for construction
            Path dummyPath = Paths.get("/dev/null");
            reader = new BasicImageReader(dummyPath, ByteOrder.BIG_ENDIAN);
            try {
                reader.getResource(null);
                throw new RuntimeException("Expected NullPointerException for null name");
            } catch (NullPointerException expected) {
                // Expected exception
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private static void testGetResourceNonexistentName() throws IOException {
        BasicImageReader reader = null;
        try {
            Path dummyPath = Paths.get("/dev/null");
            reader = new BasicImageReader(dummyPath, ByteOrder.BIG_ENDIAN);
            // Use a name very unlikely to exist
            byte[] result = reader.getResource("nonexistent_resource_name_for_test");
            if (result != null) {
                throw new RuntimeException("Expected null for nonexistent resource name");
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
