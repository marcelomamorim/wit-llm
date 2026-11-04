/*
 * @test
 * @summary ZipEntry.setLastAccessTime returns this for a non-null time and throws NullPointerException for null
 * @run main ZipEntryWitupTest
 */

import java.nio.file.attribute.FileTime;
import java.util.zip.ZipEntry;

public class ZipEntryWitupTest {

    public static void main(String[] args) throws Exception {
        ZipEntry entry = new ZipEntry("a.txt");
        FileTime time = FileTime.fromMillis(1000L);

        ZipEntry returned = entry.setLastAccessTime(time);
        if (returned != entry) {
            throw new AssertionError("setLastAccessTime must return the same entry instance");
        }

        boolean npeThrown = false;
        try {
            entry.setLastAccessTime(null);
        } catch (NullPointerException ex) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("setLastAccessTime(null) must throw NullPointerException");
        }

        ZipEntry chained = new ZipEntry("b.txt").setLastAccessTime(time);
        if (chained == null || !"b.txt".equals(chained.getName())) {
            throw new AssertionError("chained call must return the configured entry");
        }
    }
}
