/*
 * @test
 * @summary Test sun.net.ftp.FtpDirEntry.toString() method behavior
 * @run main FtpDirEntryWitupTest
 */

import sun.net.ftp.FtpDirEntry;
import sun.net.ftp.FtpDirEntry.Type;
import java.util.Date;
import java.text.DateFormat;

public class FtpDirEntryWitupTest {
    public static void main(String[] args) throws Exception {
        testToStringWithNullLastModified();
        testToStringWithNonNullLastModified();
    }

    private static void testToStringWithNullLastModified() {
        FtpDirEntry entry = new FtpDirEntry("file1");
        entry.setUser("user1");
        entry.setGroup("group1");
        entry.setType(Type.FILE);
        entry.setSize(1234L);
        // lastModified is null by default

        String expected = "file1 [FILE] (user1 / group1) 1234";
        String actual = entry.toString();

        if (!expected.equals(actual)) {
            throw new AssertionError("toString() with null lastModified failed. Expected: " + expected + ", got: " + actual);
        }
    }

    private static void testToStringWithNonNullLastModified() {
        FtpDirEntry entry = new FtpDirEntry("file2");
        entry.setUser("user2");
        entry.setGroup("group2");
        entry.setType(Type.DIR);
        entry.setSize(5678L);

        Date now = new Date(0L); // Epoch time for deterministic output
        entry.setLastModified(now);

        String prefix = "file2 [DIR] (user2 / group2) {5678} ";
        String actual = entry.toString();

        if (!actual.startsWith(prefix)) {
            throw new AssertionError("toString() with non-null lastModified failed. Expected prefix: " + prefix + ", got: " + actual);
        }

        // Check that the date part matches the formatted date
        String formattedDate = DateFormat.getDateInstance().format(now);
        if (!actual.endsWith(formattedDate)) {
            throw new AssertionError("toString() date formatting mismatch. Expected suffix: " + formattedDate + ", got: " + actual);
        }
    }
}
