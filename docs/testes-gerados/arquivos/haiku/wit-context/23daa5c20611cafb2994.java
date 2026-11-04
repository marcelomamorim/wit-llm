/*
 * @test
 * @summary Checks FtpDirEntry.toString() observable output when lastModified is not set
 * @modules java.base/sun.net.ftp
 * @run main FtpDirEntryWitupTest
 */

import sun.net.ftp.FtpDirEntry;

public class FtpDirEntryWitupTest {

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected [" + expected + "] but was [" + actual + "]");
        }
    }

    public static void main(String[] args) {
        FtpDirEntry plain = new FtpDirEntry("a.txt");
        check("a.txt [FILE] (null / null) -1", plain.toString());

        FtpDirEntry owned = new FtpDirEntry("b.txt");
        owned.setUser("alice");
        owned.setGroup("staff");
        check("b.txt [FILE] (alice / staff) -1", owned.toString());

        FtpDirEntry nullName = new FtpDirEntry(null);
        check("null [FILE] (null / null) -1", nullName.toString());
    }
}
