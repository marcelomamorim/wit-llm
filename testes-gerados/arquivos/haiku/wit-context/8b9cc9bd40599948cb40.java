/*
 * @test
 * @summary Verify ZipEntry.setCreationTime rejects null and returns the same entry
 * @run main ZipEntryWitupTest
 */
import java.nio.file.attribute.FileTime;
import java.util.zip.ZipEntry;

public class ZipEntryWitupTest {
    public static void main(String[] args) {
        ZipEntry entry = new ZipEntry("a.txt");
        boolean thrown = false;
        try {
            entry.setCreationTime(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected NullPointerException for null creation time");
        }
        FileTime time = FileTime.fromMillis(1500000000000L);
        ZipEntry result = entry.setCreationTime(time);
        if (result != entry) {
            throw new AssertionError("setCreationTime should return this entry");
        }
        if (!time.equals(entry.getCreationTime())) {
            throw new AssertionError("creation time was not stored as observed by getCreationTime");
        }
    }
}
