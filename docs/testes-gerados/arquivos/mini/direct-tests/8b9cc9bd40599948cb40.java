/*
 * @test
 * @summary Test java.util.zip.ZipEntry.setCreationTime(FileTime) method
 * @run main ZipEntryWitupTest
 */

import java.util.zip.ZipEntry;
import java.nio.file.attribute.FileTime;
import java.util.Objects;

public class ZipEntryWitupTest {
    public static void main(String[] args) {
        testSetCreationTimeReturnsSameInstance();
        testSetCreationTimeRejectsNull();
    }

    private static void testSetCreationTimeReturnsSameInstance() {
        ZipEntry entry = new ZipEntry("test");
        FileTime now = FileTime.fromMillis(System.currentTimeMillis());
        ZipEntry returned = entry.setCreationTime(now);
        if (returned != entry) {
            throw new AssertionError("setCreationTime should return the same ZipEntry instance");
        }
    }

    private static void testSetCreationTimeRejectsNull() {
        ZipEntry entry = new ZipEntry("test");
        try {
            entry.setCreationTime(null);
            throw new AssertionError("setCreationTime should throw NullPointerException when passed null");
        } catch (NullPointerException npe) {
            if (!"creationTime".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message should be 'creationTime'");
            }
        }
    }
}
