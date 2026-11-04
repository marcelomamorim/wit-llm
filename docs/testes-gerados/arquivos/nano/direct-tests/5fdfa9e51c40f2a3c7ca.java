/* @test    @summary Test for Unsafe.objectFieldOffset method    @run main UnsafeWitupTest */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testObjectFieldOffsetWithValidClassAndField();
        testObjectFieldOffsetWithNullClass();
        testObjectFieldOffsetWithNullFieldName();
    }

    private static void testObjectFieldOffsetWithValidClassAndField() {
        try {
            long offset = Unsafe.getUnsafe().objectFieldOffset(TestClass.class, "testField");
            System.out.println("Offset for 'testField' in TestClass: " + offset);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in valid case", e);
        }
    }

    private static void testObjectFieldOffsetWithNullClass() {
        try {
            Unsafe.getUnsafe().objectFieldOffset(null, "testField");
            throw new AssertionError("Expected NullPointerException for null class");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testObjectFieldOffsetWithNullFieldName() {
        try {
            Unsafe.getUnsafe().objectFieldOffset(TestClass.class, null);
            throw new AssertionError("Expected NullPointerException for null field name");
        } catch (NullPointerException e) {
            // expected
        }
    }

    public static class TestClass {
        public int testField;
    }
}
