import java.lang.reflect.Field;

/*
 * @test
 * @summary Field.getDouble on a static final Object field must throw IllegalArgumentException
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */
public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {
    static final Object VALUE = "x";

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("VALUE");
        boolean thrownNull = false;
        try {
            f.getDouble(null);
        } catch (IllegalArgumentException expected) {
            thrownNull = true;
        }
        if (!thrownNull) {
            throw new RuntimeException("expected IllegalArgumentException for null obj");
        }
        boolean thrownObj = false;
        try {
            f.getDouble(new Object());
        } catch (IllegalArgumentException expected) {
            thrownObj = true;
        }
        if (!thrownObj) {
            throw new RuntimeException("expected IllegalArgumentException for non-null obj");
        }
    }
}
