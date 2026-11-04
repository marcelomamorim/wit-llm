/*
 * @test
 * @summary Verify that Field.getLong on a reference (Object) field throws IllegalArgumentException, exercising UnsafeObjectFieldAccessorImpl.getLong through the public reflection API
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeObjectFieldAccessorImplWitupTest {

    public static class Holder {
        public Object ref = "value";
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getField("ref");
        Holder h = new Holder();
        if (!"value".equals(f.get(h))) {
            throw new RuntimeException("Field.get on Object field returned unexpected value");
        }
        expectIllegalArgument(f, h);
        expectIllegalArgument(f, null);
    }

    static void expectIllegalArgument(Field f, Object obj) throws Exception {
        try {
            f.getLong(obj);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException from getLong on Object field");
    }
}
