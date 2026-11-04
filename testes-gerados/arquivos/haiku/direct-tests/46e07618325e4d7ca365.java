/*
 * @test
 * @summary ZipEntry.setMethod accepts STORED and DEFLATED and rejects other compression methods
 * @run main ZipEntryWitupTest
 */

import java.util.zip.ZipEntry;

public class ZipEntryWitupTest {

    public static void main(String[] args) {
        ZipEntry e = new ZipEntry("a.txt");

        e.setMethod(ZipEntry.DEFLATED);
        check(e.getMethod() == ZipEntry.DEFLATED, "DEFLATED not stored");

        e.setMethod(ZipEntry.STORED);
        check(e.getMethod() == ZipEntry.STORED, "STORED not stored");

        int[] invalid = {-1, 1, 2, 7, 9, 16, 255};
        for (int m : invalid) {
            ZipEntry z = new ZipEntry("b.txt");
            z.setMethod(ZipEntry.DEFLATED);
            expectIAE(z, m);
            check(z.getMethod() == ZipEntry.DEFLATED,
                  "method changed after invalid value " + m);
        }

        ZipEntry s = new ZipEntry("c.txt");
        s.setMethod(ZipEntry.STORED);
        for (int m : invalid) {
            expectIAE(s, m);
            check(s.getMethod() == ZipEntry.STORED,
                  "method changed after invalid value " + m);
        }
    }

    static void expectIAE(ZipEntry z, int m) {
        try {
            z.setMethod(m);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException not thrown for method " + m);
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
