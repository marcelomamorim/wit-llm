/*
 * @test
 * @summary Verifies DerOutputStream.putTag combines tag class, constructed form bit and tag value as observed in the written byte
 * @modules java.base/sun.security.util
 * @run main DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;

public class DerOutputStreamWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        DerOutputStream out1 = new DerOutputStream();
        out1.putTag((byte) 0x80, false, (byte) 0x01);
        check(out1.size() == 1, "putTag must write exactly one byte");
        check(out1.toByteArray()[0] == (byte) 0x81,
              "context-specific class primitive tag 1 must be 0x81");

        DerOutputStream out2 = new DerOutputStream();
        out2.putTag((byte) 0x80, true, (byte) 0x01);
        check(out2.size() == 1, "putTag must write exactly one byte (constructed)");
        check(out2.toByteArray()[0] == (byte) 0xA1,
              "constructed bit must set 0x20 yielding 0xA1");

        DerOutputStream out3 = new DerOutputStream(16);
        out3.putTag((byte) 0x40, false, (byte) 0x02);
        check(out3.toByteArray()[0] == (byte) 0x42,
              "application class tag 2 primitive must be 0x42");

        DerOutputStream out4 = new DerOutputStream();
        out4.putTag((byte) 0x00, true, (byte) 0x10);
        check(out4.toByteArray()[0] == (byte) 0x30,
              "universal SEQUENCE tag 0x10 constructed must be 0x30");

        DerOutputStream out5 = new DerOutputStream();
        out5.putTag((byte) 0x00, false, (byte) 0x00);
        check(out5.toByteArray()[0] == (byte) 0x00,
              "zero class, zero value, primitive must be 0x00");

        DerOutputStream out6 = new DerOutputStream();
        out6.putTag((byte) 0x00, true, (byte) 0x00);
        check(out6.toByteArray()[0] == (byte) 0x20,
              "constructed bit alone must produce 0x20");

        DerOutputStream out7 = new DerOutputStream();
        out7.putTag((byte) 0x80, false, (byte) 0x01);
        out7.putTag((byte) 0x80, true, (byte) 0x02);
        byte[] bytes = out7.toByteArray();
        check(bytes.length == 2, "two putTag calls must append two bytes");
        check(bytes[0] == (byte) 0x81 && bytes[1] == (byte) 0xA2,
              "successive putTag calls must append in order");
    }
}
