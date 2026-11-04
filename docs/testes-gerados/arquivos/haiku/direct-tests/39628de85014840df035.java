/*
 * @test
 * @summary Verifies ImageReader.open(Path) rejects a null path and reports IOException for a missing image file
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        boolean npeThrown = false;
        try {
            ImageReader.open((Path) null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("expected NullPointerException for null path");
        }

        Path missing = Paths.get("witup-nonexistent-image-file-4f2a9c.jimage");
        boolean ioThrown = false;
        try {
            ImageReader.open(missing);
        } catch (IOException e) {
            ioThrown = true;
        }
        if (!ioThrown) {
            throw new AssertionError("expected IOException for missing image file");
        }
    }
}
