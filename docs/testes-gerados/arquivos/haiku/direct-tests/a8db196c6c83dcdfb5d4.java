/*
 * @test
 * @summary ImageReader.getResource(ImageLocation) must fail with IllegalStateException once the reader is closed
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */

import java.nio.ByteOrder;
import java.nio.file.Paths;
import java.nio.file.Path;

import jdk.internal.jimage.ImageLocation;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        Path modules = Paths.get(System.getProperty("java.home"), "lib", "modules");
        ImageReader reader = ImageReader.open(modules, ByteOrder.nativeOrder());
        reader.close();
        try {
            reader.getResource((ImageLocation) null);
            throw new AssertionError("expected IllegalStateException after close");
        } catch (IllegalStateException expected) {
            if (!"image file closed".equals(expected.getMessage())) {
                throw new AssertionError("unexpected message: " + expected.getMessage());
            }
        }
    }
}
