/*
 * @test
 * @summary ImageReader.getAttributes must reject calls after the reader is closed
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */

import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        Path image = Paths.get(System.getProperty("java.home"), "lib", "modules");
        if (!Files.exists(image)) {
            System.out.println("No jimage file found at " + image + ", skipping");
            return;
        }

        ImageReader reader = ImageReader.open(image, ByteOrder.nativeOrder());
        if (reader.getHeader() == null) {
            throw new RuntimeException("getHeader returned null on open reader");
        }
        reader.close();

        try {
            reader.getAttributes(0);
            throw new RuntimeException("getAttributes did not throw after close");
        } catch (IllegalStateException e) {
            if (!"image file closed".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage(), e);
            }
        }
    }
}
