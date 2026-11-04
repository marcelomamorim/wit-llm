/*
 * @test
 * @summary Verify Field.getFloat on a public short field returns the widened value and rejects a holder of the wrong type
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    public short value;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedShortFieldAccessorImplWitupTest holder = new UnsafeQualifiedShortFieldAccessorImplWitupTest();
        Field field = UnsafeQualifiedShortFieldAccessorImplWitupTest.class.getField("value");

        holder.value = 7;
        float positive = field.getFloat(holder);
        if (positive != 7.0f) {
            throw new RuntimeException("Expected 7.0f but got " + positive);
        }

        holder.value = -3;
        float negative = field.getFloat(holder);
        if (negative != -3.0f) {
            throw new RuntimeException("Expected -3.0f but got " + negative);
        }

        try {
            field.getFloat("not a holder");
            throw new RuntimeException("Expected IllegalArgumentException for wrong holder type");
        } catch (IllegalArgumentException expected) {
        }
    }
}
