/*
 * @test
 * @summary Field.getByte on a char field must throw IllegalArgumentException via the char field accessor
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    public static class Holder {
        public volatile char c = 'A';
        public char p = 'B';
    }

    static void expectIllegalArgument(Field f, Object obj) throws Exception {
        try {
            byte b = f.getByte(obj);
            throw new RuntimeException("expected IllegalArgumentException but got byte " + b);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();

        Field volatileField = Holder.class.getField("c");
        expectIllegalArgument(volatileField, h);

        Field plainField = Holder.class.getField("p");
        expectIllegalArgument(plainField, h);

        if (!Character.valueOf('A').equals(volatileField.get(h))) {
            throw new RuntimeException("get() returned unexpected value for volatile char field");
        }
        if (!Character.valueOf('B').equals(plainField.get(h))) {
            throw new RuntimeException("get() returned unexpected value for plain char field");
        }
    }
}
