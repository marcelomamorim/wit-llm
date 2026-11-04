/*
 * @test
 * @summary ImageReader.getString must reject calls after close and return a value on an open image
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        Path modules = Paths.get(System.getProperty("java.home"), "lib", "modules");

        testGetStringOnOpenReader(modules);
        testGetStringOnClosedReaderThrows(modules);
        testDoubleCloseThrowsIOException(modules);
    }

    static void testGetStringOnOpenReader(Path modules) throws IOException {
        ImageReader reader = ImageReader.open(modules);
        try {
            String s = reader.getString(0);
            if (s == null) {
                throw new AssertionError("getString(0) returned null on open reader");
            }
        } finally {
            reader.close();
        }
    }

    static void testGetStringOnClosedReaderThrows(Path modules) throws IOException {
        ImageReader reader = ImageReader.open(modules);
        reader.close();
        try {
            reader.getString(0);
            throw new AssertionError("expected IllegalStateException after close");
        } catch (IllegalStateException e) {
            if (!"image file closed".equals(e.getMessage())) {
                throw new AssertionError("unexpected message: " + e.getMessage());
            }
        }
    }

    static void testDoubleCloseThrowsIOException(Path modules) throws IOException {
        ImageReader reader = ImageReader.open(modules);
        reader.close();
        try {
            reader.close();
            throw new AssertionError("expected IOException on second close");
        } catch (IOException e) {
            if (!"image file already closed".equals(e.getMessage())) {
                throw new AssertionError("unexpected message: " + e.getMessage());
            }
        }
    }
}
