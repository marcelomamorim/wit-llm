/*
 * @test
 * @summary ImageReader.getStrings returns a non-null reader while open and fails with IllegalStateException after close
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */

import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        String home = System.getProperty("java.home");
        ImageReader reader = ImageReader.open(Paths.get(home, "lib", "modules"));
        try {
            Object strings = reader.getStrings();
            if (strings == null) {
                throw new AssertionError("getStrings() returned null on an open reader");
            }
        } finally {
            reader.close();
        }

        try {
            reader.getStrings();
            throw new AssertionError("expected IllegalStateException after close");
        } catch (IllegalStateException expected) {
            // expected: requireOpen() rejects closed reader
        }

        try {
            reader.close();
            throw new AssertionError("expected IOException on second close");
        } catch (java.io.IOException expected) {
            // expected: close() reports already closed
        }
    }
}
