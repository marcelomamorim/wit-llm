/*
 * @test
 * @summary Field.getChar on a public static final boolean field must throw IllegalArgumentException through the qualified static boolean accessor
 * @run main UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest {
    public static final boolean FLAG = true;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest.class.getField("FLAG");

        boolean rejected = false;
        try {
            f.getChar(null);
        } catch (IllegalArgumentException e) {
            rejected = true;
        }
        if (!rejected) {
            throw new RuntimeException("getChar must throw IllegalArgumentException for a boolean field");
        }

        if (!f.getBoolean(null)) {
            throw new RuntimeException("getBoolean must return the field value true");
        }

        Object value = f.get(null);
        if (!Boolean.TRUE.equals(value)) {
            throw new RuntimeException("get must return Boolean.TRUE");
        }
    }
}
