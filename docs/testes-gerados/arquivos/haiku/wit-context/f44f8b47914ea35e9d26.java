/*
 * @test
 * @summary Verifies ImageReader.getByteOrder returns the open byte order and throws IllegalStateException after close
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
            System.out.println("jimage not found, skipping");
            return;
        }

        ImageReader reader = ImageReader.open(image);
        try {
            check(reader.getByteOrder().equals(ByteOrder.nativeOrder()), "byte order mismatch");
            check(reader.getByteOrder().equals(reader.getByteOrder()), "byte order not stable");
        } finally {
            reader.close();
        }

        ImageReader closed = ImageReader.open(image);
        closed.close();
        boolean thrown = false;
        try {
            closed.getByteOrder();
        } catch (IllegalStateException expected) {
            thrown = true;
        }
        check(thrown, "expected IllegalStateException after close");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
