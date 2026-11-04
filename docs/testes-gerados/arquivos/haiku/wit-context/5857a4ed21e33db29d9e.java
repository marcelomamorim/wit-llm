/*
 * @test
 * @summary Witup generated checks for BasicImageReader.getResource(String) on the runtime jimage
 * @modules java.base/jdk.internal.jimage
 * @run main BasicImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.BasicImageReader;

public class BasicImageReaderWitupTest {

    static final class Reader extends BasicImageReader {
        Reader(Path path) throws IOException {
            super(path, ByteOrder.nativeOrder());
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Path modules = Paths.get(System.getProperty("java.home"), "lib", "modules");
        try (Reader reader = new Reader(modules)) {
            boolean npe = false;
            try {
                reader.getResource(null);
            } catch (NullPointerException e) {
                npe = true;
            }
            check(npe, "getResource(null) must throw NullPointerException");

            byte[] missing = reader.getResource("/java.base/nonexistent/Missing.class");
            check(missing == null, "absent resource must yield null");

            byte[] object = reader.getResource("/java.base/java/lang/Object.class");
            check(object != null, "java.lang.Object class resource must be present");
            check(object.length > 4, "class resource must have content");
            check((object[0] & 0xFF) == 0xCA && (object[1] & 0xFF) == 0xFE
                    && (object[2] & 0xFF) == 0xBA && (object[3] & 0xFF) == 0xBE,
                    "class resource must start with CAFEBABE magic");
        }
    }
}
