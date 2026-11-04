/*
 * @test
 * @summary Verifies that ZipOutputStream.setLevel is accepted for valid levels and that the level affects the produced compressed output
 * @run main ZipOutputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ZipOutputStreamWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static byte[] zip(int level, byte[] data) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(bos);
        zos.setLevel(level);
        zos.putNextEntry(new ZipEntry("a.txt"));
        zos.write(data, 0, data.length);
        zos.closeEntry();
        zos.close();
        return bos.toByteArray();
    }

    static byte[] unzipFirst(byte[] zipBytes) throws IOException {
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zipBytes));
        ZipEntry e = zis.getNextEntry();
        check(e != null, "no entry read back");
        check("a.txt".equals(e.getName()), "unexpected entry name: " + e.getName());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[512];
        int n;
        while ((n = zis.read(buf, 0, buf.length)) != -1) {
            out.write(buf, 0, n);
        }
        zis.close();
        return out.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        byte[] data = new byte[4096];
        for (int i = 0; i < data.length; i++) {
            data[i] = 'x';
        }

        int[] levels = {
            Deflater.NO_COMPRESSION,
            1,
            5,
            Deflater.BEST_SPEED,
            Deflater.BEST_COMPRESSION,
            Deflater.DEFAULT_COMPRESSION
        };
        for (int level : levels) {
            byte[] zipped = zip(level, data);
            byte[] restored = unzipFirst(zipped);
            check(restored.length == data.length,
                  "length mismatch at level " + level + ": " + restored.length);
            for (int i = 0; i < data.length; i++) {
                check(restored[i] == data[i],
                      "content mismatch at level " + level + " index " + i);
            }
        }

        byte[] noCompression = zip(Deflater.NO_COMPRESSION, data);
        byte[] bestCompression = zip(Deflater.BEST_COMPRESSION, data);
        check(bestCompression.length < noCompression.length,
              "BEST_COMPRESSION output (" + bestCompression.length
              + ") not smaller than NO_COMPRESSION output (" + noCompression.length + ")");
    }
}
