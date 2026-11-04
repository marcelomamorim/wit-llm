/*
 * @test
 * @summary Verify FtpDirEntry.toString() output when no last modified date is set
 * @modules java.base/sun.net.ftp
 * @run main FtpDirEntryWitupTest
 */

import sun.net.ftp.FtpDirEntry;

public class FtpDirEntryWitupTest {

    public static void main(String[] args) {
        FtpDirEntry e = new FtpDirEntry("file.txt");
        check(e.toString(), "file.txt [FILE] (null / null) -1");

        FtpDirEntry f = new FtpDirEntry("a").setUser("alice");
        check(f.toString(), "a [FILE] (alice / null) -1");
    }

    static void check(String actual, String expected) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected: " + expected + " but was: " + actual);
        }
    }
}
