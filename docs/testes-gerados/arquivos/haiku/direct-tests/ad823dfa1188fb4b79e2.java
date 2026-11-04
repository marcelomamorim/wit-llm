/*
 * @test
 * @summary Field.getFloat on a volatile Object field must throw IllegalArgumentException, as UnsafeQualifiedObjectFieldAccessorImpl.getFloat does
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public static class Holder {
        public volatile Object obj = "value";
        public volatile Object nullObj = null;
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field f = Holder.class.getField("obj");

        boolean thrown = false;
        try {
            f.getFloat(h);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getFloat on Object field");
        }

        Field n = Holder.class.getField("nullObj");
        thrown = false;
        try {
            n.getFloat(h);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getFloat on null Object field");
        }

        if (!"value".equals(f.get(h))) {
            throw new RuntimeException("get() should still return the object value");
        }
    }
}
