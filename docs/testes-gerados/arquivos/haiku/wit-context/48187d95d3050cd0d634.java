/*
 * @test
 * @summary Round-trip tests for the public ZipOutputStream(OutputStream) constructor
 * @run main ZipOutputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testAsciiEntryRoundTrip();
        testUtf8EntryNameRoundTrip();
        testEmptyEntryRoundTrip();
        System.out.println("PASSED");
    }

    static void testAsciiEntryRoundTrip() throws IOException {
        roundTrip("a.txt", "hello zip".getBytes("UTF-8"));
    }

    static void testUtf8EntryNameRoundTrip() throws IOException {
        roundTrip("\u00e9.txt", "data".getBytes("UTF-8"));
    }

    static void testEmptyEntryRoundTrip() throws IOException {
        roundTrip("empty.bin", new byte[0]);
    }

    static void roundTrip(String name, byte[] data) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(bos)) {
            zos.putNextEntry(new ZipEntry(name));
            zos.write(data);
            zos.closeEntry();
        }
        byte[] zip = bos.toByteArray();
        check(zip.length >= 4
                && zip[0] == 0x50 && zip[1] == 0x4B
                && zip[2] == 0x03 && zip[3] == 0x04,
                "local file header signature missing");
        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zip))) {
            ZipEntry e = zis.getNextEntry();
            check(e != null, "entry not found");
            check(name.equals(e.getName()), "entry name mismatch: " + e.getName());
            byte[] read = readAll(zis);
            check(Arrays.equals(data, read), "entry data mismatch");
            check(zis.getNextEntry() == null, "unexpected extra entry");
        }
    }

    static byte[] readAll(ZipInputStream zis) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[256];
        int n;
        while ((n = zis.read(buf, 0, buf.length)) != -1) {
            out.write(buf, 0, n);
        }
        return out.toByteArray();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
