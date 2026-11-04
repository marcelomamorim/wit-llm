/*
 * @test
 * @summary Witup-generated checks for CRC32C.update(byte[], int, int) exception paths and basic output
 * @run main CRC32CWitupTest
 */
import java.util.zip.CRC32C;

public class CRC32CWitupTest {

    public static void main(String[] args) {
        testNullArrayThrowsNPE();
        testNegativeOffsetThrowsAIOOBE();
        testNegativeLengthThrowsAIOOBE();
        testOffsetPlusLengthOutOfRangeThrowsAIOOBE();
        testEmptyUpdateAtEndLeavesValueUnchanged();
        testCheckValueForDigits();
    }

    static void expectThrows(Class<? extends Throwable> expected, Runnable action, String name) {
        try {
            action.run();
        } catch (Throwable t) {
            if (expected.isInstance(t)) {
                return;
            }
            throw new RuntimeException(name + ": unexpected exception " + t, t);
        }
        throw new RuntimeException(name + ": expected " + expected.getName());
    }

    static void testNullArrayThrowsNPE() {
        final CRC32C c = new CRC32C();
        expectThrows(NullPointerException.class, () -> c.update(null, 0, 0), "nullArray");
    }

    static void testNegativeOffsetThrowsAIOOBE() {
        final CRC32C c = new CRC32C();
        final byte[] b = new byte[8];
        expectThrows(ArrayIndexOutOfBoundsException.class, () -> c.update(b, -1, 2), "negativeOffset");
    }

    static void testNegativeLengthThrowsAIOOBE() {
        final CRC32C c = new CRC32C();
        final byte[] b = new byte[8];
        expectThrows(ArrayIndexOutOfBoundsException.class, () -> c.update(b, 0, -1), "negativeLength");
    }

    static void testOffsetPlusLengthOutOfRangeThrowsAIOOBE() {
        final CRC32C c = new CRC32C();
        final byte[] b = new byte[4];
        expectThrows(ArrayIndexOutOfBoundsException.class, () -> c.update(b, 2, 3), "offsetPlusLength");
    }

    static void testEmptyUpdateAtEndLeavesValueUnchanged() {
        final byte[] b = new byte[4];
        final CRC32C c = new CRC32C();
        final long before = c.getValue();
        c.update(b, b.length, 0);
        if (c.getValue() != before) {
            throw new RuntimeException("empty update changed checksum");
        }
    }

    static void testCheckValueForDigits() {
        final byte[] b = "123456789".getBytes(java.nio.charset.StandardCharsets.US_ASCII);
        final CRC32C c = new CRC32C();
        c.update(b, 0, b.length);
        if (c.getValue() != 0xE3069283L) {
            throw new RuntimeException("unexpected CRC-32C: " + Long.toHexString(c.getValue()));
        }
    }
}
