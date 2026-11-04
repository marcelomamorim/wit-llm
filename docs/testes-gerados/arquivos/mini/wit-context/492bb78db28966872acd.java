/*
 * @test
 * @summary Test ZipEntry.setLastModifiedTime(FileTime) for null and valid inputs
 * @run main ZipEntryWitupTest
 */

import java.util.zip.ZipEntry;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

public class ZipEntryWitupTest {
    public static void main(String[] args) {
        testSetLastModifiedTimeWithNull();
        testSetLastModifiedTimeWithValidFileTime();
    }

    private static void testSetLastModifiedTimeWithNull() {
        ZipEntry entry = new ZipEntry("test");
        try {
            entry.setLastModifiedTime(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testSetLastModifiedTimeWithValidFileTime() {
        ZipEntry entry = new ZipEntry("test");
        long millis = System.currentTimeMillis();
        FileTime ft = FileTime.from(millis, TimeUnit.MILLISECONDS);
        ZipEntry returned = entry.setLastModifiedTime(ft);
        if (returned != entry) {
            throw new RuntimeException("setLastModifiedTime should return this");
        }
        FileTime result = entry.getLastModifiedTime();
        if (!ft.equals(result)) {
            throw new RuntimeException("LastModifiedTime was not set correctly");
        }
    }
}
