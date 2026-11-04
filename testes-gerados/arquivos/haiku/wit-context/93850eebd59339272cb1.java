/*
 * @test
 * @summary Verifies that Field.getLong on an Object-typed field throws IllegalArgumentException, which reaches UnsafeQualifiedObjectFieldAccessorImpl.getLong through the public reflection API
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public volatile Object qualifiedRef = "value";

    public Object plainRef = "value";

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedObjectFieldAccessorImplWitupTest holder =
            new UnsafeQualifiedObjectFieldAccessorImplWitupTest();
        expectIllegalArgumentFromGetLong(holder, "qualifiedRef");
        expectIllegalArgumentFromGetLong(holder, "plainRef");
    }

    private static void expectIllegalArgumentFromGetLong(Object target, String name)
            throws Exception {
        Field field = target.getClass().getField(name);
        try {
            long value = field.getLong(target);
            throw new RuntimeException("Expected IllegalArgumentException for field " + name
                    + " but got value " + value);
        } catch (IllegalArgumentException expected) {
            // expected behavior
        }
    }
}
