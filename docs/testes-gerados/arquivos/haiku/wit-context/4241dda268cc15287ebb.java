/*
 * @test
 * @summary Verifies that reading a float field as short through the public Field API raises IllegalArgumentException, consistent with UnsafeQualifiedFloatFieldAccessorImpl.getShort always throwing
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    public static final float STATIC_FINAL_VALUE = 1.5f;
    public float instanceValue = 2.5f;

    public static void main(String[] args) throws Exception {
        Field staticField = UnsafeQualifiedFloatFieldAccessorImplWitupTest.class.getField("STATIC_FINAL_VALUE");
        expectGetShortIAE(staticField, null);

        UnsafeQualifiedFloatFieldAccessorImplWitupTest obj = new UnsafeQualifiedFloatFieldAccessorImplWitupTest();
        Field instanceField = UnsafeQualifiedFloatFieldAccessorImplWitupTest.class.getField("instanceValue");
        expectGetShortIAE(instanceField, obj);

        if (instanceField.getFloat(obj) != 2.5f) {
            throw new RuntimeException("getFloat returned unexpected value");
        }
    }

    static void expectGetShortIAE(Field f, Object o) {
        try {
            short s = f.getShort(o);
            throw new RuntimeException("expected IllegalArgumentException but got " + s);
        } catch (IllegalArgumentException expected) {
        } catch (IllegalAccessException e) {
            throw new RuntimeException("unexpected IllegalAccessException", e);
        }
    }
}
