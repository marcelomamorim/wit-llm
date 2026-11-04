/*
 * @test
 * @summary Field.getShort on a static Object field must throw IllegalArgumentException, exercising UnsafeStaticObjectFieldAccessorImpl.getShort
 * @run main UnsafeStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {

    static Object nonFinalObjField = "value";
    static final Object finalObjField = "finalValue";

    public static void main(String[] args) throws Exception {
        checkGetShortThrows("nonFinalObjField");
        checkGetShortThrows("finalObjField");
        checkGetShortIgnoresReceiver();
    }

    static void checkGetShortThrows(String name) throws Exception {
        Field f = UnsafeStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField(name);
        try {
            short s = f.getShort(null);
            throw new RuntimeException("Expected IllegalArgumentException for " + name + " but got " + s);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void checkGetShortIgnoresReceiver() throws Exception {
        Field f = UnsafeStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("nonFinalObjField");
        try {
            f.getShort(new Object());
            throw new RuntimeException("Expected IllegalArgumentException with non-null receiver");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
