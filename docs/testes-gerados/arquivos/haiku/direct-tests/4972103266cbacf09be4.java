/*
 * @test
 * @summary Verifies argument validation of ImageReader.open(Path, ByteOrder) and IOException for a missing image file
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    interface Call {
        void run() throws Exception;
    }

    public static void main(String[] args) throws Exception {
        testNullImagePath();
        testNullByteOrder();
        testBothNullArguments();
        testMissingFileThrowsIOException();
        System.out.println("PASSED");
    }

    static void expectNPE(String name, Call call) {
        try {
            call.run();
        } catch (NullPointerException e) {
            return;
        } catch (Exception e) {
            throw new RuntimeException(name + ": unexpected exception " + e, e);
        }
        throw new RuntimeException(name + ": expected NullPointerException");
    }

    static void testNullImagePath() {
        expectNPE("nullImagePath",
                () -> ImageReader.open(null, ByteOrder.LITTLE_ENDIAN));
    }

    static void testNullByteOrder() {
        Path p = Paths.get("nonexistent-image-witup.jimage");
        expectNPE("nullByteOrder",
                () -> ImageReader.open(p, null));
    }

    static void testBothNullArguments() {
        expectNPE("bothNull",
                () -> ImageReader.open(null, null));
    }

    static void testMissingFileThrowsIOException() {
        Path p = Paths.get("definitely-missing-witup-" + System.nanoTime() + ".jimage");
        try {
            ImageReader.open(p, ByteOrder.nativeOrder());
        } catch (IOException e) {
            return;
        } catch (Exception e) {
            throw new RuntimeException("missingFile: unexpected exception " + e, e);
        }
        throw new RuntimeException("missingFile: expected IOException");
    }
}
