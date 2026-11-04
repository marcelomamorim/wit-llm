/*
 * @test
 * @summary Test ZipEntry.setCreationTime(FileTime) for null and valid inputs
 * @run main ZipEntryWitupTest
 */

import java.util.zip.ZipEntry;
import java.nio.file.attribute.FileTime;
import java.util.Objects;

public class ZipEntryWitupTest {
    public static void main(String[] args) {
        testSetCreationTimeWithValidFileTime();
        testSetCreationTimeWithNull();
        System.out.println("All tests passed.");
    }

    private static void testSetCreationTimeWithValidFileTime() {
        ZipEntry entry = new ZipEntry("testEntry");
        FileTime now = FileTime.fromMillis(System.currentTimeMillis());
        ZipEntry returned = entry.setCreationTime(now);
        if (returned != entry) {
            throw new AssertionError("setCreationTime should return this instance");
        }
    }

    private static void testSetCreationTimeWithNull() {
        ZipEntry entry = new ZipEntry("testEntry");
        try {
            entry.setCreationTime(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException e) {
            // Expected exception
            if (!"creationTime".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch");
            }
        }
    }
}
