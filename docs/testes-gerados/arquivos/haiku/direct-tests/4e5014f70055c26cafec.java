/*
 * @test
 * @summary Verifies observable round-trip behavior of ZipEntry.setExtra via getExtra
 * @run main ZipEntryWitupTest
 */

import java.util.Arrays;
import java.util.zip.ZipEntry;

public class ZipEntryWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSetExtraRoundTrip() {
        ZipEntry e = new ZipEntry("a.txt");
        byte[] data = new byte[] {1, 2, 3, 4, 5};
        e.setExtra(data);
        check(Arrays.equals(data, e.getExtra()), "getExtra should return the bytes set by setExtra");
    }

    static void testSetExtraNullClears() {
        ZipEntry e = new ZipEntry("b.txt");
        e.setExtra(new byte[] {9, 8, 7});
        e.setExtra(null);
        check(e.getExtra() == null, "getExtra should return null after setExtra(null)");
    }

    static void testSetExtraEmptyArray() {
        ZipEntry e = new ZipEntry("c.txt");
        e.setExtra(new byte[0]);
        byte[] got = e.getExtra();
        check(got != null, "getExtra should not return null after setting an empty array");
        check(got.length == 0, "getExtra should return an empty array after setExtra(new byte[0])");
    }

    static void testSetExtraOverwrite() {
        ZipEntry e = new ZipEntry("d.txt");
        e.setExtra(new byte[] {1, 1, 1});
        byte[] second = new byte[] {2, 2};
        e.setExtra(second);
        check(Arrays.equals(second, e.getExtra()), "the most recent setExtra value should be visible");
    }

    public static void main(String[] args) {
        testSetExtraRoundTrip();
        testSetExtraNullClears();
        testSetExtraEmptyArray();
        testSetExtraOverwrite();
    }
}
