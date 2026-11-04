/*
 * @test
 * @summary Verifies ImageReader.getRootDirectory returns a root directory while open and throws IOException after close
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        Path modules = Paths.get(System.getProperty("java.home"), "lib", "modules");
        if (!Files.exists(modules)) {
            throw new RuntimeException("jimage file not found: " + modules);
        }

        ImageReader reader = ImageReader.open(modules, ByteOrder.nativeOrder());
        if (reader.getRootDirectory() == null) {
            throw new RuntimeException("getRootDirectory returned null on open reader");
        }
        reader.close();

        try {
            reader.getRootDirectory();
            throw new RuntimeException("expected IOException from getRootDirectory after close");
        } catch (IOException expected) {
            // expected: ensureOpen() rejects closed reader
        }
    }
}
