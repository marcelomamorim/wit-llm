/*
 * @test
 * @summary Test java.util.zip.ZipEntry.setLastModifiedTime(FileTime) method
 * @run main ZipEntryWitupTest
 */

import java.util.zip.ZipEntry;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;
import java.util.Objects;

public class ZipEntryWitupTest {
    public static void main(String[] args) throws Exception {
        testSetLastModifiedTimeNonNull();
        testSetLastModifiedTimeReturnsThis();
        testSetLastModifiedTimeRejectsNull();
    }

    private static void testSetLastModifiedTimeNonNull() {
        ZipEntry entry = new ZipEntry("test");
        long nowMillis = System.currentTimeMillis();
        FileTime ft = FileTime.from(nowMillis, TimeUnit.MILLISECONDS);
        entry.setLastModifiedTime(ft);
        FileTime result = entry.mtime;
        if (!Objects.equals(result, ft)) {
            throw new AssertionError("mtime field not set correctly");
        }
    }

    private static void testSetLastModifiedTimeReturnsThis() {
        ZipEntry entry = new ZipEntry("test");
        FileTime ft = FileTime.from(0, TimeUnit.MILLISECONDS);
        ZipEntry returned = entry.setLastModifiedTime(ft);
        if (returned != entry) {
            throw new AssertionError("setLastModifiedTime should return this");
        }
    }

    private static void testSetLastModifiedTimeRejectsNull() {
        ZipEntry entry = new ZipEntry("test");
        try {
            entry.setLastModifiedTime(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException npe) {
            if (!"lastModifiedTime".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch");
            }
        }
    }
}
