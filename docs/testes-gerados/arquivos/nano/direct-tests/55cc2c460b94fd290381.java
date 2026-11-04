/* @test    @summary Test ZipEntry copy constructor for correctness and null handling    @run main ZipEntryWitupTest */

import java.util.zip.ZipEntry;
import static java.util.zip.ZipEntry.*;

public class ZipEntryWitupTest {

    public static void main(String[] args) {
        testCopyConstructorWithValidEntry();
        testCopyConstructorWithNull();
    }

    static void testCopyConstructorWithValidEntry() {
        ZipEntry original = new ZipEntry("testEntry");
        original.crc = 12345;
        original.size = 100;
        original.method = DEFLATED;
        original.comment = "Sample comment";
        original.extra = new byte[] {1, 2, 3};
        original.xdostime = 0x4A00;
        original.mtime = null;
        original.atime = null;
        original.ctime = null;
        original.flag = 1;

        ZipEntry copy = new ZipEntry(original);

        assert copy.name.equals("testEntry") : "Name mismatch";
        assert copy.crc == 12345 : "CRC mismatch";
        assert copy.size == 100 : "Size mismatch";
        assert copy.method == DEFLATED : "Method mismatch";
        assert copy.comment.equals("Sample comment") : "Comment mismatch";
        assert copy.extra.length == 3 : "Extra length mismatch";
        assert copy.xdostime == 0x4A00 : "XDOSTime mismatch";
        assert copy.flag == 1 : "Flag mismatch";
        System.out.println("testCopyConstructorWithValidEntry passed.");
    }

    static void testCopyConstructorWithNull() {
        try {
            new ZipEntry(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            assert e.getMessage().equals("entry") : "Unexpected message: " + e.getMessage();
            System.out.println("testCopyConstructorWithNull passed.");
        }
    }
}
