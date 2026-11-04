/*
 * @test
 * @summary Test for sun.net.ftp.FtpDirEntry.toString()
 * @run main FtpDirEntryWitupTest
 */

import sun.net.ftp.FtpDirEntry;
import java.util.Date;
import java.text.DateFormat;

public class FtpDirEntryWitupTest {

    public static void main(String[] args) {
        testToStringWithNullLastModified();
        testToStringWithValidLastModified();
    }

    private static void testToStringWithNullLastModified() {
        FtpDirEntry entry = new FtpDirEntry("file.txt");
        // Set fields to known values
        entry.setUser("user1");
        entry.setGroup("group1");
        // size default is -1
        // lastModified is null by default
        String result = entry.toString();
        String expected = "file.txt [FILE] (user1 / group1) -1";
        if (!result.equals(expected)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + result);
        }
    }

    private static void testToStringWithValidLastModified() {
        FtpDirEntry entry = new FtpDirEntry("file2.txt");
        entry.setUser("user2");
        entry.setGroup("group2");
        // Set size
        entry.setSize(1024);
        // Set lastModified to a specific date
        Date date = new Date(1638316800000L); // 2021-12-01T00:00:00Z
        entry.setLastModified(date);
        String result = entry.toString();
        String formattedDate = DateFormat.getDateInstance().format(date);
        String expected = "file2.txt [FILE] (user2 / group2) {1024} " + formattedDate;
        if (!result.equals(expected)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + result);
        }
    }
}
