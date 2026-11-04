/*
 * @test
 * @summary Field.getFloat on a reference-typed field must throw IllegalArgumentException
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public static class Holder {
        public final Object ref = "x";
        public Object plain = "y";
    }

    public static void main(String[] args) throws Exception {
        Holder holder = new Holder();

        Field finalField = Holder.class.getField("ref");
        try {
            finalField.getFloat(holder);
            throw new AssertionError("expected IllegalArgumentException for final Object field");
        } catch (IllegalArgumentException expected) {
        }

        Field plainField = Holder.class.getField("plain");
        try {
            plainField.getFloat(holder);
            throw new AssertionError("expected IllegalArgumentException for Object field");
        } catch (IllegalArgumentException expected) {
        }
    }
}
