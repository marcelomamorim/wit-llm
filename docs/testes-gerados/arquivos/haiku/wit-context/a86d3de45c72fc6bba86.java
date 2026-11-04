/*
 * @test
 * @summary Verifies ImageReader.getString(int) returns a string while open and throws IllegalStateException after close
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */

import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        Path modules = Paths.get(System.getProperty("java.home"), "lib", "modules");

        ImageReader reader = ImageReader.open(modules, ByteOrder.nativeOrder());
        try {
            String s = reader.getString(0);
            if (s == null) {
                throw new AssertionError("getString(0) returned null while open");
            }
        } finally {
            reader.close();
        }

        boolean thrown = false;
        try {
            reader.getString(0);
        } catch (IllegalStateException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getString(int) did not throw IllegalStateException after close");
        }
    }
}
