/*
 * @test
 * @summary Verifies ImageReader.findLocation on an open reader and after close
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */

import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageLocation;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Path image = Paths.get(System.getProperty("java.home"), "lib", "modules");

        ImageReader reader = ImageReader.open(image);
        try {
            ImageLocation found = reader.findLocation("java.base", "java/lang/Object.class");
            check(found != null, "java/lang/Object.class should be found in java.base");

            ImageLocation missing = reader.findLocation("java.base", "no/such/Type.class");
            check(missing == null, "non-existent resource should yield null");
        } finally {
            reader.close();
        }

        ImageReader closedReader = ImageReader.open(image);
        closedReader.close();
        boolean thrown = false;
        try {
            closedReader.findLocation("java.base", "java/lang/Object.class");
        } catch (IllegalStateException e) {
            thrown = true;
            check("image file closed".equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
        }
        check(thrown, "findLocation on closed reader should throw IllegalStateException");
    }
}
