/* @test    @summary Test setting valid and invalid sizes in ZipEntry    @run main ZipEntryWitupTest */

import java.util.zip.ZipEntry;

public class ZipEntryWitupTest {

    public static void main(String[] args) {
        testSetSizeValid();
        testSetSizeInvalid();
    }

    static void testSetSizeValid() {
        ZipEntry entry = new ZipEntry("test.txt");
        long validSize = 1024L;
        entry.setSize(validSize);
        if (entry.getSize() != validSize) {
            throw new AssertionError("Expected size: " + validSize + ", but got: " + entry.getSize());
        }
    }

    static void testSetSizeInvalid() {
        ZipEntry entry = new ZipEntry("test.txt");
        try {
            entry.setSize(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative size");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
