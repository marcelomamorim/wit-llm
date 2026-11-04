/*
 * @test
 * @summary Verify ZipEntry.setCrc accepts the CRC-32 range 0..0xFFFFFFFF and rejects values outside it
 * @run main ZipEntryWitupTest
 */

import java.util.zip.ZipEntry;

public class ZipEntryWitupTest {

    public static void main(String[] args) {
        ZipEntry e = new ZipEntry("a.txt");

        e.setCrc(0L);
        check(e.getCrc() == 0L, "crc 0 should be accepted");

        e.setCrc(0xFFFFFFFFL);
        check(e.getCrc() == 0xFFFFFFFFL, "crc 0xFFFFFFFF should be accepted");

        e.setCrc(0x12345678L);
        check(e.getCrc() == 0x12345678L, "regular crc should be stored");

        expectIllegalArgument(e, -1L);
        expectIllegalArgument(e, 0x100000000L);
        expectIllegalArgument(e, Long.MIN_VALUE);
        expectIllegalArgument(e, Long.MAX_VALUE);

        check(e.getCrc() == 0x12345678L, "rejected crc must not change the stored value");
    }

    static void expectIllegalArgument(ZipEntry e, long crc) {
        try {
            e.setCrc(crc);
        } catch (IllegalArgumentException ex) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for crc " + crc);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
