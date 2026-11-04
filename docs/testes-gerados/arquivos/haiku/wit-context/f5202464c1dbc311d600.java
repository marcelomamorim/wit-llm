/*
 * @test
 * @summary Field.getBoolean on static float fields must throw IllegalArgumentException
 * @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {
    public static final float FINAL_F = 1.5f;
    public static float plainF = 2.5f;

    public static void main(String[] args) throws Exception {
        check("FINAL_F");
        check("plainF");
    }

    static void check(String name) throws Exception {
        Field f = UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest.class.getField(name);
        try {
            f.getBoolean(null);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("no IllegalArgumentException for " + name);
    }
}
