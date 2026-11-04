/*
 * @test
 * @summary Test ZipInputStream.skip(long) method behavior
 * @run main ZipInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.ZipInputStream;

public class ZipInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testSkipNegative();
        testSkipZero();
        testSkipPartial();
        testSkipMoreThanAvailable();
    }

    private static void testSkipNegative() {
        try {
            ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(new byte[0]));
            zis.skip(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative skip length");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testSkipZero() throws IOException {
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(new byte[10]));
        long skipped = zis.skip(0);
        if (skipped != 0) {
            throw new RuntimeException("Expected skip(0) to return 0 but got " + skipped);
        }
        zis.close();
    }

    private static void testSkipPartial() throws IOException {
        byte[] data = new byte[100];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) i;
        }
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(data));
        long toSkip = 50;
        long skipped = zis.skip(toSkip);
        if (skipped != toSkip) {
            throw new RuntimeException("Expected to skip " + toSkip + " bytes but skipped " + skipped);
        }
        zis.close();
    }

    private static void testSkipMoreThanAvailable() throws IOException {
        byte[] data = new byte[20];
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(data));
        long toSkip = 100;
        long skipped = zis.skip(toSkip);
        if (skipped != data.length) {
            throw new RuntimeException("Expected to skip only available " + data.length + " bytes but skipped " + skipped);
        }
        zis.close();
    }
}
