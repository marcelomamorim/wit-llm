/*
 * @test
 * @summary Verify ZipEntry(ZipEntry) rejects null and copies public observable state
 * @run main ZipEntryWitupTest
 */

import java.util.Arrays;
import java.util.zip.ZipEntry;

public class ZipEntryWitupTest {

    public static void main(String[] args) throws Exception {
        testNullEntryThrowsNPE();
        testCopyPreservesObservableState();
        testCopyIsIndependentOfSourceAfterCopy();
    }

    static void testNullEntryThrowsNPE() {
        boolean thrown = false;
        try {
            new ZipEntry((ZipEntry) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null entry");
        }
    }

    static void testCopyPreservesObservableState() {
        ZipEntry src = new ZipEntry("a.txt");
        src.setSize(10L);
        src.setCrc(123L);
        src.setMethod(ZipEntry.DEFLATED);
        src.setComment("c");
        src.setExtra(new byte[] {0x34, 0x12, 0x00, 0x00});

        ZipEntry copy = new ZipEntry(src);

        check("a.txt".equals(copy.getName()), "name not copied");
        check(copy.getSize() == 10L, "size not copied");
        check(copy.getCrc() == 123L, "crc not copied");
        check(copy.getMethod() == ZipEntry.DEFLATED, "method not copied");
        check("c".equals(copy.getComment()), "comment not copied");
        check(Arrays.equals(new byte[] {0x34, 0x12, 0x00, 0x00}, copy.getExtra()), "extra not copied");
    }

    static void testCopyIsIndependentOfSourceAfterCopy() {
        ZipEntry src = new ZipEntry("b.txt");
        src.setSize(20L);
        src.setCrc(456L);
        src.setMethod(ZipEntry.STORED);

        ZipEntry copy = new ZipEntry(src);

        src.setSize(99L);
        src.setCrc(789L);
        src.setMethod(ZipEntry.DEFLATED);

        check(copy.getSize() == 20L, "copy size changed with source");
        check(copy.getCrc() == 456L, "copy crc changed with source");
        check(copy.getMethod() == ZipEntry.STORED, "copy method changed with source");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
