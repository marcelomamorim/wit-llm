/*
 * @test
 * @summary Field.getBoolean on a short field must throw IllegalArgumentException through the unsafe short accessor
 * @run main UnsafeQualifiedStaticShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticShortFieldAccessorImplWitupTest {

    static short staticShort = 42;
    short instanceShort = 5;

    public static void main(String[] args) throws Exception {
        Field sf = UnsafeQualifiedStaticShortFieldAccessorImplWitupTest.class.getDeclaredField("staticShort");
        if (sf.getShort(null) != 42) {
            throw new RuntimeException("static short read mismatch");
        }
        try {
            sf.getBoolean(null);
            throw new RuntimeException("expected IllegalArgumentException for static short getBoolean");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        Field inf = UnsafeQualifiedStaticShortFieldAccessorImplWitupTest.class.getDeclaredField("instanceShort");
        UnsafeQualifiedStaticShortFieldAccessorImplWitupTest t = new UnsafeQualifiedStaticShortFieldAccessorImplWitupTest();
        if (inf.getShort(t) != 5) {
            throw new RuntimeException("instance short read mismatch");
        }
        try {
            inf.getBoolean(t);
            throw new RuntimeException("expected IllegalArgumentException for instance short getBoolean");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
