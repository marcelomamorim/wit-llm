/*
 * @test
 * @summary Checks public Field.getDouble and Field.get behavior for a volatile double field, which uses UnsafeQualifiedDoubleFieldAccessorImpl, and that a wrong receiver type raises IllegalArgumentException
 * @run main UnsafeQualifiedDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    public static class Holder {
        public volatile double value;
    }

    public static void main(String[] args) throws Exception {
        Holder holder = new Holder();
        holder.value = 3.5d;
        Field field = Holder.class.getField("value");

        double direct = field.getDouble(holder);
        if (direct != 3.5d) {
            throw new AssertionError("getDouble returned " + direct + " expected 3.5");
        }

        Object boxed = field.get(holder);
        if (!(boxed instanceof Double) || ((Double) boxed).doubleValue() != 3.5d) {
            throw new AssertionError("get returned " + boxed + " expected Double 3.5");
        }

        field.setDouble(holder, 7.25d);
        if (field.getDouble(holder) != 7.25d) {
            throw new AssertionError("getDouble after setDouble returned unexpected value");
        }

        boolean thrown = false;
        try {
            field.getDouble("not a Holder");
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalArgumentException for wrong receiver type");
        }
    }
}
