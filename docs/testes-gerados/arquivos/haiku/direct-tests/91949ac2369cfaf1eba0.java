/*
 * @test
 * @summary Verify Field.getDouble on a char field, which dispatches to UnsafeCharacterFieldAccessorImpl.getDouble
 * @run main UnsafeCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeCharacterFieldAccessorImplWitupTest {
    public char value;

    public static void main(String[] args) throws Exception {
        UnsafeCharacterFieldAccessorImplWitupTest holder = new UnsafeCharacterFieldAccessorImplWitupTest();
        Field field = UnsafeCharacterFieldAccessorImplWitupTest.class.getDeclaredField("value");

        holder.value = 'A';
        double d = field.getDouble(holder);
        if (d != 65.0d) {
            throw new AssertionError("expected 65.0 but was " + d);
        }

        holder.value = (char) 0xFFFF;
        d = field.getDouble(holder);
        if (d != 65535.0d) {
            throw new AssertionError("expected 65535.0 but was " + d);
        }

        holder.value = (char) 0;
        d = field.getDouble(holder);
        if (d != 0.0d) {
            throw new AssertionError("expected 0.0 but was " + d);
        }

        Object wrongType = "not a holder";
        try {
            field.getDouble(wrongType);
            throw new AssertionError("expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
