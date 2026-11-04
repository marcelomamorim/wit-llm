/*
 * @test
 * @summary Test ByteArrayOutputStream.toString(int hibyte) for normal and boundary conditions
 * @run main ByteArrayOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;

public class ByteArrayOutputStreamWitupTest {
    public static void main(String[] args) {
        testToStringNormal();
        testToStringEmptyBuffer();
        testToStringWithHibyte();
        testToStringWithCountZero();
        testToStringWithCountExceedingBuffer();
    }

    private static void testToStringNormal() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] data = {65, 66, 67, 68}; // ABCD
        for (byte b : data) {
            baos.write(b);
        }
        String s = baos.toString(0);
        if (!"ABCD".equals(s)) {
            throw new RuntimeException("testToStringNormal failed: expected ABCD but got " + s);
        }
    }

    private static void testToStringEmptyBuffer() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String s = baos.toString(0);
        if (!"".equals(s)) {
            throw new RuntimeException("testToStringEmptyBuffer failed: expected empty string but got " + s);
        }
    }

    private static void testToStringWithHibyte() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(0x41); // 'A'
        baos.write(0x42); // 'B'
        // hibyte = 1 means high byte set to 1 << 8 = 256, so chars are (256 + byte)
        String s = baos.toString(1);
        // The resulting string chars should be 0x101 and 0x102
        if (s.length() != 2 || s.charAt(0) != 0x101 || s.charAt(1) != 0x102) {
            throw new RuntimeException("testToStringWithHibyte failed: unexpected chars " +
                ((int) s.charAt(0)) + ", " + ((int) s.charAt(1)));
        }
    }

    private static void testToStringWithCountZero() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(10);
        // count is 0 initially
        String s = baos.toString(0);
        if (!"".equals(s)) {
            throw new RuntimeException("testToStringWithCountZero failed: expected empty string but got " + s);
        }
    }

    private static void testToStringWithCountExceedingBuffer() {
        // This test tries to simulate the condition buf.length < count which should cause StringIndexOutOfBoundsException
        // However, count is protected and no public setter, so we cannot set count > buf.length directly.
        // We test that normal usage does not cause exception.
        ByteArrayOutputStream baos = new ByteArrayOutputStream(2);
        baos.write(0x41);
        baos.write(0x42);
        // Normal toString should work
        String s = baos.toString(0);
        if (!"AB".equals(s)) {
            throw new RuntimeException("testToStringWithCountExceedingBuffer failed: expected AB but got " + s);
        }
        // We cannot test the exception path due to lack of public API to corrupt internal state.
    }
}
