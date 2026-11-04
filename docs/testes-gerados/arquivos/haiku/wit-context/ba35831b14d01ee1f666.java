/*
 * @test
 * @summary Verify ImageReader.getName() returns a stable non-null name for an open image and throws IllegalStateException after close
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */

import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        Path modules = Paths.get(System.getProperty("java.home"), "lib", "modules");

        ImageReader reader = ImageReader.open(modules);
        try {
            String name = reader.getName();
            if (name == null) {
                throw new AssertionError("getName() returned null on an open image");
            }
            String again = reader.getName();
            if (!name.equals(again)) {
                throw new AssertionError("getName() not stable: " + name + " vs " + again);
            }
        } finally {
            reader.close();
        }

        ImageReader closed = ImageReader.open(modules);
        closed.close();
        try {
            closed.getName();
            throw new AssertionError("expected IllegalStateException after close");
        } catch (IllegalStateException expected) {
            // expected: requireOpen() rejects closed reader
        }
    }
}
