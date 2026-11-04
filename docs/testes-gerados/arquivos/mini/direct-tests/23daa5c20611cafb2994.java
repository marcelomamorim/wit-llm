/*
 * @test
 * @summary Test sun.net.ftp.FtpDirEntry.toString() method behavior
 * @run main FtpDirEntryWitupTest
 */

import sun.net.ftp.FtpDirEntry;
import sun.net.ftp.FtpDirEntry.Type;
import java.text.DateFormat;
import java.util.Date;

public class FtpDirEntryWitupTest {
    public static void main(String[] args) throws Exception {
        testToStringWithNullLastModified();
        testToStringWithNonNullLastModified();
    }

    private static void testToStringWithNullLastModified() {
        FtpDirEntry entry = new FtpDirEntry("file1");
        entry.setUser("userA");
        entry.setGroup("groupA");
        entry.setType(Type.DIR);
        entry.setSize(12345L);

        // lastModified is null by default
        String result = entry.toString();

        // Expected format: name + " [" + type + "] (" + user + " / " + group + ") " + size
        String expected = "file1 [DIR] (userA / groupA) 12345";

        if (!result.equals(expected)) {
            throw new AssertionError("toString() with null lastModified returned: " + result + ", expected: " + expected);
        }
    }

    private static void testToStringWithNonNullLastModified() {
        FtpDirEntry entry = new FtpDirEntry("file2");
        entry.setUser("userB");
        entry.setGroup("groupB");
        entry.setType(Type.FILE);
        entry.setSize(67890L);

        Date now = new Date(1538352000000L); // 2018-10-01 GMT approx
        entry.setLastModified(now);

        String result = entry.toString();

        // Expected format: name + " [" + type + "] (" + user + " / " + group + ") {" + size + "} " + formattedDate
        String formattedDate = DateFormat.getDateInstance().format(now);
        String expected = "file2 [FILE] (userB / groupB) {67890} " + formattedDate;

        if (!result.equals(expected)) {
            throw new AssertionError("toString() with non-null lastModified returned: " + result + ", expected: " + expected);
        }
    }
}
