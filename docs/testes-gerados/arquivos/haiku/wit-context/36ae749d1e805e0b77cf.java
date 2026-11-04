/*
 * @test
 * @summary Verify that Field.getChar on a float field reaches UnsafeFloatFieldAccessorImpl.getChar and throws IllegalArgumentException, while getFloat still works
 * @run main UnsafeFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeFloatFieldAccessorImplWitupTest {

    public static class Holder {
        public float value = 1.5f;
    }

    public static void main(String[] args) throws Exception {
        Holder holder = new Holder();
        Field field = Holder.class.getDeclaredField("value");

        boolean thrown = false;
        try {
            char c = field.getChar(holder);
            throw new RuntimeException("Expected IllegalArgumentException but got char: " + c);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("IllegalArgumentException was not thrown by getChar");
        }

        float f = field.getFloat(holder);
        if (f != 1.5f) {
            throw new RuntimeException("Unexpected float value: " + f);
        }
    }
}
