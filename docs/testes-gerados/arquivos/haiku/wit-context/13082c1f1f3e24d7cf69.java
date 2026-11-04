/*
 * @test
 * @summary Verifies that reading a short field as a byte through the public Field API throws IllegalArgumentException, exercising UnsafeShortFieldAccessorImpl.getByte
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeShortFieldAccessorImplWitupTest {
    static short staticShort = 7;
    short instanceShort = 9;

    public static void main(String[] args) throws Exception {
        UnsafeShortFieldAccessorImplWitupTest holder = new UnsafeShortFieldAccessorImplWitupTest();

        Field instanceField = UnsafeShortFieldAccessorImplWitupTest.class.getDeclaredField("instanceShort");
        if (instanceField.getShort(holder) != 9) {
            throw new RuntimeException("unexpected short value for instance field");
        }
        expectIllegalArgument(instanceField, holder);

        Field staticField = UnsafeShortFieldAccessorImplWitupTest.class.getDeclaredField("staticShort");
        if (staticField.getShort(null) != 7) {
            throw new RuntimeException("unexpected short value for static field");
        }
        expectIllegalArgument(staticField, null);
    }

    static void expectIllegalArgument(Field f, Object obj) {
        try {
            byte b = f.getByte(obj);
            throw new RuntimeException("expected IllegalArgumentException but got byte " + b);
        } catch (IllegalArgumentException expected) {
            // expected
        } catch (IllegalAccessException e) {
            throw new RuntimeException("unexpected IllegalAccessException", e);
        }
    }
}
