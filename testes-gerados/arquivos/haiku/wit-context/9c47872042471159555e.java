/*
 * @test
 * @summary Verifies putTag encodes tag class, constructed flag and tag number as a single byte
 * @modules java.base/sun.security.util
 * @run main DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;

public class DerOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testPrimitiveTag();
        testConstructedTag();
        testConstructedFlagOnZeroClass();
        testSingleByteWritten();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testPrimitiveTag() {
        DerOutputStream out = new DerOutputStream();
        out.putTag((byte) 0x80, false, (byte) 0x01);
        byte[] b = out.toByteArray();
        check(b.length == 1, "expected 1 byte, got " + b.length);
        check(b[0] == (byte) 0x81, "expected 0x81, got " + (b[0] & 0xFF));
    }

    static void testConstructedTag() {
        DerOutputStream out = new DerOutputStream();
        out.putTag((byte) 0x80, true, (byte) 0x01);
        byte[] b = out.toByteArray();
        check(b.length == 1, "expected 1 byte, got " + b.length);
        check(b[0] == (byte) 0xA1, "expected 0xA1, got " + (b[0] & 0xFF));
    }

    static void testConstructedFlagOnZeroClass() {
        DerOutputStream out = new DerOutputStream(16);
        out.putTag((byte) 0x00, true, (byte) 0x10);
        byte[] b = out.toByteArray();
        check(b.length == 1, "expected 1 byte, got " + b.length);
        check(b[0] == (byte) 0x30, "expected 0x30, got " + (b[0] & 0xFF));
    }

    static void testSingleByteWritten() {
        DerOutputStream out = new DerOutputStream();
        out.putTag((byte) 0x40, false, (byte) 0x02);
        check(out.size() == 1, "expected size 1, got " + out.size());
        out.putTag((byte) 0x40, false, (byte) 0x02);
        check(out.size() == 2, "expected size 2, got " + out.size());
        byte[] b = out.toByteArray();
        check(b[0] == (byte) 0x42 && b[1] == (byte) 0x42, "unexpected bytes");
    }
}
