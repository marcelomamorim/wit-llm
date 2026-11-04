/*
 * @test
 * @summary Verify that Field.get on a public non-final, non-volatile reference field returns the stored object, exercising the Unsafe-based object accessor path
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeObjectFieldAccessorImplWitupTest {
    public Object ref;

    public static void main(String[] args) throws Exception {
        UnsafeObjectFieldAccessorImplWitupTest t = new UnsafeObjectFieldAccessorImplWitupTest();
        Field f = UnsafeObjectFieldAccessorImplWitupTest.class.getField("ref");

        Object s = "value";
        t.ref = s;
        check(f.get(t) == s, "string identity");

        t.ref = null;
        check(f.get(t) == null, "null value");

        Integer i = Integer.valueOf(42);
        t.ref = i;
        check(f.get(t) == i, "boxed integer identity");

        Object o = new Object();
        t.ref = o;
        check(f.get(t) == o, "plain object identity");

        for (int k = 0; k < 30; k++) {
            t.ref = Integer.valueOf(k);
            check(f.get(t) == t.ref, "repeated get " + k);
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException("Failed: " + msg);
        }
    }
}
