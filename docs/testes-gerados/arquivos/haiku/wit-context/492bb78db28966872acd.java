/*
 * @test
 * @summary Verifies ZipEntry.setLastModifiedTime rejects null and returns the same entry instance
 * @run main ZipEntryWitupTest
 */

import java.nio.file.attribute.FileTime;
import java.util.zip.ZipEntry;

public class ZipEntryWitupTest {

    public static void main(String[] args) {
        testNullTimeThrowsNullPointerException();
        testReturnsSameInstance();
    }

    static void testNullTimeThrowsNullPointerException() {
        ZipEntry entry = new ZipEntry("a.txt");
        try {
            entry.setLastModifiedTime(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null time");
    }

    static void testReturnsSameInstance() {
        ZipEntry entry = new ZipEntry("b.txt");
        ZipEntry result = entry.setLastModifiedTime(FileTime.fromMillis(1500000000000L));
        if (result != entry) {
            throw new RuntimeException("setLastModifiedTime should return this");
        }
    }
}
