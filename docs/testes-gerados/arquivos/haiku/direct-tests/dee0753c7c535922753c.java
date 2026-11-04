/*
 * @test
 * @summary Field.getBoolean on static char fields must throw IllegalArgumentException
 * @run main UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest {
    public static char mutable = 'a';
    public static final char constant = 'b';

    public static void main(String[] args) throws Exception {
        checkGetBooleanRejects("mutable");
        checkGetBooleanRejects("constant");
    }

    static void checkGetBooleanRejects(String name) throws Exception {
        Field f = UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest.class.getDeclaredField(name);
        try {
            f.getBoolean(null);
            throw new RuntimeException("expected IllegalArgumentException for " + name);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
