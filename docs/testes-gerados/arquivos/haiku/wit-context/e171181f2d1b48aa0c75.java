/*
 * @test
 * @summary ImageReader.findNode must throw IOException once the reader is closed and return null for a missing name on an open image
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
            System.out.println("lib/modules not found, skipping");
            return;
        }

        ImageReader closedReader = ImageReader.open(modules);
        closedReader.close();
        try {
            closedReader.findNode("/java.base/java/lang/Object.class");
            throw new AssertionError("findNode on closed reader did not throw IOException");
        } catch (IOException expected) {
            System.out.println("Got expected: " + expected.getMessage());
        }

        ImageReader openReader = ImageReader.open(modules);
        try {
            Object missing = openReader.findNode("/nonexistent/module/Missing.class");
            if (missing != null) {
                throw new AssertionError("findNode returned a node for a missing name");
            }
        } finally {
            openReader.close();
        }
    }
}
