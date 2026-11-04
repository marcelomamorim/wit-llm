/*
 * @test
 * @summary Witup-derived checks for ZipInputStream.skip exception paths and observable return values
 * @run main ZipInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.ZipInputStream;

public class ZipInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativeSkipThrowsIllegalArgumentException();
        testLargeNegativeSkipThrowsIllegalArgumentException();
        testNegativeSkipOnClosedStreamThrowsIllegalArgumentException();
        testSkipZeroOnOpenStreamReturnsZero();
        testSkipWithoutEntryReturnsZero();
        testSkipOnClosedStreamThrowsIOException();
        testSkipZeroOnClosedStreamThrowsIOException();
        System.out.println("PASSED");
    }

    private static ZipInputStream newEmptyZis() {
        return new ZipInputStream(new ByteArrayInputStream(new byte[0]));
    }

    private static void testNegativeSkipThrowsIllegalArgumentException() throws Exception {
        ZipInputStream zis = newEmptyZis();
        try {
            zis.skip(-1L);
            throw new RuntimeException("Expected IllegalArgumentException for skip(-1)");
        } catch (IllegalArgumentException expected) {
        } finally {
            zis.close();
        }
    }

    private static void testLargeNegativeSkipThrowsIllegalArgumentException() throws Exception {
        ZipInputStream zis = newEmptyZis();
        try {
            zis.skip(Long.MIN_VALUE);
            throw new RuntimeException("Expected IllegalArgumentException for skip(Long.MIN_VALUE)");
        } catch (IllegalArgumentException expected) {
        } finally {
            zis.close();
        }
    }

    private static void testNegativeSkipOnClosedStreamThrowsIllegalArgumentException() throws Exception {
        ZipInputStream zis = newEmptyZis();
        zis.close();
        try {
            zis.skip(-5L);
            throw new RuntimeException("Expected IllegalArgumentException for negative skip on closed stream");
        } catch (IllegalArgumentException expected) {
        }
    }

    private static void testSkipZeroOnOpenStreamReturnsZero() throws Exception {
        ZipInputStream zis = newEmptyZis();
        try {
            long result = zis.skip(0L);
            if (result != 0L) {
                throw new RuntimeException("Expected skip(0) to return 0 but was " + result);
            }
        } finally {
            zis.close();
        }
    }

    private static void testSkipWithoutEntryReturnsZero() throws Exception {
        ZipInputStream zis = newEmptyZis();
        try {
            long result = zis.skip(10L);
            if (result != 0L) {
                throw new RuntimeException("Expected skip(10) without entry to return 0 but was " + result);
            }
        } finally {
            zis.close();
        }
    }

    private static void testSkipOnClosedStreamThrowsIOException() throws Exception {
        ZipInputStream zis = newEmptyZis();
        zis.close();
        try {
            zis.skip(5L);
            throw new RuntimeException("Expected IOException for skip on closed stream");
        } catch (IOException expected) {
        }
    }

    private static void testSkipZeroOnClosedStreamThrowsIOException() throws Exception {
        ZipInputStream zis = newEmptyZis();
        zis.close();
        try {
            zis.skip(0L);
            throw new RuntimeException("Expected IOException for skip(0) on closed stream");
        } catch (IOException expected) {
        }
    }
}
