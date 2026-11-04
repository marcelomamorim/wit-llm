/*
 * @test
 * @summary Verify that Field.getShort on a public static volatile Object field throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {
    public static volatile Object value = "x";

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest.class.getField("value");
        try {
            f.getShort(null);
            throw new RuntimeException("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
        }
    }
}
