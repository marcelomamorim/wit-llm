/*
 * @test
 * @summary Field.getInt on a volatile long field must throw IllegalArgumentException (reaches UnsafeQualifiedLongFieldAccessorImpl.getInt)
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {
    public volatile long value = 42L;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedLongFieldAccessorImplWitupTest t = new UnsafeQualifiedLongFieldAccessorImplWitupTest();
        Field f = UnsafeQualifiedLongFieldAccessorImplWitupTest.class.getField("value");

        boolean thrown = false;
        try {
            int r = f.getInt(t);
            throw new RuntimeException("getInt did not throw, returned " + r);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("IllegalArgumentException not thrown");
        }

        if (f.getLong(t) != 42L) {
            throw new RuntimeException("getLong sanity check failed");
        }

        UnsafeQualifiedLongFieldAccessorImplWitupTest t2 = new UnsafeQualifiedLongFieldAccessorImplWitupTest();
        t2.value = Long.MAX_VALUE;
        thrown = false;
        try {
            f.getInt(t2);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("IllegalArgumentException not thrown for second object");
        }
    }
}
