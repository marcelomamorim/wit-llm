/*
 * @test
 * @summary Test java.util.zip.ZipEntry.setExtra(byte[])
 * @run main ZipEntryWitupTest
 */

import java.util.zip.ZipEntry;

public class ZipEntryWitupTest {
    public static void main(String[] args) {
        testSetExtraNull();
        testSetExtraEmpty();
        testSetExtraNonEmpty();
    }

    private static void testSetExtraNull() {
        ZipEntry entry = new ZipEntry("testEntryNull");
        entry.setExtra(null);
        // No exception expected, extra field set to null
        byte[] extra = entry.getExtra();
        if (extra != null) {
            throw new RuntimeException("Expected null extra field after setExtra(null)");
        }
    }

    private static void testSetExtraEmpty() {
        ZipEntry entry = new ZipEntry("testEntryEmpty");
        byte[] empty = new byte[0];
        entry.setExtra(empty);
        byte[] extra = entry.getExtra();
        if (extra == null) {
            throw new RuntimeException("Expected non-null extra field after setExtra(empty array)");
        }
        if (extra.length != 0) {
            throw new RuntimeException("Expected empty extra field array length after setExtra(empty array)");
        }
    }

    private static void testSetExtraNonEmpty() {
        ZipEntry entry = new ZipEntry("testEntryNonEmpty");
        byte[] data = new byte[] {1, 2, 3, 4, 5};
        entry.setExtra(data);
        byte[] extra = entry.getExtra();
        if (extra == null) {
            throw new RuntimeException("Expected non-null extra field after setExtra(non-empty array)");
        }
        if (extra.length != data.length) {
            throw new RuntimeException("Expected extra field length to match input array length");
        }
        for (int i = 0; i < data.length; i++) {
            if (extra[i] != data[i]) {
                throw new RuntimeException("Extra field content mismatch at index " + i);
            }
        }
    }
}
