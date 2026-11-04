/*
 * @test
 * @summary Tests ImageReader.findNode on the runtime jimage and its behavior after close
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;

import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static Path runtimeImage() {
        return Paths.get(System.getProperty("java.home"), "lib", "modules");
    }

    static void testFindExistingNode() throws IOException {
        try (ImageReader reader = ImageReader.open(runtimeImage())) {
            Object node = reader.findNode("/java.base/java/lang/Object.class");
            check(node != null, "expected node for java/lang/Object.class");
        }
    }

    static void testFindMissingNode() throws IOException {
        try (ImageReader reader = ImageReader.open(runtimeImage())) {
            Object node = reader.findNode("/java.base/no/such/Clazz.class");
            check(node == null, "expected null for missing node");
        }
    }

    static void testFindNodeAfterCloseThrows() throws IOException {
        ImageReader reader = ImageReader.open(runtimeImage());
        reader.close();
        boolean thrown = false;
        try {
            reader.findNode("/java.base/java/lang/Object.class");
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "expected IOException from findNode after close");
    }

    public static void main(String[] args) throws IOException {
        testFindExistingNode();
        testFindMissingNode();
        testFindNodeAfterCloseThrows();
    }
}
