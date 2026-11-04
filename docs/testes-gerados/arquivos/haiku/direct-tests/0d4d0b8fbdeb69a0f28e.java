/*
 * @test
 * @summary Verify that getChar on an Object-typed field reached through reflection throws IllegalArgumentException
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public volatile Object vol = "v";
    public final Object fin = "f";
    public Object plain = "p";

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedObjectFieldAccessorImplWitupTest t = new UnsafeQualifiedObjectFieldAccessorImplWitupTest();
        checkGetCharThrows(t, "vol");
        checkGetCharThrows(t, "fin");
        checkGetCharThrows(t, "plain");
    }

    private static void checkGetCharThrows(Object target, String name) throws Exception {
        Field f = UnsafeQualifiedObjectFieldAccessorImplWitupTest.class.getField(name);
        boolean thrown = false;
        try {
            f.getChar(target);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getChar on field " + name);
        }
    }
}
