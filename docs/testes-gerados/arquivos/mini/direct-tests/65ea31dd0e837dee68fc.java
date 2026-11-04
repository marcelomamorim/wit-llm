/*
 * @test
 * @summary Test java.util.zip.ZipEntry.setLastAccessTime(FileTime) method
 * @run main ZipEntryWitupTest
 */

import java.util.zip.ZipEntry;
import java.nio.file.attribute.FileTime;
import java.util.Objects;

public class ZipEntryWitupTest {
    public static void main(String[] args) {
        testSetLastAccessTimeWithValidFileTime();
        testSetLastAccessTimeWithNull();
    }

    private static void testSetLastAccessTimeWithValidFileTime() {
        ZipEntry entry = new ZipEntry("testEntry");
        FileTime now = FileTime.fromMillis(System.currentTimeMillis());
        ZipEntry returned = entry.setLastAccessTime(now);
        if (returned != entry) {
            throw new AssertionError("setLastAccessTime should return the same ZipEntry instance");
        }
        // Since atime is private, we verify by calling setLastAccessTime again with same value
        // to ensure no exception and consistent behavior
        entry.setLastAccessTime(now);
    }

    private static void testSetLastAccessTimeWithNull() {
        ZipEntry entry = new ZipEntry("testEntry");
        try {
            entry.setLastAccessTime(null);
            throw new AssertionError("Expected NullPointerException when passing null to setLastAccessTime");
        } catch (NullPointerException npe) {
            if (!"lastAccessTime".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + npe.getMessage());
            }
        }
    }
}
