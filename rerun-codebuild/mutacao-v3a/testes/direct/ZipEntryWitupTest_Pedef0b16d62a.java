/*
 * @test
 * @summary Test for ZipEntry.setSize(long) method
 * @run main ZipEntryWitupTest_Pedef0b16d62a
 */

import java.util.zip.ZipEntry;

public class ZipEntryWitupTest_Pedef0b16d62a {
    public static void main(String[] args) {
        testSetSizeValid();
        testSetSizeNegative();
    }

    public static void testSetSizeValid() {
        ZipEntry entry = new ZipEntry("test.txt");
        long validSize = 1024;
        entry.setSize(validSize);
        assert entry.getSize() == validSize : "Size should be set to valid value";
    }

    public static void testSetSizeNegative() {
        ZipEntry entry = new ZipEntry("test.txt");
        try {
            entry.setSize(-1);
            assert false : "Expected IllegalArgumentException for negative size";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("invalid entry size") : "Exception message mismatch";
        }
    }
}
