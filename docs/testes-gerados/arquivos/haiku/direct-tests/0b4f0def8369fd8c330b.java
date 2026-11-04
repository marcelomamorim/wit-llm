/*
 * @test
 * @summary Verifies ImageReader.getModuleNames returns module names from the runtime jimage and rejects use after close
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Path image = Paths.get(System.getProperty("java.home"), "lib", "modules");
        ImageReader reader = ImageReader.open(image);

        String[] names = reader.getModuleNames();
        check(names != null, "getModuleNames returned null");
        check(names.length > 0, "getModuleNames returned no modules");

        boolean foundBase = false;
        for (String name : names) {
            check(name != null, "module name is null");
            check(!name.startsWith("/modules"), "module name still has prefix: " + name);
            if (name.equals("java.base")) {
                foundBase = true;
            }
        }
        check(foundBase, "java.base not found in module names");

        reader.close();

        try {
            reader.getModuleNames();
            throw new RuntimeException("expected IllegalStateException after close");
        } catch (IllegalStateException expected) {
            // expected: requireOpen() rejects closed reader
        }

        try {
            reader.close();
            throw new RuntimeException("expected IOException on second close");
        } catch (IOException expected) {
            // expected: image file already closed
        }
    }
}
