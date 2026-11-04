/*
 * @test
 * @summary Test for ZipEntry.setLastModifiedTime(FileTime)
 * @run main ZipEntryWitupTest
 */

import java.util.zip.ZipEntry;
import java.nio.file.attribute.FileTime;
import java.time.Instant;

public class ZipEntryWitupTest {

    public static void main(String[] args) {
        testSetLastModifiedTimeWithValidFileTime();
        testSetLastModifiedTimeWithNull();
    }

    static void testSetLastModifiedTimeWithValidFileTime() {
        ZipEntry entry = new ZipEntry("test.txt");
        FileTime ft = FileTime.from(Instant.now());
        try {
            ZipEntry result = entry.setLastModifiedTime(ft);
            if (result != null && result == entry) {
                System.out.println("PASS: setLastModifiedTime with valid FileTime");
            } else {
                System.out.println("FAIL: setLastModifiedTime with valid FileTime");
            }
        } catch (Exception e) {
            System.out.println("FAIL: setLastModifiedTime with valid FileTime - Exception: " + e);
        }
    }

    static void testSetLastModifiedTimeWithNull() {
        ZipEntry entry = new ZipEntry("test.txt");
        try {
            entry.setLastModifiedTime(null);
            System.out.println("FAIL: setLastModifiedTime with null did not throw");
        } catch (NullPointerException e) {
            System.out.println("PASS: setLastModifiedTime with null threw NullPointerException");
        } catch (Exception e) {
            System.out.println("FAIL: setLastModifiedTime with null - Unexpected exception: " + e);
        }
    }
}
