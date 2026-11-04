/*
 * @test
 * @summary Verify that getChar on a boolean field is rejected with IllegalArgumentException through the public java.lang.reflect.Field API
 * @run main UnsafeBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeBooleanFieldAccessorImplWitupTest {
    public static boolean staticFlag = true;
    public boolean instanceFlag = true;

    public static void main(String[] args) throws Exception {
        Field sf = UnsafeBooleanFieldAccessorImplWitupTest.class.getDeclaredField("staticFlag");
        Field inf = UnsafeBooleanFieldAccessorImplWitupTest.class.getDeclaredField("instanceFlag");

        expectIllegalArgument(sf, null);
        expectIllegalArgument(inf, new UnsafeBooleanFieldAccessorImplWitupTest());
    }

    static void expectIllegalArgument(Field f, Object obj) {
        try {
            f.getChar(obj);
        } catch (IllegalArgumentException expected) {
            if (expected.getMessage() == null) {
                throw new RuntimeException("IllegalArgumentException without message for " + f.getName());
            }
            return;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unexpected IllegalAccessException for " + f.getName(), e);
        }
        throw new RuntimeException("No IllegalArgumentException thrown for getChar on boolean field " + f.getName());
    }
}
