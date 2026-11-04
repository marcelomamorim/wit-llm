/*
 * @test
 * @summary Verify that Field.getDouble on a static boolean field throws IllegalArgumentException, reaching UnsafeQualifiedStaticBooleanFieldAccessorImpl.getDouble through the public reflection API
 * @run main UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest {

    static volatile boolean volatileFlag = true;
    static final boolean finalFlag = true;

    public static void main(String[] args) throws Exception {
        checkGetDoubleThrows("volatileFlag");
        checkGetDoubleThrows("finalFlag");
        checkGetBooleanStillWorks();
    }

    static void checkGetDoubleThrows(String name) throws Exception {
        Field f = UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest.class.getDeclaredField(name);
        try {
            double d = f.getDouble(null);
            throw new RuntimeException("Expected IllegalArgumentException for " + name + " but got " + d);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void checkGetBooleanStillWorks() throws Exception {
        Field f = UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest.class.getDeclaredField("volatileFlag");
        if (!f.getBoolean(null)) {
            throw new RuntimeException("Expected getBoolean to return true");
        }
    }
}
