/*
 * @test
 * @summary ImageReader directory nodes must reject getLocation() with IllegalArgumentException
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */

import java.nio.file.Path;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        Path modules = Path.of(System.getProperty("java.home"), "lib", "modules");
        try (ImageReader reader = ImageReader.open(modules)) {
            var root = reader.getRootDirectory();
            try {
                root.getLocation();
                throw new AssertionError("expected IllegalArgumentException");
            } catch (IllegalArgumentException expected) {
                String msg = expected.getMessage();
                if (msg == null || !msg.startsWith("not a resource: ")) {
                    throw new AssertionError("unexpected message: " + msg);
                }
            }
        }
    }
}
