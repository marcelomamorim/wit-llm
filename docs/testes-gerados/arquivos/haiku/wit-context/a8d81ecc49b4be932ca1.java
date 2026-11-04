/*
 * @test
 * @summary Witup generated checks for ZipEntry.setCrc argument validation
 * @run main ZipEntryWitupTest
 */

public class ZipEntryWitupTest {

    public static void main(String[] args) {
        testValidCrcValuesAreStored();
        testInvalidCrcThrowsAndKeepsPreviousValue();
    }

    static void testValidCrcValuesAreStored() {
        ZipEntry e = new ZipEntry("a.txt");
        long[] valid = {0L, 1L, 12345L, 0xFFFFFFFFL};
        for (long v : valid) {
            e.setCrc(v);
            check(e.getCrc() == v, "crc not stored: " + v);
        }
    }

    static void testInvalidCrcThrowsAndKeepsPreviousValue() {
        long[] invalid = {-1L, -2147483648L, 0x100000000L, Long.MAX_VALUE};
        for (long bad : invalid) {
            ZipEntry e = new ZipEntry("b.txt");
            e.setCrc(42L);
            boolean thrown = false;
            try {
                e.setCrc(bad);
            } catch (IllegalArgumentException ex) {
                thrown = true;
            }
            check(thrown, "expected IllegalArgumentException for crc: " + bad);
            check(e.getCrc() == 42L, "crc changed after rejected value: " + bad);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
