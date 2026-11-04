/*
 * @test
 * @summary Verify that ImageReader.Node.getChildren() throws IllegalArgumentException for non-directory nodes and that the root directory returns children.
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        Path modules = Paths.get(System.getProperty("java.home"), "lib", "modules");
        try (ImageReader reader = ImageReader.open(modules)) {
            var resource = reader.findNode("/java.base/java/lang/Object.class");
            if (resource == null) {
                throw new AssertionError("Object.class resource not found in jimage");
            }
            try {
                resource.getChildren();
                throw new AssertionError("expected IllegalArgumentException for non-directory node");
            } catch (IllegalArgumentException e) {
                String msg = e.getMessage();
                if (msg == null || !msg.startsWith("not a directory: ")) {
                    throw new AssertionError("unexpected message: " + msg);
                }
            }

            List<?> rootChildren = reader.getRootDirectory().getChildren();
            if (rootChildren == null) {
                throw new AssertionError("root directory children must not be null");
            }
        }
    }
}
