/*
 * @test
 * @summary Verify that Field.getFloat on a static Object field throws IllegalArgumentException through the public reflection API
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {

    static volatile Object volatileField = "value";
    static final Object finalField = "value";

    public static void main(String[] args) throws Exception {
        checkGetFloatThrowsIAE("volatileField");
        checkGetFloatThrowsIAE("finalField");
    }

    static void checkGetFloatThrowsIAE(String name) throws Exception {
        Field f = UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField(name);
        if (!Modifier.isStatic(f.getModifiers())) {
            throw new RuntimeException("Field is not static: " + name);
        }
        try {
            float v = f.getFloat(null);
            throw new RuntimeException("Expected IllegalArgumentException for " + name + " but got " + v);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
