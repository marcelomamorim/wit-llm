/*
 * @test
 * @summary Verify ImageReader.getImagePath returns the opened image path and fails after close
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
        if (!modules.toFile().exists()) {
            throw new AssertionError("jimage file not found: " + modules);
        }

        ImageReader reader = ImageReader.open(modules);
        try {
            Path p = reader.getImagePath();
            if (p == null) {
                throw new AssertionError("getImagePath returned null");
            }
            Path expected = modules.toAbsolutePath().normalize();
            Path actual = p.toAbsolutePath().normalize();
            if (!expected.equals(actual)) {
                throw new AssertionError("expected " + expected + " but was " + actual);
            }
        } finally {
            reader.close();
        }

        try {
            reader.getImagePath();
            throw new AssertionError("getImagePath should fail after close");
        } catch (IllegalStateException expected) {
            // expected: requireOpen() rejects closed reader
        }

        try {
            reader.close();
            throw new AssertionError("second close should fail");
        } catch (IOException expected) {
            // expected: "image file already closed"
        }
    }
}
