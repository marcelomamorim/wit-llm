/*
 * @test
 * @summary Verifies that BasicImageReader.getResourceStream rejects a null ImageLocation with NullPointerException
 * @modules java.base/jdk.internal.jimage
 * @run main BasicImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.file.Paths;
import jdk.internal.jimage.BasicImageReader;

public class BasicImageReaderWitupTest {

    static class TestReader extends BasicImageReader {
        TestReader(java.nio.file.Path path) throws IOException {
            super(path);
        }
    }

    public static void main(String[] args) throws Exception {
        String javaHome = System.getProperty("java.home");
        java.nio.file.Path modules = Paths.get(javaHome, "lib", "modules");

        try (TestReader reader = new TestReader(modules)) {
            boolean npe = false;
            try {
                reader.getResourceStream(null);
            } catch (NullPointerException e) {
                npe = true;
            }
            if (!npe) {
                throw new RuntimeException("Expected NullPointerException for null ImageLocation");
            }
        }
    }
}
