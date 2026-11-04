/* @test    @summary Test setLastAccessTime method of ZipEntry class    @run main ZipEntryWitupTest */

import java.util.zip.ZipEntry;
import java.nio.file.attribute.FileTime;
import java.time.Instant;

public class ZipEntryWitupTest {

    public static void main(String[] args) {
        testSetLastAccessTimeWithValidTime();
        testSetLastAccessTimeWithNull();
    }

    static void testSetLastAccessTimeWithValidTime() {
        ZipEntry entry = new ZipEntry("test.txt");
        FileTime now = FileTime.from(Instant.now());
        ZipEntry result = entry.setLastAccessTime(now);
        assert result == entry : "setLastAccessTime should return the same instance";
        assert entry.atime.equals(now) : "atime should be set to the provided FileTime";
    }

    static void testSetLastAccessTimeWithNull() {
        try {
            ZipEntry entry = new ZipEntry("test.txt");
            entry.setLastAccessTime(null);
            assert false : "Expected NullPointerException when passing null";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
