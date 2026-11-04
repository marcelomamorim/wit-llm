/*
 * @test
 * @summary ZipInputStream(InputStream) delegates to the charset constructor: null input throws NPE and UTF-8 entry names are decoded
 * @run main ZipInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNullInputThrowsNPE();
        testUtf8EntryNameDecoded();
    }

    static void testNullInputThrowsNPE() {
        try {
            new ZipInputStream(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected: the delegated constructor rejects a null stream
        }
    }

    static void testUtf8EntryNameDecoded() throws IOException {
        String name = "caf\u00e9.txt";
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(bos)) {
            zos.putNextEntry(new ZipEntry(name));
            zos.write("hi".getBytes(StandardCharsets.US_ASCII));
            zos.closeEntry();
        }
        try (ZipInputStream zis = new ZipInputStream(
                new ByteArrayInputStream(bos.toByteArray()))) {
            ZipEntry e = zis.getNextEntry();
            if (e == null) {
                throw new AssertionError("expected an entry");
            }
            if (!name.equals(e.getName())) {
                throw new AssertionError("unexpected name: " + e.getName());
            }
        }
    }
}
