/*
 * @test
 * @summary Verify that Field.getFloat on a public static double field throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {
    public static final double VALUE = 1.5d;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest.class.getField("VALUE");
        try {
            field.getFloat(null);
            throw new RuntimeException("expected IllegalArgumentException from getFloat on double field");
        } catch (IllegalArgumentException expected) {
        }
        if (field.getDouble(null) != 1.5d) {
            throw new RuntimeException("getDouble returned unexpected value");
        }
    }
}
