/*
 * @test
 * @summary Verifies ZipEntry.setMethod accepts STORED and DEFLATED and rejects other compression methods without changing the entry state
 * @run main ZipEntryWitupTest
 */

import java.util.zip.ZipEntry;

public class ZipEntryWitupTest {

    public static void main(String[] args) {
        ZipEntry stored = new ZipEntry("stored.txt");
        stored.setMethod(ZipEntry.STORED);
        check(stored.getMethod() == ZipEntry.STORED, "STORED should be accepted");

        ZipEntry deflated = new ZipEntry("deflated.txt");
        deflated.setMethod(ZipEntry.DEFLATED);
        check(deflated.getMethod() == ZipEntry.DEFLATED, "DEFLATED should be accepted");

        int[] invalid = {1, 2, 7, 9, 16, 255, -1};
        for (int m : invalid) {
            ZipEntry e = new ZipEntry("invalid.txt");
            boolean thrown = false;
            try {
                e.setMethod(m);
            } catch (IllegalArgumentException ex) {
                thrown = true;
            }
            check(thrown, "method " + m + " should be rejected with IllegalArgumentException");
            check(e.getMethod() == -1, "rejected method " + m + " must not modify the entry");
        }

        ZipEntry keep = new ZipEntry("keep.txt");
        keep.setMethod(ZipEntry.DEFLATED);
        boolean thrown = false;
        try {
            keep.setMethod(3);
        } catch (IllegalArgumentException ex) {
            thrown = true;
        }
        check(thrown, "method 3 should be rejected after a valid method was set");
        check(keep.getMethod() == ZipEntry.DEFLATED, "previous valid method must be preserved after rejection");

        keep.setMethod(ZipEntry.STORED);
        check(keep.getMethod() == ZipEntry.STORED, "STORED should replace DEFLATED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
