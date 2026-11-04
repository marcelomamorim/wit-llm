/*
 * @test
 * @summary Test sun.security.util.DerOutputStream.putTag(byte, boolean, byte) for basic correctness and overflow resilience
 * @run main DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;

public class DerOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        DerOutputStream out = new DerOutputStream();

        // Test 1: putTag with form = false, simple tagClass and val
        out.reset();
        byte tagClass = 0x01; // arbitrary tag class
        boolean form = false;
        byte val = 0x02; // arbitrary val
        out.putTag(tagClass, form, val);
        byte[] result = out.toByteArray();
        if (result.length != 1 || result[0] != (byte)(tagClass | val)) {
            throw new RuntimeException("Test 1 failed: unexpected output");
        }

        // Test 2: putTag with form = true, check bit 0x20 is set
        out.reset();
        form = true;
        out.putTag(tagClass, form, val);
        result = out.toByteArray();
        byte expected = (byte)(tagClass | val | 0x20);
        if (result.length != 1 || result[0] != expected) {
            throw new RuntimeException("Test 2 failed: form bit not set correctly");
        }

        // Test 3: putTag with zero values
        out.reset();
        out.putTag((byte)0, false, (byte)0);
        result = out.toByteArray();
        if (result.length != 1 || result[0] != 0) {
            throw new RuntimeException("Test 3 failed: zero values");
        }

        // Test 4: putTag with max byte values
        out.reset();
        out.putTag((byte)0x7F, true, (byte)0x7F);
        result = out.toByteArray();
        expected = (byte)(0x7F | 0x7F | 0x20);
        if (result.length != 1 || result[0] != expected) {
            throw new RuntimeException("Test 4 failed: max byte values");
        }

        // Test 5: putTag multiple calls accumulate bytes
        out.reset();
        out.putTag((byte)0x01, false, (byte)0x01);
        out.putTag((byte)0x02, true, (byte)0x02);
        result = out.toByteArray();
        if (result.length != 2) {
            throw new RuntimeException("Test 5 failed: expected 2 bytes output");
        }
        if (result[0] != (byte)(0x01 | 0x01)) {
            throw new RuntimeException("Test 5 failed: first byte incorrect");
        }
        if (result[1] != (byte)(0x02 | 0x02 | 0x20)) {
            throw new RuntimeException("Test 5 failed: second byte incorrect");
        }

        // Note: The WIT expaths suggest OutOfMemoryError on large internal buffer growth,
        // but such conditions are impractical to test deterministically here and are
        // related to ByteArrayOutputStream internals. We do not test those here.

        System.out.println("All tests passed.");
    }
}
