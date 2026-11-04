/*
 * @test
 * @summary Verifies ZipEntry.setExtra(byte[]) accepts null and extra fields up to 0xFFFF bytes and rejects longer non-null arrays with IllegalArgumentException
 * @run main ZipEntryWitupTest
 */

import java.util.zip.ZipEntry;

public class ZipEntryWitupTest {

    public static void main(String[] args) {
        ZipEntry e = new ZipEntry("entry.txt");

        e.setExtra(null);
        e.setExtra(new byte[0]);
        e.setExtra(new byte[65535]);

        expectIllegalArgument(e, new byte[65536]);
        expectIllegalArgument(e, new byte[65537]);

        e.setExtra(new byte[10]);
        e.setExtra(null);
    }

    static void expectIllegalArgument(ZipEntry e, byte[] extra) {
        boolean thrown = false;
        try {
            e.setExtra(extra);
        } catch (IllegalArgumentException ex) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for extra length " + extra.length);
        }
    }
}
