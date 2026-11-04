/*
 * @test
 * @summary Verify Field.getInt on a char field widens the value and rejects objects of an incompatible type
 * @run main UnsafeCharacterFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeCharacterFieldAccessorImplWitupTest {
    public char value = 'A';

    public static void main(String[] args) throws Exception {
        UnsafeCharacterFieldAccessorImplWitupTest holder = new UnsafeCharacterFieldAccessorImplWitupTest();
        Field field = UnsafeCharacterFieldAccessorImplWitupTest.class.getField("value");

        int first = field.getInt(holder);
        if (first != (int) 'A') {
            throw new RuntimeException("Expected 65 but got " + first);
        }

        holder.value = 'z';
        int second = field.getInt(holder);
        if (second != (int) 'z') {
            throw new RuntimeException("Expected 122 but got " + second);
        }

        try {
            field.getInt("not a holder");
            throw new RuntimeException("IllegalArgumentException expected for incompatible object");
        } catch (IllegalArgumentException expected) {
        }
    }
}
