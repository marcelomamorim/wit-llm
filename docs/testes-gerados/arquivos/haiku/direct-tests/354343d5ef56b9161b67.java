/*
 * @test
 * @summary Verifies that ImageReader.getResourceBuffer rejects calls after the reader is closed
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        Path modules = Paths.get(System.getProperty("java.home"), "lib", "modules");
        if (!Files.exists(modules)) {
            System.out.println("No jimage file found at " + modules + "; skipping.");
            return;
        }

        ImageReader reader = ImageReader.open(modules);
        reader.close();

        try {
            reader.getResourceBuffer(null);
            throw new AssertionError("Expected IllegalStateException after close");
        } catch (IllegalStateException expected) {
            // requireOpen() must reject the call before loc is used
        }

        try {
            reader.close();
            throw new AssertionError("Expected IOException on second close");
        } catch (IOException expected) {
            // close() rejects an already closed reader
        }
    }
}
