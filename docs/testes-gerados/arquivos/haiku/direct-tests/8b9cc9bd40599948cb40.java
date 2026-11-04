/*
 * @test
 * @summary Verifies ZipEntry.setCreationTime returns this and rejects null with NullPointerException
 * @run main ZipEntryWitupTest
 */

import java.nio.file.attribute.FileTime;
import java.util.zip.ZipEntry;

public class ZipEntryWitupTest {

    public static void main(String[] args) {
        ZipEntry e = new ZipEntry("a.txt");

        ZipEntry r1 = e.setCreationTime(FileTime.fromMillis(1000000L));
        check(r1 == e, "setCreationTime must return this");

        ZipEntry r2 = e.setCreationTime(FileTime.fromMillis(2000L));
        check(r2 == e, "setCreationTime must return this on repeated calls");

        try {
            e.setCreationTime(null);
            throw new RuntimeException("expected NullPointerException for null time");
        } catch (NullPointerException npe) {
            check("creationTime".equals(npe.getMessage()),
                  "NPE message must be 'creationTime' but was: " + npe.getMessage());
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
