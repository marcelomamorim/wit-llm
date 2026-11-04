/*
 * @test
 * @summary Field.getShort on a reference (volatile Object) field must throw IllegalArgumentException
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public volatile Object ref = "x";
    public static volatile Object sref = "y";

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedObjectFieldAccessorImplWitupTest t = new UnsafeQualifiedObjectFieldAccessorImplWitupTest();

        Field f = UnsafeQualifiedObjectFieldAccessorImplWitupTest.class.getField("ref");
        expectIllegalArgument(f, t);

        Field sf = UnsafeQualifiedObjectFieldAccessorImplWitupTest.class.getField("sref");
        expectIllegalArgument(sf, null);

        if (!"x".equals(f.get(t))) {
            throw new RuntimeException("get() returned unexpected value");
        }
    }

    static void expectIllegalArgument(Field f, Object o) {
        try {
            f.getShort(o);
        } catch (IllegalArgumentException expected) {
            return;
        } catch (Exception e) {
            throw new RuntimeException("unexpected exception: " + e, e);
        }
        throw new RuntimeException("getShort did not throw IllegalArgumentException");
    }
}
