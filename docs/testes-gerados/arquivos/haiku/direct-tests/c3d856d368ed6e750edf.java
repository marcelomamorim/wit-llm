/*
 * @test
 * @summary Verify that Field.getFloat on a char field returns the widened char value through UnsafeCharacterFieldAccessorImpl.getFloat
 * @run main UnsafeCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeCharacterFieldAccessorImplWitupTest {
    private char c = 'A';

    public static void main(String[] args) throws Exception {
        UnsafeCharacterFieldAccessorImplWitupTest t = new UnsafeCharacterFieldAccessorImplWitupTest();
        Field f = UnsafeCharacterFieldAccessorImplWitupTest.class.getDeclaredField("c");
        f.setAccessible(true);

        check(f.getFloat(t) == 65.0f, "initial 'A' should widen to 65.0f");

        f.setChar(t, (char) 0xFFFF);
        check(f.getFloat(t) == 65535.0f, "0xFFFF should widen to 65535.0f");

        f.setChar(t, (char) 0x0000);
        check(f.getFloat(t) == 0.0f, "0x0000 should widen to 0.0f");

        f.set(t, Character.valueOf('z'));
        check(f.getFloat(t) == 122.0f, "'z' set via set(Object) should widen to 122.0f");
    }

    private static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
