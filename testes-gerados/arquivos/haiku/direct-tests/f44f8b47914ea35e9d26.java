/*
 * @test
 * @summary Verifies ImageReader.getByteOrder() returns the byte order used to open the image and fails after close
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */

import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;

import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static Path modulesImage() {
        return Paths.get(System.getProperty("java.home"), "lib", "modules");
    }

    static void testNativeOrderExplicit() throws Exception {
        ImageReader reader = ImageReader.open(modulesImage(), ByteOrder.nativeOrder());
        try {
            check(reader.getByteOrder() == ByteOrder.nativeOrder(),
                  "getByteOrder should return the explicitly opened native order");
        } finally {
            reader.close();
        }
    }

    static void testDefaultOpenUsesNativeOrder() throws Exception {
        try (ImageReader reader = ImageReader.open(modulesImage())) {
            check(reader.getByteOrder() == ByteOrder.nativeOrder(),
                  "default open should use native byte order");
        }
    }

    static void testGetByteOrderAfterCloseThrows() throws Exception {
        ImageReader reader = ImageReader.open(modulesImage(), ByteOrder.nativeOrder());
        reader.close();
        try {
            reader.getByteOrder();
            throw new RuntimeException("getByteOrder after close should throw IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected: requireOpen() reports the closed image
        }
    }

    public static void main(String[] args) throws Exception {
        testNativeOrderExplicit();
        testDefaultOpenUsesNativeOrder();
        testGetByteOrderAfterCloseThrows();
    }
}
