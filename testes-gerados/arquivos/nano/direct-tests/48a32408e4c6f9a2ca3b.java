/* @test    @summary Test the skip method of ZipInputStream for correct behavior and edge cases    @run main ZipInputStreamWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.ZipInputStream;

public class ZipInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testSkipNormal();
        testSkipZero();
        testSkipNegative();
        testSkipBeyondEOF();
    }

    static void testSkipNormal() throws IOException {
        byte[] data = new byte[1024];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (i % 256);
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        try (ZipInputStream zis = new ZipInputStream(bais)) {
            long skipped = zis.skip(100);
            assert skipped == 100 : "Expected to skip 100 bytes, but skipped " + skipped;
            long remaining = data.length - 100;
            long skipped2 = zis.skip(remaining);
            assert skipped2 == remaining : "Expected to skip remaining " + remaining + ", but skipped " + skipped2;
            long skipped3 = zis.skip(10);
            assert skipped3 == 0 : "Expected to skip 0 bytes after EOF, but skipped " + skipped3;
        }
    }

    static void testSkipZero() throws IOException {
        byte[] data = new byte[50];
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        try (ZipInputStream zis = new ZipInputStream(bais)) {
            long skipped = zis.skip(0);
            assert skipped == 0 : "Skipping zero should return 0, but got " + skipped;
        }
    }

    static void testSkipNegative() throws IOException {
        byte[] data = new byte[50];
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        try (ZipInputStream zis = new ZipInputStream(bais)) {
            boolean exceptionThrown = false;
            try {
                zis.skip(-10);
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
            }
            assert exceptionThrown : "Expected IllegalArgumentException for negative skip";
        }
    }

    static void testSkipBeyondEOF() throws IOException {
        byte[] data = new byte[20];
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        try (ZipInputStream zis = new ZipInputStream(bais)) {
            long skipped = zis.skip(100);
            assert skipped == 20 : "Expected to skip only available 20 bytes, but skipped " + skipped;
            long skipped2 = zis.skip(10);
            assert skipped2 == 0 : "After EOF, skip should return 0, but got " + skipped2;
        }
    }
}
