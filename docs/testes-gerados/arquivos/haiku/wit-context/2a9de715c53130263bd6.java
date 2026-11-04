/*
 * @test
 * @summary Verify that Field.getByte on reference-typed fields throws IllegalArgumentException and that get still works
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeObjectFieldAccessorImplWitupTest {
    public String text = "abc";
    public Object obj = new Object();

    public static void main(String[] args) throws Exception {
        UnsafeObjectFieldAccessorImplWitupTest t = new UnsafeObjectFieldAccessorImplWitupTest();

        Field textField = UnsafeObjectFieldAccessorImplWitupTest.class.getField("text");
        Field objField = UnsafeObjectFieldAccessorImplWitupTest.class.getField("obj");

        expectIllegalArgument(textField, t);
        expectIllegalArgument(objField, t);

        if (!"abc".equals(textField.get(t))) {
            throw new AssertionError("get regression on String field");
        }
        if (objField.get(t) != t.obj) {
            throw new AssertionError("get regression on Object field");
        }
    }

    static void expectIllegalArgument(Field f, Object o) {
        try {
            f.getByte(o);
        } catch (IllegalArgumentException e) {
            return;
        } catch (IllegalAccessException e) {
            throw new AssertionError("unexpected IllegalAccessException for " + f.getName(), e);
        }
        throw new AssertionError("no IllegalArgumentException for " + f.getName());
    }
}
