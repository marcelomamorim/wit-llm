/*
 * @test
 * @summary ImageReader.getHeader must return a header while open and throw IllegalStateException after close
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
            System.out.println("lib/modules not found, skipping");
            return;
        }

        ImageReader reader = ImageReader.open(modules);
        if (reader.getHeader() == null) {
            throw new RuntimeException("getHeader returned null on open reader");
        }

        reader.close();

        try {
            reader.getHeader();
            throw new RuntimeException("expected IllegalStateException from getHeader after close");
        } catch (IllegalStateException expected) {
            // expected: requireOpen() guards closed readers
        }

        try {
            reader.close();
            throw new RuntimeException("expected IOException on second close");
        } catch (IOException expected) {
            // expected: close() rejects an already closed reader
        }
    }
}
