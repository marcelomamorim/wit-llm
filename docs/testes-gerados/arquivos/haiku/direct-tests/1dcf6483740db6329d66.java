/*
 * @test
 * @summary ImageReader.close() must close once, reject a second close with IOException, and block later use
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

        ImageReader reader = ImageReader.open(modules);
        reader.close();

        try {
            reader.close();
            throw new AssertionError("second close() should throw IOException");
        } catch (IOException e) {
            if (!"image file already closed".equals(e.getMessage())) {
                throw new AssertionError("unexpected message: " + e.getMessage());
            }
        }

        try {
            reader.getHeader();
            throw new AssertionError("getHeader() after close should throw IllegalStateException");
        } catch (IllegalStateException e) {
            if (!"image file closed".equals(e.getMessage())) {
                throw new AssertionError("unexpected message: " + e.getMessage());
            }
        }

        try {
            reader.findNode("/modules");
            throw new AssertionError("findNode() after close should throw IOException");
        } catch (IOException e) {
            if (!"image file closed".equals(e.getMessage())) {
                throw new AssertionError("unexpected message: " + e.getMessage());
            }
        }

        try {
            ImageReader.open(null);
            throw new AssertionError("open(null) should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
