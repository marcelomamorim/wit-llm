/*
 * @test
 * @summary Verify ImageReader.getResource(Node) returns resource bytes while open and throws IOException after close
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
        var node = reader.findNode("/modules/java.base/java/lang/Object.class");
        if (node == null) {
            throw new RuntimeException("node not found");
        }
        byte[] bytes = reader.getResource(node);
        if (bytes == null || bytes.length == 0) {
            throw new RuntimeException("empty resource");
        }
        reader.close();
        try {
            reader.getResource(node);
            throw new RuntimeException("expected IOException after close");
        } catch (IOException expected) {
        }
    }
}
