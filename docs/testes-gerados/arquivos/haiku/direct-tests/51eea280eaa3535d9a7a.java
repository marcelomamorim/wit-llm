/*
 * @test
 * @summary Tests ImageReader.findLocation on the runtime jimage, including a closed reader
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        Path image = Paths.get(System.getProperty("java.home"), "lib", "modules");

        ImageReader reader = ImageReader.open(image);
        Object found = reader.findLocation("/java.base/java/lang/Object.class");
        if (found == null) {
            throw new AssertionError("expected a location for java.lang.Object");
        }
        Object missing = reader.findLocation("/java.base/does/not/Exists.class");
        if (missing != null) {
            throw new AssertionError("expected null for a missing name");
        }
        reader.close();

        try {
            reader.findLocation("/java.base/java/lang/Object.class");
            throw new AssertionError("expected IllegalStateException after close");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
