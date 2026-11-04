/*
 * @test
 * @summary Tests for the ZipEntry(ZipEntry) copy constructor
 * @run main ZipEntryWitupTest
 */

import java.util.Arrays;
import java.util.zip.ZipEntry;

public class ZipEntryWitupTest {

    public static void main(String[] args) throws Exception {
        testCopiesFields();
        testNullThrowsNPE();
        testCopyIndependentOfSource();
        testDefaultsCopied();
        testTimeCopied();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testCopiesFields() {
        ZipEntry e = new ZipEntry("a/b.txt");
        e.setSize(10);
        e.setCompressedSize(5);
        e.setCrc(123L);
        e.setMethod(ZipEntry.DEFLATED);
        e.setComment("hi");
        e.setExtra(new byte[] {1, 2, 3});

        ZipEntry c = new ZipEntry(e);
        check("a/b.txt".equals(c.getName()), "name not copied");
        check(c.getSize() == 10, "size not copied");
        check(c.getCompressedSize() == 5, "compressed size not copied");
        check(c.getCrc() == 123L, "crc not copied");
        check(c.getMethod() == ZipEntry.DEFLATED, "method not copied");
        check("hi".equals(c.getComment()), "comment not copied");
        check(Arrays.equals(new byte[] {1, 2, 3}, c.getExtra()), "extra not copied");
    }

    static void testNullThrowsNPE() {
        try {
            new ZipEntry((ZipEntry) null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testCopyIndependentOfSource() {
        ZipEntry e = new ZipEntry("x");
        e.setSize(1);
        ZipEntry c = new ZipEntry(e);
        c.setSize(99);
        c.setComment("c");
        check(e.getSize() == 1, "source size changed by copy mutation");
        check(e.getComment() == null, "source comment changed by copy mutation");
        check(c.getSize() == 99, "copy size not updated");
    }

    static void testDefaultsCopied() {
        ZipEntry e = new ZipEntry("n");
        ZipEntry c = new ZipEntry(e);
        check("n".equals(c.getName()), "name not copied for default entry");
        check(c.getSize() == -1, "default size not preserved");
        check(c.getCompressedSize() == -1, "default compressed size not preserved");
        check(c.getCrc() == -1, "default crc not preserved");
        check(c.getMethod() == -1, "default method not preserved");
        check(c.getComment() == null, "default comment not preserved");
        check(c.getExtra() == null, "default extra not preserved");
    }

    static void testTimeCopied() {
        ZipEntry e = new ZipEntry("t");
        e.setTime(1500000000000L);
        ZipEntry c = new ZipEntry(e);
        check(c.getTime() == e.getTime(), "time not copied");
    }
}
