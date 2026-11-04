/*
 * @test
 * @summary Verifies that getByte on a boolean field reached via public Field API throws IllegalArgumentException
 * @run main UnsafeBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeBooleanFieldAccessorImplWitupTest {
    public boolean flag;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeBooleanFieldAccessorImplWitupTest.class.getDeclaredField("flag");
        UnsafeBooleanFieldAccessorImplWitupTest t = new UnsafeBooleanFieldAccessorImplWitupTest();

        if (f.getBoolean(t) != false) {
            throw new RuntimeException("getBoolean sanity check failed");
        }

        expectIllegalArgument(f, t);
        expectIllegalArgument(f, null);
    }

    private static void expectIllegalArgument(Field f, Object obj) {
        try {
            f.getByte(obj);
        } catch (IllegalArgumentException expected) {
            return;
        } catch (Exception e) {
            throw new RuntimeException("unexpected exception: " + e, e);
        }
        throw new RuntimeException("expected IllegalArgumentException from getByte");
    }
}
