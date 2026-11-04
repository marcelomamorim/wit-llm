/*
 * @test
 * @summary Verify that reading a static final boolean field as float via reflection throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest {

    static final boolean FLAG = true;

    static boolean plain = false;

    public static void main(String[] args) throws Exception {
        Field flag = UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest.class.getDeclaredField("FLAG");
        flag.setAccessible(true);

        if (flag.getBoolean(null) != true) {
            throw new RuntimeException("getBoolean control failed");
        }

        expectIllegalArgument(flag, null);

        Field plainField = UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest.class.getDeclaredField("plain");
        plainField.setAccessible(true);
        expectIllegalArgument(plainField, null);
    }

    private static void expectIllegalArgument(Field f, Object obj) {
        try {
            f.getFloat(obj);
        } catch (IllegalArgumentException expected) {
            return;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("unexpected IllegalAccessException", e);
        }
        throw new RuntimeException("expected IllegalArgumentException for getFloat on boolean field " + f.getName());
    }
}
