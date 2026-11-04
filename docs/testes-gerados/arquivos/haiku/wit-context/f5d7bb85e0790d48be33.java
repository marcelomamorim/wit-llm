/*
 * @test
 * @summary Field.getByte on a static long field must throw IllegalArgumentException (narrowing not allowed)
 * @run main UnsafeQualifiedStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticLongFieldAccessorImplWitupTest {
    static final long FINAL_VALUE = 42L;
    static long mutableValue = 7L;

    public static void main(String[] args) throws Exception {
        check("FINAL_VALUE");
        check("mutableValue");
    }

    static void check(String name) throws Exception {
        Field f = UnsafeQualifiedStaticLongFieldAccessorImplWitupTest.class.getDeclaredField(name);
        try {
            byte b = f.getByte(null);
            throw new RuntimeException("expected IllegalArgumentException for " + name + " but got " + b);
        } catch (IllegalArgumentException expected) {
        }
    }
}
