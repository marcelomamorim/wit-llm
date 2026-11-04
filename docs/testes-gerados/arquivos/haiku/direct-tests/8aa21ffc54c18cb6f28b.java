/*
 * @test
 * @summary Verifies UUID.node() returns the low 48 bits for version 1 UUIDs and throws UnsupportedOperationException otherwise
 * @run main UUIDWitupTest
 */

import java.util.UUID;

public class UUIDWitupTest {

    private static int failures = 0;

    private static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    private static void checkNodeThrowsUnsupported(UUID uuid, String label) {
        try {
            long value = uuid.node();
            failures++;
            System.err.println("FAILED: " + label + " expected UnsupportedOperationException but got " + value);
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    public static void main(String[] args) {
        UUID v1 = new UUID(0x0000000000001000L, 0xC123456789ABCDEFL);
        check(v1.version() == 1, "precondition: version of v1 must be 1");
        check(v1.node() == 0x456789ABCDEFL, "node of version 1 UUID must mask high 16 bits, got " + Long.toHexString(v1.node()));

        UUID v1AllOnes = new UUID(0x123456789ABC1DEFL, 0xFFFFFFFFFFFFFFFFL);
        check(v1AllOnes.version() == 1, "precondition: version of v1AllOnes must be 1");
        check(v1AllOnes.node() == 0x0000FFFFFFFFFFFFL, "node must be 0x0000FFFFFFFFFFFF, got " + Long.toHexString(v1AllOnes.node()));

        UUID v1Zero = new UUID(0x0000000000001000L, 0x0000000000000000L);
        check(v1Zero.node() == 0L, "node of zero low bits must be 0");

        UUID v4 = new UUID(0x0000000000004000L, 0xC123456789ABCDEFL);
        check(v4.version() == 4, "precondition: version of v4 must be 4");
        checkNodeThrowsUnsupported(v4, "version 4");

        UUID v3 = new UUID(0x0000000000003000L, 0x0L);
        check(v3.version() == 3, "precondition: version of v3 must be 3");
        checkNodeThrowsUnsupported(v3, "version 3");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
