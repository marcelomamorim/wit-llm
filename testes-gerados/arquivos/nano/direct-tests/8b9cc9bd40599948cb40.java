/*
 * @test
 * @summary Test setting creation time on ZipEntry
 * @run main ZipEntryWitupTest
 */

import java.util.zip.ZipEntry;
import java.nio.file.attribute.FileTime;

public class ZipEntryWitupTest {

    public static void main(String[] args) {
        testSetCreationTimeWithValidFileTime();
        testSetCreationTimeWithNull();
    }

    static void testSetCreationTimeWithValidFileTime() {
        ZipEntry entry = new ZipEntry("test.txt");
        FileTime ft = FileTime.from(1609459200000L, java.util.concurrent.TimeUnit.MILLISECONDS); // 2021-01-01T00:00:00Z
        ZipEntry result = entry.setCreationTime(ft);
        if (result != entry) {
            throw new AssertionError("setCreationTime should return the same ZipEntry instance");
        }
        if (entry.ctime != ft) {
            throw new AssertionError("Creation time was not set correctly");
        }
    }

    static void testSetCreationTimeWithNull() {
        try {
            ZipEntry entry = new ZipEntry("test.txt");
            entry.setCreationTime(null);
            throw new AssertionError("Expected NullPointerException when setting null FileTime");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
