/*
 * @test
 * @summary Checks public-observable constructor guards of BasicImageReader (null path and missing image file)
 * @modules java.base/jdk.internal.jimage
 * @run main BasicImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;

import jdk.internal.jimage.BasicImageReader;

public class BasicImageReaderWitupTest {

    static class Reader extends BasicImageReader {
        Reader(Path path, ByteOrder order) throws IOException {
            super(path, order);
        }
    }

    public static void main(String[] args) throws Exception {
        testNullPathRejected();
        testNullByteOrderRejected();
        testMissingImageFileRejected();
    }

    static void testNullPathRejected() {
        try {
            new Reader(null, ByteOrder.LITTLE_ENDIAN);
        } catch (NullPointerException expected) {
            return;
        } catch (IOException e) {
            throw new RuntimeException("Expected NullPointerException, got IOException", e);
        }
        throw new RuntimeException("Expected NullPointerException for null path");
    }

    static void testNullByteOrderRejected() {
        Path p = Paths.get("nonexistent-witup-" + System.nanoTime() + ".jimage");
        try {
            new Reader(p, null);
        } catch (NullPointerException expected) {
            return;
        } catch (IOException e) {
            throw new RuntimeException("Expected NullPointerException, got IOException", e);
        }
        throw new RuntimeException("Expected NullPointerException for null byte order");
    }

    static void testMissingImageFileRejected() {
        Path p = Paths.get("nonexistent-witup-" + System.nanoTime() + ".jimage");
        try {
            new Reader(p, ByteOrder.LITTLE_ENDIAN);
        } catch (IOException expected) {
            return;
        }
        throw new RuntimeException("Expected IOException for missing image file");
    }
}
