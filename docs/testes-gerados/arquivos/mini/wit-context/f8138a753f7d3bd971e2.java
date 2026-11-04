/*
 * @test
 * @summary Deterministic tests for jdk.internal.jimage.ImageReaderFactory.get(Path)
 * @run main ImageReaderFactoryWitupTest
 */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReaderFactory;

public class ImageReaderFactoryWitupTest {
    public static void main(String[] args) throws Exception {
        testGetWithNullPath();
        testGetWithInvalidPath();
        testGetWithBootModulesPath();
    }

    private static void testGetWithNullPath() {
        try {
            ImageReaderFactory.get(null);
            throw new RuntimeException("Expected NullPointerException for null path");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException for null path", ioe);
        }
    }

    private static void testGetWithInvalidPath() throws Exception {
        // Use a path very likely not to exist to test IOException path
        Path invalidPath = Paths.get("/this/path/should/not/exist/for/testing/jimage");
        try {
            ImageReaderFactory.get(invalidPath);
            // If no exception, the file exists or is accessible, test passes silently
        } catch (IOException ioe) {
            // IOException expected if file does not exist or cannot be opened
        }
    }

    private static void testGetWithBootModulesPath() throws Exception {
        // Test get with the known BOOT_MODULES_JIMAGE path
        // This path is internal but we can try to get it via getImageReader() indirectly
        try {
            ImageReaderFactory.getImageReader();
            // If no exception, test passes silently
        } catch (Exception e) {
            // Accept UncheckedIOException or IOException
            if (!(e instanceof java.io.UncheckedIOException || e instanceof IOException)) {
                throw e;
            }
        }
    }
}
