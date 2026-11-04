/*
 * @test
 * @summary Test sun.security.util.DerOutputStream.putTag method behavior
 * @run main sun.security.util.DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;

public class DerOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testPutTagBasic();
        testPutTagWithFormTrue();
        testPutTagWithZeroValues();
    }

    private static void testPutTagBasic() throws Exception {
        DerOutputStream dos = new DerOutputStream();
        byte tagClass = 0x40; // Context-specific class
        boolean form = false;
        byte val = 0x01; // tag number 1

        dos.putTag(tagClass, form, val);
        byte[] result = dos.toByteArray();

        // Expected: tagClass | val = 0x40 | 0x01 = 0x41
        if (result.length != 1 || result[0] != 0x41) {
            throw new RuntimeException("testPutTagBasic failed: expected 0x41, got " + (result.length > 0 ? String.format("0x%02X", result[0]) : "empty"));
        }
    }

    private static void testPutTagWithFormTrue() throws Exception {
        DerOutputStream dos = new DerOutputStream();
        byte tagClass = 0x00; // Universal class
        boolean form = true; // Constructed form
        byte val = 0x10; // tag number 16

        dos.putTag(tagClass, form, val);
        byte[] result = dos.toByteArray();

        // Expected: tag = tagClass | val = 0x00 | 0x10 = 0x10
        // if form true, tag |= 0x20 => 0x10 | 0x20 = 0x30
        if (result.length != 1 || result[0] != 0x30) {
            throw new RuntimeException("testPutTagWithFormTrue failed: expected 0x30, got " + (result.length > 0 ? String.format("0x%02X", result[0]) : "empty"));
        }
    }

    private static void testPutTagWithZeroValues() throws Exception {
        DerOutputStream dos = new DerOutputStream();
        byte tagClass = 0x00;
        boolean form = false;
        byte val = 0x00;

        dos.putTag(tagClass, form, val);
        byte[] result = dos.toByteArray();

        // Expected: 0x00
        if (result.length != 1 || result[0] != 0x00) {
            throw new RuntimeException("testPutTagWithZeroValues failed: expected 0x00, got " + (result.length > 0 ? String.format("0x%02X", result[0]) : "empty"));
        }
    }
}
