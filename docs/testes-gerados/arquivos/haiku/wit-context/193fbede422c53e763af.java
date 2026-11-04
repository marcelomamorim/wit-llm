/*
 * @test
 * @summary Field.getShort on a static long field must throw IllegalArgumentException (observable behaviour of the long accessor getShort)
 * @run main UnsafeQualifiedStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticLongFieldAccessorImplWitupTest {

    public static final long QUALIFIED_VALUE = 42L;
    public static long PLAIN_VALUE = 7L;

    public static void main(String[] args) throws Exception {
        checkShortRejected("QUALIFIED_VALUE");
        checkShortRejected("PLAIN_VALUE");
        checkLongStillWorks();
    }

    private static void checkShortRejected(String name) throws Exception {
        Field f = UnsafeQualifiedStaticLongFieldAccessorImplWitupTest.class.getDeclaredField(name);
        try {
            short s = f.getShort(null);
            throw new RuntimeException("Expected IllegalArgumentException for " + name + " but got " + s);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void checkLongStillWorks() throws Exception {
        Field f = UnsafeQualifiedStaticLongFieldAccessorImplWitupTest.class.getDeclaredField("QUALIFIED_VALUE");
        long v = f.getLong(null);
        if (v != 42L) {
            throw new RuntimeException("Unexpected long value: " + v);
        }
    }
}
