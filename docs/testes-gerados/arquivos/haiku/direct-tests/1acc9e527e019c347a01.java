/*
 * @test
 * @summary Tests observable behavior of ZipOutputStream.finish()
 * @run main ZipOutputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testFinishEmptyStreamWritesEndRecord();
        testFinishIsIdempotent();
        testFinishAfterCloseThrows();
        testFinishClosesOpenEntry();
    }

    static void testFinishEmptyStreamWritesEndRecord() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(bos);
        zos.finish();
        byte[] b = bos.toByteArray();
        if (b.length != 22) {
            throw new RuntimeException("Expected 22 bytes, got " + b.length);
        }
        if (b[0] != 0x50 || b[1] != 0x4b || b[2] != 0x05 || b[3] != 0x06) {
            throw new RuntimeException("END signature not found");
        }
    }

    static void testFinishIsIdempotent() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(bos);
        zos.finish();
        int first = bos.toByteArray().length;
        zos.finish();
        int second = bos.toByteArray().length;
        if (first != second) {
            throw new RuntimeException("Second finish() changed output: " + first + " vs " + second);
        }
    }

    static void testFinishAfterCloseThrows() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(bos);
        zos.close();
        boolean thrown = false;
        try {
            zos.finish();
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("finish() after close() did not throw IOException");
        }
    }

    static void testFinishClosesOpenEntry() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(bos);
        zos.putNextEntry(new ZipEntry("a.txt"));
        zos.write("hello".getBytes(StandardCharsets.UTF_8));
        zos.finish();

        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(bos.toByteArray()));
        ZipEntry e = zis.getNextEntry();
        if (e == null || !"a.txt".equals(e.getName())) {
            throw new RuntimeException("Entry a.txt not found");
        }
        ByteArrayOutputStream content = new ByteArrayOutputStream();
        byte[] buf = new byte[64];
        int n;
        while ((n = zis.read(buf)) != -1) {
            content.write(buf, 0, n);
        }
        String s = new String(content.toByteArray(), StandardCharsets.UTF_8);
        if (!"hello".equals(s)) {
            throw new RuntimeException("Unexpected content: " + s);
        }
        zis.close();
    }
}
