/*
 * @test
 * @summary Verifies ClassLoader.getSystemResourceAsStream returns null for a missing resource and a readable stream for an existing class resource
 * @run main ClassLoaderWitupTest
 */

import java.io.InputStream;

public class ClassLoaderWitupTest {
    public static void main(String[] args) throws Exception {
        InputStream missing = ClassLoader.getSystemResourceAsStream("witup/no/such/resource-12345.txt");
        if (missing != null) {
            missing.close();
            throw new RuntimeException("expected null for missing resource");
        }

        InputStream in = ClassLoader.getSystemResourceAsStream("java/lang/Object.class");
        if (in == null) {
            throw new RuntimeException("expected stream for java/lang/Object.class");
        }
        try (InputStream s = in) {
            int b0 = s.read();
            int b1 = s.read();
            int b2 = s.read();
            int b3 = s.read();
            if (b0 != 0xCA || b1 != 0xFE || b2 != 0xBA || b3 != 0xBE) {
                throw new RuntimeException("unexpected class file magic");
            }
        }
    }
}
