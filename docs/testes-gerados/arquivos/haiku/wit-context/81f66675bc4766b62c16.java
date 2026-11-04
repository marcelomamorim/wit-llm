/*
 * @test
 * @summary Field.getDouble on a volatile float field reaches the qualified float accessor and rejects objects of the wrong type
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    public static class Holder {
        public volatile float f = 1.5f;
    }

    public static void main(String[] args) throws Exception {
        Field field = Holder.class.getField("f");
        Holder h = new Holder();

        double v = field.getDouble(h);
        if (v != 1.5d) {
            throw new RuntimeException("unexpected value " + v);
        }

        field.setFloat(h, 2.25f);
        if (field.getDouble(h) != 2.25d) {
            throw new RuntimeException("value not updated");
        }

        try {
            field.getDouble(new Object());
            throw new RuntimeException("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
        }
    }
}
