/* @test    @summary Test for ZipEntry.setSize(long) method to verify exception for invalid sizes    @run main ZipEntryWitupTest_Pedef0b16d62a */

import java.util.zip.ZipEntry;

public class ZipEntryWitupTest_Pedef0b16d62a {

    public static void main(String[] args) {
        testSetSizeThrowsOnNegative();
        testSetSizeAcceptsZero();
        testSetSizeAcceptsPositive();
    }

    private static void testSetSizeThrowsOnNegative() {
        ZipEntry ze = new ZipEntry("test.txt");
        boolean threw = false;
        try {
            ze.setSize(-1L);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for negative size");
        }
    }

    private static void testSetSizeAcceptsZero() {
        ZipEntry ze = new ZipEntry("test.txt");
        try {
            ze.setSize(0L);
        } catch (Exception e) {
            throw new AssertionError("Did not expect exception for size zero", e);
        }
    }

    private static void testSetSizeAcceptsPositive() {
        ZipEntry ze = new ZipEntry("test.txt");
        long size = 12345L;
        try {
            ze.setSize(size);
        } catch (Exception e) {
            throw new AssertionError("Did not expect exception for positive size", e);
        }
    }
}
