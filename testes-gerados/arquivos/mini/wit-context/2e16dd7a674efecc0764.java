/*
 * @test
 * @summary Test BasicImageReader.open(Path) for null argument and normal usage
 * @run main BasicImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.BasicImageReader;

public class BasicImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        testOpenWithNullPath();
        testOpenWithValidPath();
    }

    private static void testOpenWithNullPath() {
        try {
            BasicImageReader.open(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException", ioe);
        }
    }

    private static void testOpenWithValidPath() throws IOException {
        // Use a dummy path that likely does not exist but is valid
        Path dummyPath = Paths.get("/dev/null");
        BasicImageReader reader = null;
        try {
            reader = BasicImageReader.open(dummyPath);
            if (reader == null) {
                throw new RuntimeException("BasicImageReader.open returned null");
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
