/*
 * @test
 * @summary Verify ImageReader.getEntryNames returns entries from the runtime jimage and enforces the open state
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        Path image = Paths.get(System.getProperty("java.home"), "lib", "modules");
        if (!Files.exists(image)) {
            throw new RuntimeException("jimage not found: " + image);
        }

        ImageReader reader = ImageReader.open(image);
        try {
            String[] names = reader.getEntryNames();
            if (names == null || names.length == 0) {
                throw new RuntimeException("getEntryNames returned no entries");
            }

            boolean found = false;
            for (String n : names) {
                if (n != null && n.endsWith("java/lang/Object.class")) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new RuntimeException("java/lang/Object.class not listed");
            }

            String[] first = names.clone();
            String[] second = reader.getEntryNames();
            Arrays.sort(first);
            Arrays.sort(second);
            if (!Arrays.equals(first, second)) {
                throw new RuntimeException("getEntryNames not stable across calls");
            }
        } finally {
            reader.close();
        }

        try {
            reader.getEntryNames();
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
