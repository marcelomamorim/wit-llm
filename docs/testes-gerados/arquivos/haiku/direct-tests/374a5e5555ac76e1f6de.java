/*
 * @test
 * @summary Verify that Field.getDouble on a reference-typed final field throws IllegalArgumentException, exercising the unsafe-qualified object field accessor path
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {
    private final Object value = "abc";
    private final Object nullValue = null;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedObjectFieldAccessorImplWitupTest t =
            new UnsafeQualifiedObjectFieldAccessorImplWitupTest();

        Field f = UnsafeQualifiedObjectFieldAccessorImplWitupTest.class.getDeclaredField("value");
        f.setAccessible(true);
        expectIllegalArgument(f, t);

        Field g = UnsafeQualifiedObjectFieldAccessorImplWitupTest.class.getDeclaredField("nullValue");
        g.setAccessible(true);
        expectIllegalArgument(g, t);
    }

    static void expectIllegalArgument(Field f, Object obj) {
        try {
            f.getDouble(obj);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException from getDouble on field " + f.getName());
    }
}
