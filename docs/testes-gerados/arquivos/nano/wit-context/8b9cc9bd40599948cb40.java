/*
 * @test
 * @summary Test for ZipEntry.setCreationTime(FileTime)
 * @run main ZipEntryWitupTest
 */

import java.util.zip.ZipEntry;
import java.nio.file.attribute.FileTime;

public class ZipEntryWitupTest {

    public static void main(String[] args) {
        testSetCreationTimeWithNull();
        testSetCreationTimeWithValid();
    }

    private static void testSetCreationTimeWithNull() {
        ZipEntry entry = new ZipEntry("test.txt");
        try {
            entry.setCreationTime(null);
            System.out.println("FAIL: Expected NullPointerException for null argument");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testSetCreationTimeWithValid() {
        ZipEntry entry = new ZipEntry("test.txt");
        FileTime ft = FileTime.fromMillis(System.currentTimeMillis());
        try {
            ZipEntry result = entry.setCreationTime(ft);
            if (result == entry) {
                System.out.println("PASS: setCreationTime returns the same instance");
            } else {
                System.out.println("FAIL: setCreationTime does not return the same instance");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
