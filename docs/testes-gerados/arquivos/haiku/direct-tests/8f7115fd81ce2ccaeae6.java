/*
 * @test
 * @summary Verify that Field.getInt on a char field returns the widened char value through the public reflection API
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    public volatile char c = 'A';
    public final char f = 'Z';

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedCharacterFieldAccessorImplWitupTest t =
            new UnsafeQualifiedCharacterFieldAccessorImplWitupTest();

        Field fc = UnsafeQualifiedCharacterFieldAccessorImplWitupTest.class.getField("c");
        if (fc.getInt(t) != 65) {
            throw new RuntimeException("Expected 65 for volatile char 'A', got " + fc.getInt(t));
        }

        t.c = (char) 65535;
        if (fc.getInt(t) != 65535) {
            throw new RuntimeException("Expected 65535 after update, got " + fc.getInt(t));
        }

        Field ff = UnsafeQualifiedCharacterFieldAccessorImplWitupTest.class.getField("f");
        if (ff.getInt(t) != 'Z') {
            throw new RuntimeException("Expected 'Z' value for final char field, got " + ff.getInt(t));
        }
    }
}
