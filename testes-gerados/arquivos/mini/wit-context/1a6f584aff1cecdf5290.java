/*
 * @test
 * @summary Test ZipOutputStream.setLevel(int) for valid and invalid compression levels
 * @run main ZipOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.util.zip.ZipOutputStream;
import java.util.zip.Deflater;

public class ZipOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidLevels();
        testInvalidLevels();
    }

    private static void testValidLevels() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);

        // Valid levels: 0 to 9 and -1
        for (int level = -1; level <= 9; level++) {
            zos.setLevel(level);
            // Check that the level was set correctly in the underlying Deflater
            // Since Deflater.getLevel() is public, we can verify
            int defLevel = getDeflaterLevel(zos);
            if (defLevel != level) {
                throw new AssertionError("Expected deflater level " + level + " but got " + defLevel);
            }
        }

        zos.close();
    }

    private static void testInvalidLevels() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);

        int[] invalidLevels = { -2, 10, 100, Integer.MIN_VALUE, Integer.MAX_VALUE };

        for (int level : invalidLevels) {
            try {
                zos.setLevel(level);
                throw new AssertionError("Expected IllegalArgumentException for level: " + level);
            } catch (IllegalArgumentException expected) {
                // Expected exception
            }
        }

        zos.close();
    }

    // Helper method to access the deflater level via reflection since def is protected in DeflaterOutputStream
    private static int getDeflaterLevel(ZipOutputStream zos) throws Exception {
        java.lang.reflect.Field defField = ZipOutputStream.class.getSuperclass().getDeclaredField("def");
        defField.setAccessible(true);
        Deflater def = (Deflater) defField.get(zos);
        return def.getLevel();
    }
}
