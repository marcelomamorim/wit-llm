/*
 * @test
 * @summary Verify Field.getByte rejects int fields (narrowing is not a widening conversion), exercising the public reflection path
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    private final int finalValue = 42;
    private int plainValue = 7;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedIntegerFieldAccessorImplWitupTest t = new UnsafeQualifiedIntegerFieldAccessorImplWitupTest();
        checkField(t, "finalValue", 42);
        checkField(t, "plainValue", 7);
    }

    private static void checkField(Object target, String name, int expected) throws Exception {
        Field f = UnsafeQualifiedIntegerFieldAccessorImplWitupTest.class.getDeclaredField(name);
        f.setAccessible(true);
        if (f.getInt(target) != expected) {
            throw new RuntimeException("unexpected getInt value for " + name);
        }
        try {
            f.getByte(target);
            throw new RuntimeException("expected IllegalArgumentException for getByte on int field " + name);
        } catch (IllegalArgumentException expectedException) {
            // expected
        }
    }
}
