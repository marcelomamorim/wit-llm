/*
 * @test
 * @summary Field.getByte on static float fields must throw IllegalArgumentException, while get and getFloat still return the float value
 * @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {

    static volatile float volatileField = 1.5f;
    static final float finalField = 2.5f;
    static float plainField = 3.5f;

    public static void main(String[] args) throws Exception {
        checkGetByteThrows("volatileField");
        checkGetByteThrows("finalField");
        checkGetByteThrows("plainField");

        checkFloatValue("volatileField", 1.5f);
        checkFloatValue("finalField", 2.5f);
        checkFloatValue("plainField", 3.5f);
    }

    static void checkGetByteThrows(String name) throws Exception {
        Field f = UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest.class.getDeclaredField(name);
        try {
            f.getByte(null);
            throw new RuntimeException("Expected IllegalArgumentException for getByte on " + name);
        } catch (IllegalArgumentException expected) {
        }
    }

    static void checkFloatValue(String name, float expected) throws Exception {
        Field f = UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest.class.getDeclaredField(name);
        Object boxed = f.get(null);
        if (!(boxed instanceof Float) || ((Float) boxed).floatValue() != expected) {
            throw new RuntimeException("Unexpected get() value for " + name + ": " + boxed);
        }
        if (f.getFloat(null) != expected) {
            throw new RuntimeException("Unexpected getFloat() value for " + name);
        }
    }
}
