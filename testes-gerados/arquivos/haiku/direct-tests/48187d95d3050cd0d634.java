/*
 * @test
 * @summary Verifies that ZipOutputStream(OutputStream) encodes entry names as UTF-8 and produces a readable archive
 * @run main ZipOutputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipOutputStreamWitupTest {

    private static int indexOf(byte[] haystack, byte[] needle) {
        outer:
        for (int i = 0; i <= haystack.length - needle.length; i++) {
            for (int j = 0; j < needle.length; j++) {
                if (haystack[i + j] != needle[j]) {
                    continue outer;
                }
            }
            return i;
        }
        return -1;
    }

    public static void main(String[] args) throws Exception {
        String name = "caf\u00e9.txt";
        byte[] data = "hello".getBytes(StandardCharsets.US_ASCII);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(bos)) {
            zos.putNextEntry(new ZipEntry(name));
            zos.write(data);
            zos.closeEntry();
        }
        byte[] zip = bos.toByteArray();

        byte[] utf8E acute = null;
        utf8Eacute = new byte[] {(byte) 0xC3, (byte) 0xA9};
        if (indexOf(zip, utf8Eacute) < 0) {
            throw new RuntimeException("Entry name is not encoded as UTF-8 in the archive");
        }

        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zip))) {
            ZipEntry e = zis.getNextEntry();
            if (e == null) {
                throw new RuntimeException("No entry found in archive");
            }
            if (!name.equals(e.getName())) {
                throw new RuntimeException("Unexpected entry name: " + e.getName());
            }
        }
    }
}
