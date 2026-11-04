/*
 * @test
 * @summary Verify that Node.getChildren() on a non-directory jimage node throws IllegalArgumentException with the "not a directory" message
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws IOException {
        Path modules = Paths.get(System.getProperty("java.home"), "lib", "modules");
        try (ImageReader reader = ImageReader.open(modules)) {
            List<ImageReader.Node> roots = reader.getRootDirectory().getChildren();
            if (roots.isEmpty()) {
                throw new AssertionError("root directory has no children");
            }
            ImageReader.Node file = findNonDirectory(roots, 0);
            if (file == null) {
                throw new AssertionError("no non-directory node found in image");
            }
            try {
                file.getChildren();
                throw new AssertionError("expected IllegalArgumentException");
            } catch (IllegalArgumentException e) {
                String msg = e.getMessage();
                if (msg == null || !msg.startsWith("not a directory: ")) {
                    throw new AssertionError("unexpected message: " + msg);
                }
            }
        }
    }

    private static ImageReader.Node findNonDirectory(List<ImageReader.Node> nodes, int depth)
            throws IOException {
        for (ImageReader.Node n : nodes) {
            List<ImageReader.Node> kids;
            try {
                kids = n.getChildren();
            } catch (IllegalArgumentException e) {
                return n;
            }
            if (depth < 8) {
                ImageReader.Node found = findNonDirectory(kids, depth + 1);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }
}
