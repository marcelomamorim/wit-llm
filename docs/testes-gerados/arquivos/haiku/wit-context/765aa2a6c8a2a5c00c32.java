/*
 * @test
 * @summary Verify that Field.getShort on a boolean field throws IllegalArgumentException, reaching UnsafeBooleanFieldAccessorImpl.getShort through the public reflection API
 * @run main UnsafeBooleanFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeBooleanFieldAccessorImplWitupTest {
    public boolean flag = true;

    public static void main(String[] args) throws Exception {
        UnsafeBooleanFieldAccessorImplWitupTest target = new UnsafeBooleanFieldAccessorImplWitupTest();
        Field field = UnsafeBooleanFieldAccessorImplWitupTest.class.getField("flag");

        boolean thrown = false;
        try {
            field.getShort(target);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException from getShort on boolean field");
        }

        if (!field.getBoolean(target)) {
            throw new AssertionError("getBoolean should return the current value of the boolean field");
        }
    }
}
