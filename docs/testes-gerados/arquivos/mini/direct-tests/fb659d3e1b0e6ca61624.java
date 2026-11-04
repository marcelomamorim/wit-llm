/*
 * @test
 * @summary Test ByteArrayOutputStream.toString(int hibyte) method behavior
 * @run main ByteArrayOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;

public class ByteArrayOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testEmptyBuffer();
        testNonEmptyBuffer();
        testHibyteZero();
        testHibyteNegative();
    }

    private static void testEmptyBuffer() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String s = baos.toString(0);
        if (!"".equals(s)) {
            throw new AssertionError("Expected empty string for empty buffer, got: '" + s + "'");
        }
    }

    private static void testNonEmptyBuffer() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] data = {65, 66, 67}; // ASCII 'A', 'B', 'C'
        try {
            // Use reflection to access protected buf and count for test setup
            java.lang.reflect.Field bufField = ByteArrayOutputStream.class.getDeclaredField("buf");
            java.lang.reflect.Field countField = ByteArrayOutputStream.class.getDeclaredField("count");
            bufField.setAccessible(true);
            countField.setAccessible(true);
            bufField.set(baos, data);
            countField.setInt(baos, data.length);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Reflection failed", e);
        }
        String s = baos.toString(0);
        if (!"ABC".equals(s)) {
            throw new AssertionError("Expected 'ABC', got: '" + s + "'");
        }
    }

    private static void testHibyteZero() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] data = {0x41, 0x42, 0x43}; // 'A', 'B', 'C'
        try {
            java.lang.reflect.Field bufField = ByteArrayOutputStream.class.getDeclaredField("buf");
            java.lang.reflect.Field countField = ByteArrayOutputStream.class.getDeclaredField("count");
            bufField.setAccessible(true);
            countField.setAccessible(true);
            bufField.set(baos, data);
            countField.setInt(baos, data.length);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Reflection failed", e);
        }
        String s = baos.toString(0);
        if (!"ABC".equals(s)) {
            throw new AssertionError("Expected 'ABC' with hibyte=0, got: '" + s + "'");
        }
    }

    private static void testHibyteNegative() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] data = {0x41, 0x42, 0x43}; // 'A', 'B', 'C'
        try {
            java.lang.reflect.Field bufField = ByteArrayOutputStream.class.getDeclaredField("buf");
            java.lang.reflect.Field countField = ByteArrayOutputStream.class.getDeclaredField("count");
            bufField.setAccessible(true);
            countField.setAccessible(true);
            bufField.set(baos, data);
            countField.setInt(baos, data.length);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Reflection failed", e);
        }
        String s = baos.toString(-1);
        if (s.length() != 3) {
            throw new AssertionError("Expected length 3 with hibyte=-1, got length: " + s.length());
        }
    }
}
