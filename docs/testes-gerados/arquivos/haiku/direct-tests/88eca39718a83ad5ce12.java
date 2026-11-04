/*
 * @test
 * @summary Verifies ImageReader.getResource(String) returns class bytes for an existing resource, null for a missing one, and throws IllegalStateException after close.
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */

import java.nio.file.Path;
import java.nio.file.Paths;
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

        byte[] data = reader.getResource("/java.base/java/lang/Object.class");
        check(data != null, "expected bytes for java/lang/Object.class");
        check(data.length > 4, "class bytes too short");
        check((data[0] & 0xFF) == 0xCA && (data[1] & 0xFF) == 0xFE
                && (data[2] & 0xFF) == 0xBA && (data[3] & 0xFF) == 0xBE,
                "expected class file magic number");

        byte[] missing = reader.getResource("/java.base/does/not/Exist.class");
        check(missing == null, "expected null for missing resource");

        reader.close();

        boolean thrown = false;
        try {
            reader.getResource("/java.base/java/lang/Object.class");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalStateException after close");
    }
}
