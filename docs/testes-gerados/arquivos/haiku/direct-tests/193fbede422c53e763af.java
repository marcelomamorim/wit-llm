/*
 * @test
 * @summary Verify that Field.getShort on a static long field throws IllegalArgumentException via the public reflection API
 * @run main UnsafeQualifiedStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticLongFieldAccessorImplWitupTest {

    public static long VALUE = 123456789012L;
    public static final long FINAL_VALUE = 987654321098L;

    public static void main(String[] args) throws Exception {
        checkShortThrows("VALUE");
        checkShortThrows("FINAL_VALUE");
        checkLongReturnsValue("VALUE", VALUE);
        checkLongReturnsValue("FINAL_VALUE", FINAL_VALUE);
    }

    static void checkShortThrows(String name) throws Exception {
        Field f = UnsafeQualifiedStaticLongFieldAccessorImplWitupTest.class.getDeclaredField(name);
        boolean thrown = false;
        try {
            f.getShort(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException from getShort on " + name);
        }
    }

    static void checkLongReturnsValue(String name, long expected) throws Exception {
        Field f = UnsafeQualifiedStaticLongFieldAccessorImplWitupTest.class.getDeclaredField(name);
        long actual = f.getLong(null);
        if (actual != expected) {
            throw new AssertionError("getLong mismatch for " + name + ": " + actual + " != " + expected);
        }
    }
}
