/*
 * @test
 * @summary Test for jdk.internal.jimage.ImageReader.getRootDirectory() method.
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.Directory;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testGetRootDirectoryWhenClosed();
        testGetRootDirectoryWhenOpen();
    }

    private static void testGetRootDirectoryWhenClosed() throws Exception {
        Path dummyPath = Paths.get("/dummy/path");
        // Assume open returns a valid instance; here we simulate by calling open with a dummy path
        // Note: In real tests, use a real or mocked Path; here, for simplicity, we assume the method exists.
        try {
            jdk.internal.jimage.ImageReader reader = jdk.internal.jimage.ImageReader.open(dummyPath);
            reader.close();
            try {
                reader.getRootDirectory();
                // If no exception, test fails
                throw new AssertionError("Expected IOException when calling getRootDirectory() after close");
            } catch (IOException e) {
                // Expected exception
            }
        } catch (IOException e) {
            // If open fails, test cannot proceed; rethrow
            throw e;
        }
    }

    private static void testGetRootDirectoryWhenOpen() throws Exception {
        Path dummyPath = Paths.get("/dummy/path");
        jdk.internal.jimage.ImageReader reader = jdk.internal.jimage.ImageReader.open(dummyPath);
        try {
            Directory dir = reader.getRootDirectory();
            // Optionally, verify dir is not null
            if (dir == null) {
                throw new AssertionError("getRootDirectory() returned null");
            }
        } finally {
            reader.close();
        }
    }
}
