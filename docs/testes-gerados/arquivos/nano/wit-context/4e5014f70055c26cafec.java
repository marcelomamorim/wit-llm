/* @test    @summary Test for ZipEntry.setExtra(byte[] extra) method to verify behavior with valid and invalid inputs.    @run main ZipEntryWitupTest */

import java.util.zip.ZipEntry;
import static java.util.zip.ZipConstants64.*;

public class ZipEntryWitupTest {

    public static void main(String[] args) {
        testSetExtraWithValidData();
        testSetExtraWithNull();
        testSetExtraWithTooLargeData();
    }

    static void testSetExtraWithValidData() {
        ZipEntry entry = new ZipEntry("test.txt");
        byte[] validExtra = new byte[10];
        try {
            entry.setExtra(validExtra);
            System.out.println("testSetExtraWithValidData passed");
        } catch (Exception e) {
            System.err.println("testSetExtraWithValidData failed: " + e);
        }
    }

    static void testSetExtraWithNull() {
        ZipEntry entry = new ZipEntry("test.txt");
        try {
            entry.setExtra(null);
            System.out.println("testSetExtraWithNull passed");
        } catch (Exception e) {
            System.err.println("testSetExtraWithNull failed: " + e);
        }
    }

    static void testSetExtraWithTooLargeData() {
        ZipEntry entry = new ZipEntry("test.txt");
        byte[] tooLargeExtra = new byte[65536]; // 65536 > 0xFFFF
        try {
            entry.setExtra(tooLargeExtra);
            System.err.println("testSetExtraWithTooLargeData failed: no exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testSetExtraWithTooLargeData passed: caught expected exception");
        } catch (Exception e) {
            System.err.println("testSetExtraWithTooLargeData failed: unexpected exception: " + e);
        }
    }
}
