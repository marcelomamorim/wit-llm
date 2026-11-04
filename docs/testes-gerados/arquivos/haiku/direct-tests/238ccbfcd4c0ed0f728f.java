/*
 * @test
 * @summary Tests ZipOutputStream(OutputStream, Charset) constructor: null charset rejection and charset-aware round trip of entry names
 * @run main ZipOutputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNullCharsetThrowsNPE();
        testRoundTripUtf8();
        testRoundTripIso88591();
    }

    static void testNullCharsetThrowsNPE() {
        try {
            new ZipOutputStream(new ByteArrayOutputStream(), (Charset) null);
        } catch (NullPointerException e) {
            if (!"charset is null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected NPE message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null charset");
    }

    static void testRoundTripUtf8() throws IOException {
        roundTrip(StandardCharsets.UTF_8, "caf\u00E9.txt", "hello utf8".getBytes(StandardCharsets.UTF_8));
    }

    static void testRoundTripIso88591() throws IOException {
        roundTrip(StandardCharsets.ISO_8859_1, "caf\u00E9.txt", "hello latin1".getBytes(StandardCharsets.ISO_8859_1));
    }

    static void roundTrip(Charset cs, String name, byte[] data) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(bos, cs);
        zos.putNextEntry(new ZipEntry(name));
        zos.write(data);
        zos.closeEntry();
        zos.close();

        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(bos.toByteArray()), cs);
        ZipEntry entry = zis.getNextEntry();
        if (entry == null) {
            throw new RuntimeException("No entry read back for charset " + cs);
        }
        if (!name.equals(entry.getName())) {
            throw new RuntimeException("Name mismatch for " + cs + ": " + entry.getName());
        }
        byte[] read = readAll(zis);
        if (!java.util.Arrays.equals(data, read)) {
            throw new RuntimeException("Content mismatch for charset " + cs);
        }
    }

    static byte[] readAll(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[256];
        int n;
        while ((n = in.read(buf)) != -1) {
            out.write(buf, 0, n);
        }
        return out.toByteArray();
    }
}
