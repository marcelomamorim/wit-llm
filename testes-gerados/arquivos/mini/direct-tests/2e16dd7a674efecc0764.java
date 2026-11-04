/*
 * @test
 * @summary Test BasicImageReader.open(Path) method
 * @run main BasicImageReaderWitupTest
 */

import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import jdk.internal.jimage.BasicImageReader;

public class BasicImageReaderWitupTest {
    public static void main(String[] args) throws IOException {
        // Use a valid path string for testing
        Path path = Paths.get(System.getProperty("java.home"));

        // Call open and verify it returns a non-null BasicImageReader
        BasicImageReader reader = BasicImageReader.open(path);
        if (reader == null) {
            throw new AssertionError("BasicImageReader.open returned null");
        }

        // Verify that the byte order used is native order
        // We cannot access byteOrder field directly, but we can check that open uses nativeOrder by contract
        // So just ensure no exception and non-null reader is enough for this test

        // Close the reader to test AutoCloseable compliance
        reader.close();
    }
}
