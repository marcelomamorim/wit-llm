/*
 * @test
 * @summary Verifies double field reads through the public Field API, which reaches UnsafeDoubleFieldAccessorImpl.getDouble
 * @run main UnsafeDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeDoubleFieldAccessorImplWitupTest {

    public static class Holder {
        public double value;
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getField("value");

        Holder h = new Holder();
        h.value = 3.5;
        if (f.getDouble(h) != 3.5) {
            throw new RuntimeException("getDouble returned " + f.getDouble(h) + ", expected 3.5");
        }

        f.setDouble(h, -2.25);
        if (f.getDouble(h) != -2.25) {
            throw new RuntimeException("getDouble after setDouble returned " + f.getDouble(h));
        }
        if (h.value != -2.25) {
            throw new RuntimeException("field value not updated: " + h.value);
        }

        Object boxed = f.get(h);
        if (!(boxed instanceof Double) || ((Double) boxed).doubleValue() != -2.25) {
            throw new RuntimeException("get(Object) returned " + boxed + ", expected Double(-2.25)");
        }

        h.value = 0.0;
        if (f.getDouble(h) != 0.0) {
            throw new RuntimeException("getDouble returned " + f.getDouble(h) + ", expected 0.0");
        }
    }
}
