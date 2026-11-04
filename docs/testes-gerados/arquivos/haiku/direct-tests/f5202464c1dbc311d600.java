/*
 * @test
 * @summary Field.getBoolean on static float fields must throw IllegalArgumentException, reaching the qualified static float accessor getBoolean path through the public java.lang.reflect.Field API
 * @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {

    public static final float FINAL_FLOAT = 1.5f;
    public static float mutableFloat = 2.5f;

    public static void main(String[] args) throws Exception {
        testFinalStaticFloatGetBooleanThrows();
        testMutableStaticFloatGetBooleanThrows();
        testFinalStaticFloatGetFloatStillWorks();
    }

    static void testFinalStaticFloatGetBooleanThrows() throws Exception {
        Field f = UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest.class.getDeclaredField("FINAL_FLOAT");
        boolean thrown = false;
        try {
            f.getBoolean(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getBoolean on final static float field");
        }
    }

    static void testMutableStaticFloatGetBooleanThrows() throws Exception {
        Field f = UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest.class.getDeclaredField("mutableFloat");
        boolean thrown = false;
        try {
            f.getBoolean(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getBoolean on static float field");
        }
    }

    static void testFinalStaticFloatGetFloatStillWorks() throws Exception {
        Field f = UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest.class.getDeclaredField("FINAL_FLOAT");
        float v = f.getFloat(null);
        if (v != 1.5f) {
            throw new RuntimeException("Unexpected float value: " + v);
        }
    }
}
