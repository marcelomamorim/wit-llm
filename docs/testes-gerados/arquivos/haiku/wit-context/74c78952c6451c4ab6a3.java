import java.lang.reflect.Field;

/*
 * @test
 * @summary Verifies that reading a double field as char through the public Field API throws IllegalArgumentException, and that the double value is still readable.
 * @run main UnsafeQualifiedDoubleFieldAccessorImplWitupTest
 */
public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {
    public volatile double value = 1.5;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedDoubleFieldAccessorImplWitupTest target = new UnsafeQualifiedDoubleFieldAccessorImplWitupTest();
        Field field = UnsafeQualifiedDoubleFieldAccessorImplWitupTest.class.getField("value");

        double d = field.getDouble(target);
        if (d != 1.5) {
            throw new RuntimeException("Unexpected double value: " + d);
        }

        boolean thrown = false;
        try {
            field.getChar(target);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getChar on double field");
        }
    }
}
