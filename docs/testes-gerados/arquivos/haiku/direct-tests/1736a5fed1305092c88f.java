/*
 * @test
 * @summary Field.getBoolean on a volatile long field must throw IllegalArgumentException, while get and getLong still work
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
            f.getBoolean(t);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getBoolean on volatile long field did not throw IllegalArgumentException");
        }

        Object boxed = f.get(t);
        if (!(boxed instanceof Long) || ((Long) boxed).longValue() != 42L) {
            throw new AssertionError("get returned unexpected value: " + boxed);
        }
        if (f.getLong(t) != 42L) {
            throw new AssertionError("getLong returned unexpected value");
        }

        thrown = false;
        try {
            f.getBoolean(t);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getBoolean did not throw on second invocation");
        }
    }
}
