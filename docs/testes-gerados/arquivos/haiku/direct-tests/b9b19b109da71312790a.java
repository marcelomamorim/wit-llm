/*
 * @test
 * @summary Verifies ImageReader.getHeader() returns a header while open and rejects calls after close
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */

import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageHeader;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        Path modules = Paths.get(System.getProperty("java.home"), "lib", "modules");

        ImageReader reader = ImageReader.open(modules, ByteOrder.nativeOrder());
        try {
            ImageHeader first = reader.getHeader();
            if (first == null) {
                throw new RuntimeException("getHeader() returned null while open");
            }
            ImageHeader second = reader.getHeader();
            if (second == null) {
                throw new RuntimeException("second getHeader() call returned null");
            }
        } finally {
            reader.close();
        }

        boolean thrown = false;
        try {
            reader.getHeader();
        } catch (IllegalStateException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getHeader() after close() did not throw IllegalStateException");
        }
    }
}
