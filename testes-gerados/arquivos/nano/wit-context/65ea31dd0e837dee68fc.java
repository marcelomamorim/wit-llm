/*
 * @test
 * @summary Test for ZipEntry.setLastAccessTime(FileTime)
 * @run main ZipEntryWitupTest
 */

import java.util.zip.ZipEntry;
import java.nio.file.attribute.FileTime;

public class ZipEntryWitupTest {

    public static void main(String[] args) {
        testSetLastAccessTimeWithValidFileTime();
        testSetLastAccessTimeWithNull();
    }

    static void testSetLastAccessTimeWithValidFileTime() {
        ZipEntry entry = new ZipEntry("test.txt");
        FileTime ft = FileTime.from(0, java.util.concurrent.TimeUnit.MILLISECONDS);
        ZipEntry result = entry.setLastAccessTime(ft);
        assert result == entry : "Expected the same ZipEntry instance";
        assert entry.atime.equals(ft) : "atime should be set to the provided FileTime";
    }

    static void testSetLastAccessTimeWithNull() {
        ZipEntry entry = new ZipEntry("test.txt");
        try {
            entry.setLastAccessTime(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            assert e.getMessage().equals("lastAccessTime") : "Exception message should be 'lastAccessTime'";
        }
    }
}
