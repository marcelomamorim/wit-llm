/*
 * @test
 * @summary Field.getFloat on a static boolean field must throw IllegalArgumentException
 * @run main UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest {

    static volatile boolean flag = true;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest.class.getDeclaredField("flag");

        expectIllegalArgument(f, null);
        expectIllegalArgument(f, new Object());

        Object boxed = f.get(null);
        if (!Boolean.TRUE.equals(boxed)) {
            throw new RuntimeException("expected Boolean.TRUE from get but got " + boxed);
        }
    }

    static void expectIllegalArgument(Field f, Object obj) throws Exception {
        try {
            float v = f.getFloat(obj);
            throw new RuntimeException("expected IllegalArgumentException but got " + v);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
