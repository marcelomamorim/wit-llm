/*
 * @test
 * @summary Verify BasicImageReader.getResource(String) against the runtime jimage
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
        Reader(Path p) throws IOException {
            super(p, ByteOrder.nativeOrder());
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        Path modules = Paths.get(System.getProperty("java.home"), "lib", "modules");
        Reader reader = new Reader(modules);
        try {
            byte[] obj = reader.getResource("/java.base/java/lang/Object.class");
            check(obj != null, "Object.class not found");
            check(obj.length > 4, "Object.class content too short");
            check((obj[0] & 0xFF) == 0xCA && (obj[1] & 0xFF) == 0xFE
                    && (obj[2] & 0xFF) == 0xBA && (obj[3] & 0xFF) == 0xBE,
                    "Object.class does not start with class file magic");

            byte[] again = reader.getResource("/java.base/java/lang/Object.class");
            check(again != null && again.length == obj.length, "repeated lookup differs in length");

            byte[] missing = reader.getResource("/java.base/java/lang/NoSuchClassWitup.class");
            check(missing == null, "missing resource should return null");

            boolean npe = false;
            try {
                reader.getResource(null);
            } catch (NullPointerException e) {
                npe = true;
            }
            check(npe, "null name should throw NullPointerException");
        } finally {
            reader.close();
        }
    }
}
