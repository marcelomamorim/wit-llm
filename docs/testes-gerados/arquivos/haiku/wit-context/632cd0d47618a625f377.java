/*
 * @test
 * @summary Verifies that getInt on a static float field throws IllegalArgumentException through the public Field API
 * @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {

    public static final float FINAL_F = 1.5f;
    public static float mutableF = 2.5f;

    public static void main(String[] args) throws Exception {
        Field fin = UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest.class.getDeclaredField("FINAL_F");
        Field mut = UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest.class.getDeclaredField("mutableF");

        if (((Float) fin.get(null)).floatValue() != 1.5f) {
            throw new RuntimeException("unexpected value for FINAL_F");
        }
        if (((Float) mut.get(null)).floatValue() != 2.5f) {
            throw new RuntimeException("unexpected value for mutableF");
        }

        expectIllegalArgument(fin);
        expectIllegalArgument(mut);
    }

    private static void expectIllegalArgument(Field f) {
        try {
            int v = f.getInt(null);
            throw new RuntimeException("no IllegalArgumentException for " + f.getName() + ", got " + v);
        } catch (IllegalArgumentException expected) {
            // expected
        } catch (IllegalAccessException e) {
            throw new RuntimeException("unexpected IllegalAccessException for " + f.getName(), e);
        }
    }
}
