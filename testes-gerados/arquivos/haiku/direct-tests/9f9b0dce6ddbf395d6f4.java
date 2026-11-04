/*
 * @test
 * @summary ZipInputStream constructor null-argument checks and empty stream behavior
 * @run main ZipInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipInputStreamWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void main(String[] args) throws Exception {
        // Valid stream with explicit charset: empty input yields no entries
        try (ZipInputStream zis = new ZipInputStream(
                new ByteArrayInputStream(new byte[0]), StandardCharsets.UTF_8)) {
            ZipEntry e = zis.getNextEntry();
            check(e == null, "expected null entry for empty input");
        }

        // Single-argument constructor delegates to UTF-8 variant
        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(new byte[0]))) {
            ZipEntry e = zis.getNextEntry();
            check(e == null, "expected null entry for empty input (default charset ctor)");
        }

        // Null input stream must be rejected with the documented message
        try {
            new ZipInputStream(null, StandardCharsets.UTF_8);
            throw new AssertionError("NullPointerException expected for null input");
        } catch (NullPointerException npe) {
            check("in is null".equals(npe.getMessage()),
                  "unexpected message for null input: " + npe.getMessage());
        }

        // Null charset must be rejected with the documented message
        try {
            new ZipInputStream(new ByteArrayInputStream(new byte[0]), null);
            throw new AssertionError("NullPointerException expected for null charset");
        } catch (NullPointerException npe) {
            check("charset is null".equals(npe.getMessage()),
                  "unexpected message for null charset: " + npe.getMessage());
        }
    }
}
