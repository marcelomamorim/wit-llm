/*
 * @test
 * @summary Verifies the null-argument contract of ImageReader.open, which is observable through the public API
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */

import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;

import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        Path path = Paths.get("nonexistent.jimage");

        try {
            ImageReader.open(null);
            throw new RuntimeException("expected NullPointerException for null path");
        } catch (NullPointerException expected) {
        }

        try {
            ImageReader.open(path, null);
            throw new RuntimeException("expected NullPointerException for null byte order");
        } catch (NullPointerException expected) {
        }

        try {
            ImageReader.open(null, ByteOrder.nativeOrder());
            throw new RuntimeException("expected NullPointerException for null path");
        } catch (NullPointerException expected) {
        }
    }
}
