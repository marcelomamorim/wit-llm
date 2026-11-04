/* @test    @summary Test the toString() method of FtpDirEntry for various states    @run main FtpDirEntryWitupTest */

import sun.net.ftp.FtpDirEntry;
import java.text.DateFormat;
import java.util.Date;

public class FtpDirEntryWitupTest {

    public static void main(String[] args) {
        testToStringWithoutLastModified();
        testToStringWithLastModified();
    }

    static void testToStringWithoutLastModified() {
        FtpDirEntry entry = new FtpDirEntry("testfile.txt");
        // Set type to FILE explicitly
        entry.setType(FtpDirEntry.Type.FILE);
        // Set user and group
        entry.setUser("user1");
        entry.setGroup("group1");
        // Set size
        entry.setSize(1024);
        // Ensure lastModified is null
        // Call toString and verify output contains expected substrings
        String result = entry.toString();
        assert result.contains("testfile.txt") : "Expected name in toString";
        assert result.contains("[FILE]") : "Expected type in toString";
        assert result.contains("user1") : "Expected user in toString";
        assert result.contains("group1") : "Expected group in toString";
        assert result.contains("1024") : "Expected size in toString";
        System.out.println("testToStringWithoutLastModified passed: " + result);
    }

    static void testToStringWithLastModified() {
        FtpDirEntry entry = new FtpDirEntry("anotherfile.log");
        entry.setType(FtpDirEntry.Type.FILE);
        entry.setUser("user2");
        entry.setGroup("group2");
        entry.setSize(2048);
        // Set lastModified to current date
        Date now = new Date();
        entry.setLastModified(now);
        String result = entry.toString();
        assert result.contains("anotherfile.log") : "Expected name in toString";
        assert result.contains("[FILE]") : "Expected type in toString";
        assert result.contains("user2") : "Expected user in toString";
        assert result.contains("group2") : "Expected group in toString";
        assert result.contains("2048") : "Expected size in toString";
        String formattedDate = DateFormat.getDateInstance().format(now);
        assert result.contains(formattedDate) : "Expected date in toString";
        System.out.println("testToStringWithLastModified passed: " + result);
    }
}
