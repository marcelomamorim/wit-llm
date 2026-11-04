/*
 * @test
 * @summary Verifies ImageReader.getResource(Node) returns class bytes for a valid node and fails after the reader is closed
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        String modules = Paths.get(System.getProperty("java.home"), "lib", "modules").toString();

        ImageReader reader = ImageReader.open(Paths.get(modules));
        var node = reader.findNode("/java.base/java/lang/Object.class");
        check(node != null, "findNode returned null for java/lang/Object.class");

        byte[] bytes = reader.getResource(node);
        check(bytes != null, "getResource returned null");
        check(bytes.length > 4, "resource too short: " + bytes.length);
        check((bytes[0] & 0xFF) == 0xCA && (bytes[1] & 0xFF) == 0xFE
                && (bytes[2] & 0xFF) == 0xBA && (bytes[3] & 0xFF) == 0xBE,
                "resource does not start with class file magic");

        byte[] again = reader.getResource(node);
        check(again.length == bytes.length, "repeated getResource returned different length");

        reader.close();

        boolean threw = false;
        try {
            reader.getResource(node);
        } catch (IOException e) {
            threw = true;
        }
        check(threw, "getResource(Node) after close did not throw IOException");
    }
}
