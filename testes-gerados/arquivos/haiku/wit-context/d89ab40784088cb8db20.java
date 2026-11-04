/*
 * @test
 * @summary Witup generated tests for ClassLoader.getSystemResourceAsStream(String)
 * @run main ClassLoaderWitupTest
 */

import java.io.InputStream;

public class ClassLoaderWitupTest {
    public static void main(String[] args) throws Exception {
        try (InputStream in = ClassLoader.getSystemResourceAsStream("witup/nonexistent/resource/missing.txt")) {
            if (in != null) {
                throw new AssertionError("Expected null for missing resource");
            }
        }

        try (InputStream in = ClassLoader.getSystemResourceAsStream("java/lang/Object.class")) {
            if (in == null) {
                throw new AssertionError("Expected stream for java/lang/Object.class");
            }
            int b1 = in.read();
            int b2 = in.read();
            int b3 = in.read();
            int b4 = in.read();
            if (b1 != 0xCA || b2 != 0xFE || b3 != 0xBA || b4 != 0xBE) {
                throw new AssertionError("Unexpected class file magic");
            }
        }
    }
}
