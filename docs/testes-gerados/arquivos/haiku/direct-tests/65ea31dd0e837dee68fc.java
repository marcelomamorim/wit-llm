/*
 * @test
 * @summary Verifies ZipEntry.setLastAccessTime returns this, stores the time, and rejects null
 * @run main ZipEntryWitupTest
 */

import java.nio.file.attribute.FileTime;
import java.util.zip.ZipEntry;

public class ZipEntryWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void main(String[] args) throws Exception {
        ZipEntry entry = new ZipEntry("a.txt");
        FileTime time = FileTime.fromMillis(1500000000000L);

        ZipEntry returned = entry.setLastAccessTime(time);
        check(returned == entry, "setLastAccessTime must return this");
        check(time.equals(entry.getLastAccessTime()), "last access time must be stored");

        FileTime other = FileTime.fromMillis(1600000000000L);
        entry.setLastAccessTime(other);
        check(other.equals(entry.getLastAccessTime()), "last access time must be replaced");

        try {
            entry.setLastAccessTime(null);
            throw new AssertionError("null time must throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check(other.equals(entry.getLastAccessTime()), "value must be unchanged after null rejected");
    }
}
