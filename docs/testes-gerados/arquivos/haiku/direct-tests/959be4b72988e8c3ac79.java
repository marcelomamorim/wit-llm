/*
 * @test
 * @summary Behavioral tests for CRC32.update(byte[], int, int) argument validation and checksum results
 * @run main CRC32WitupTest
 */

import java.util.zip.CRC32;

public class CRC32WitupTest {

    interface Action {
        void run() throws Exception;
    }

    static void expect(Class<? extends Throwable> expected, Action action) {
        try {
            action.run();
        } catch (Throwable t) {
            if (expected.isInstance(t)) {
                return;
            }
            throw new RuntimeException("Unexpected exception: " + t, t);
        }
        throw new RuntimeException("Expected " + expected.getName());
    }

    public static void main(String[] args) {
        byte[] data = {0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39};

        CRC32 full = new CRC32();
        full.update(data, 0, data.length);
        if (full.getValue() != 0xCBF43926L) {
            throw new RuntimeException("Wrong CRC for 123456789: " + Long.toHexString(full.getValue()));
        }

        CRC32 sub = new CRC32();
        sub.update(data, 2, 3);
        CRC32 bytewise = new CRC32();
        bytewise.update(0x33);
        bytewise.update(0x34);
        bytewise.update(0x35);
        if (sub.getValue() != bytewise.getValue()) {
            throw new RuntimeException("Offset/length update mismatch");
        }

        CRC32 empty = new CRC32();
        empty.update(data, data.length, 0);
        if (empty.getValue() != 0L) {
            throw new RuntimeException("Empty update at end should not change CRC");
        }

        CRC32 state = new CRC32();
        state.update(data, 0, data.length);
        long before = state.getValue();
        expect(NullPointerException.class, () -> state.update(null, 0, 0));
        expect(ArrayIndexOutOfBoundsException.class, () -> state.update(data, -1, 1));
        expect(ArrayIndexOutOfBoundsException.class, () -> state.update(data, 0, -1));
        expect(ArrayIndexOutOfBoundsException.class, () -> state.update(data, 5, 5));
        expect(ArrayIndexOutOfBoundsException.class, () -> state.update(data, Integer.MAX_VALUE, 1));
        if (state.getValue() != before) {
            throw new RuntimeException("Failed update must not modify CRC");
        }

        CRC32 reset = new CRC32();
        reset.update(data, 0, data.length);
        reset.reset();
        reset.update(data, 0, 0);
        if (reset.getValue() != 0L) {
            throw new RuntimeException("Reset then empty update should yield 0");
        }
    }
}
