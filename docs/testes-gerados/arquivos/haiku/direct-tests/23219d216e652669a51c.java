/*
 * @test
 * @summary Field.getChar on a reference field must throw IllegalArgumentException, observed through the public reflection API
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeObjectFieldAccessorImplWitupTest {
    public Object ref = "value";

    public static void main(String[] args) throws Exception {
        UnsafeObjectFieldAccessorImplWitupTest t = new UnsafeObjectFieldAccessorImplWitupTest();
        Field f = UnsafeObjectFieldAccessorImplWitupTest.class.getField("ref");

        boolean thrown = false;
        try {
            char c = f.getChar(t);
            throw new AssertionError("expected IllegalArgumentException, got " + c);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getChar did not throw");
        }

        thrown = false;
        try {
            f.getChar(t);
            throw new AssertionError("second getChar call did not throw");
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("second getChar did not throw");
        }

        Object v = f.get(t);
        if (!"value".equals(v)) {
            throw new AssertionError("field value changed: " + v);
        }
    }
}
