/*
 * @test    
 * @summary Field.getByte on float fields must throw IllegalArgumentException and getFloat must still return the stored value
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    public float publicField = 1.5f;
    public final float finalField = 2.5f;
    public volatile float volatileField = 3.5f;
    public static final float STATIC_FINAL = 4.5f;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedFloatFieldAccessorImplWitupTest t = new UnsafeQualifiedFloatFieldAccessorImplWitupTest();
        check(t, "publicField", 1.5f);
        check(t, "finalField", 2.5f);
        check(t, "volatileField", 3.5f);
        check(null, "STATIC_FINAL", 4.5f);
    }

    static void check(Object obj, String name, float expected) throws Exception {
        Field f = UnsafeQualifiedFloatFieldAccessorImplWitupTest.class.getField(name);

        float actual = f.getFloat(obj);
        if (actual != expected) {
            throw new RuntimeException("getFloat mismatch for " + name + ": " + actual);
        }

        boolean thrown = false;
        try {
            f.getByte(obj);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getByte did not throw IllegalArgumentException for " + name);
        }
    }
}
