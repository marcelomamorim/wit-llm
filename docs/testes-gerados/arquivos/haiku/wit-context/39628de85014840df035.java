/*
 * @test
 * @summary Verify ImageReader.open(Path) rejects a null path and reports a missing image file with IOException
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        try {
            ImageReader.open((Path) null);
            throw new AssertionError("NullPointerException expected");
        } catch (NullPointerException expected) {
        }
        Path missing = Paths.get("nonexistent-image-file-witup.jimage");
        try {
            ImageReader.open(missing);
            throw new AssertionError("IOException expected");
        } catch (IOException expected) {
        }
    }
}
