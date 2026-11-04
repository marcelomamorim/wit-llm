/*
 * @test
 * @summary Verify null argument checks in the ZipInputStream(InputStream, Charset) constructor
 * @run main ZipInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipInputStream;

public class ZipInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNullInputThrowsNpe();
        testNullCharsetThrowsNpe();
        testNullInputWithNullCharsetThrowsNpe();
        testValidArgumentsAccepted();
        testSingleArgConstructorNullInputThrowsNpe();
    }

    static void testNullInputThrowsNpe() {
        expectNpe(() -> new ZipInputStream(null, StandardCharsets.UTF_8), "null in");
    }

    static void testNullCharsetThrowsNpe() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        expectNpe(() -> new ZipInputStream(in, null), "null charset");
    }

    static void testNullInputWithNullCharsetThrowsNpe() {
        expectNpe(() -> new ZipInputStream(null, null), "null in and null charset");
    }

    static void testValidArgumentsAccepted() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        Charset cs = StandardCharsets.ISO_8859_1;
        try (ZipInputStream zis = new ZipInputStream(in, cs)) {
            if (zis == null) {
                throw new AssertionError("constructor returned null");
            }
        }
    }

    static void testSingleArgConstructorNullInputThrowsNpe() {
        expectNpe(() -> new ZipInputStream(null), "single-arg null in");
    }

    static void expectNpe(Runnable r, String label) {
        try {
            r.run();
        } catch (NullPointerException expected) {
            return;
        }
        throw new AssertionError("Expected NullPointerException for: " + label);
    }
}
