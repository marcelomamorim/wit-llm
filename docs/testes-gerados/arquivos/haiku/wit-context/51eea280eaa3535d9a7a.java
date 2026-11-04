/*
 * @test
 * @summary Witup generated checks for ImageReader.findLocation: open-state guard and basic lookup
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */

import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageLocation;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        Path modules = Paths.get(System.getProperty("java.home"), "lib", "modules");

        try (ImageReader reader = ImageReader.open(modules)) {
            ImageLocation loc = reader.findLocation("/java.base/java/lang/Object.class");
            if (loc == null) {
                throw new AssertionError("expected location for java.lang.Object");
            }
            ImageLocation missing = reader.findLocation("/no.such.module/NoSuch.class");
            if (missing != null) {
                throw new AssertionError("expected null for missing resource");
            }
        }

        ImageReader closed = ImageReader.open(modules);
        closed.close();
        try {
            closed.findLocation("/java.base/java/lang/Object.class");
            throw new AssertionError("expected IllegalStateException after close");
        } catch (IllegalStateException expected) {
            // expected: requireOpen() guard
        }
    }
}
