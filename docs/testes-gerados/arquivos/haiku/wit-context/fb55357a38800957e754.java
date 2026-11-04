/*
 * @test
 * @summary Verifies observable close() behavior of ImageReader, including double close and shared readers
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    static void fail(String msg) {
        throw new AssertionError(msg);
    }

    public static void main(String[] args) throws Exception {
        Path modules = Paths.get(System.getProperty("java.home"), "lib", "modules");
        if (!Files.exists(modules)) {
            System.out.println("lib/modules not found; skipping");
            return;
        }

        ImageReader r = ImageReader.open(modules);
        r.close();

        try {
            r.close();
            fail("second close() should throw IOException");
        } catch (IOException expected) {
            if (!"image file already closed".equals(expected.getMessage())) {
                fail("unexpected message: " + expected.getMessage());
            }
        }

        try {
            r.getRootDirectory();
            fail("getRootDirectory() after close should throw IOException");
        } catch (IOException expected) {
        }

        try {
            r.getHeader();
            fail("getHeader() after close should throw IllegalStateException");
        } catch (IllegalStateException expected) {
        }

        ImageReader r1 = ImageReader.open(modules);
        ImageReader r2 = ImageReader.open(modules);
        r1.close();
        if (r2.getRootDirectory() == null) {
            fail("remaining reader should still be usable");
        }
        try {
            r1.close();
            fail("closing r1 twice should throw IOException");
        } catch (IOException expected) {
        }
        r2.close();
        try {
            r2.close();
            fail("closing r2 twice should throw IOException");
        } catch (IOException expected) {
        }
    }
}
