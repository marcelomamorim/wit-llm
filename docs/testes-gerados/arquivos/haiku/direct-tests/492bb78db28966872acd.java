/*
 * @test
 * @summary Verifies ZipEntry.setLastModifiedTime returns this, stores the time observably, and rejects null
 * @run main ZipEntryWitupTest
 */

import java.nio.file.attribute.FileTime;
import java.util.zip.ZipEntry;

public class ZipEntryWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        ZipEntry e = new ZipEntry("a.txt");

        FileTime t1 = FileTime.fromMillis(1500000000000L);
        ZipEntry r = e.setLastModifiedTime(t1);
        check(r == e, "setLastModifiedTime must return this");
        check(t1.equals(e.getLastModifiedTime()), "getLastModifiedTime must return the set time");

        try {
            e.setLastModifiedTime(null);
            throw new RuntimeException("expected NullPointerException for null time");
        } catch (NullPointerException ex) {
            check("lastModifiedTime".equals(ex.getMessage()),
                  "unexpected NPE message: " + ex.getMessage());
        }
        check(t1.equals(e.getLastModifiedTime()),
              "time must be unchanged after rejected null");

        FileTime t2 = FileTime.fromMillis(1600000000000L);
        e.setLastModifiedTime(t2);
        check(t2.equals(e.getLastModifiedTime()), "second set must overwrite the first");

        ZipEntry c = (ZipEntry) e.clone();
        check(t2.equals(c.getLastModifiedTime()), "clone must preserve the last modified time");
    }
}
