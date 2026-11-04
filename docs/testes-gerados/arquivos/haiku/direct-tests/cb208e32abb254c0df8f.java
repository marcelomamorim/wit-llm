/*
 * @test
 * @summary Field.getByte on a final short field must throw IllegalArgumentException; the public widening getters must still return the stored value
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    public final short fs = 5;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedShortFieldAccessorImplWitupTest t = new UnsafeQualifiedShortFieldAccessorImplWitupTest();
        Field f = UnsafeQualifiedShortFieldAccessorImplWitupTest.class.getField("fs");

        boolean thrown = false;
        try {
            f.getByte(t);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Field.getByte on short field did not throw IllegalArgumentException");
        }

        if (f.getInt(t) != 5) {
            throw new RuntimeException("getInt returned unexpected value");
        }
        if (f.getLong(t) != 5L) {
            throw new RuntimeException("getLong returned unexpected value");
        }
        Object boxed = f.get(t);
        if (!(boxed instanceof Short) || ((Short) boxed).shortValue() != 5) {
            throw new RuntimeException("get returned unexpected value");
        }
    }
}
