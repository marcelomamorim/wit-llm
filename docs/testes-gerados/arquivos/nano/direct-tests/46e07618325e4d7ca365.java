/*
 * @test
 * @summary Test for ZipEntry.setMethod(int method)
 * @run main ZipEntryWitupTest
 */

import java.util.zip.ZipEntry;

public class ZipEntryWitupTest {

    public static void main(String[] args) {
        testSetMethodValid();
        testSetMethodInvalid();
    }

    static void testSetMethodValid() {
        ZipEntry entry = new ZipEntry("test.txt");
        // Test setting to STORED
        entry.setMethod(ZipEntry.STORED);
        assert entry.method == ZipEntry.STORED : "Method should be STORED";
        // Test setting to DEFLATED
        entry.setMethod(ZipEntry.DEFLATED);
        assert entry.method == ZipEntry.DEFLATED : "Method should be DEFLATED";
    }

    static void testSetMethodInvalid() {
        ZipEntry entry = new ZipEntry("test.txt");
        boolean exceptionThrown = false;
        try {
            entry.setMethod(1); // invalid method
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Setting invalid method should throw IllegalArgumentException";
    }
}
