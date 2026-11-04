/*
 * @test
 * @summary ImageReader.getResource(String) returns class bytes while open and rejects calls after close
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws IOException {
        Path modules = Paths.get(System.getProperty("java.home"), "lib", "modules");
        ImageReader reader = ImageReader.open(modules);
        byte[] bytes = reader.getResource("/java.base/java/lang/Object.class");
        if (bytes == null || bytes.length < 4
                || (bytes[0] & 0xFF) != 0xCA || (bytes[1] & 0xFF) != 0xFE
                || (bytes[2] & 0xFF) != 0xBA || (bytes[3] & 0xFF) != 0xBE) {
            throw new RuntimeException("unexpected resource content");
        }
        reader.close();
        try {
            reader.getResource("/java.base/java/lang/Object.class");
            throw new RuntimeException("expected IllegalStateException");
        } catch (IllegalStateException expected) {
        }
    }
}
