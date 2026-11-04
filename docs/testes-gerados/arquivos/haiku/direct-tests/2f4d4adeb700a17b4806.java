/*
 * @test
 * @summary Behavioral check of float field access through the public java.lang.reflect.Field API on a volatile float field
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    static class Holder {
        volatile float value;
    }

    public static void main(String[] args) throws Exception {
        testGetFloatReturnsStoredValue();
        testGetFloatReflectsUpdatesFromSetFloat();
        testGetFloatViaBoxedGetReturnsSameValue();
        testGetFloatWithWrongObjectTypeThrows();
    }

    static Field valueField() throws Exception {
        Field f = Holder.class.getDeclaredField("value");
        f.setAccessible(true);
        return f;
    }

    static void testGetFloatReturnsStoredValue() throws Exception {
        Holder h = new Holder();
        h.value = 1.5f;
        float r = valueField().getFloat(h);
        if (r != 1.5f) {
            throw new RuntimeException("expected 1.5f but got " + r);
        }
    }

    static void testGetFloatReflectsUpdatesFromSetFloat() throws Exception {
        Holder h = new Holder();
        Field f = valueField();
        f.setFloat(h, -2.25f);
        float r = f.getFloat(h);
        if (r != -2.25f) {
            throw new RuntimeException("expected -2.25f but got " + r);
        }
        if (h.value != -2.25f) {
            throw new RuntimeException("field state mismatch: " + h.value);
        }
    }

    static void testGetFloatViaBoxedGetReturnsSameValue() throws Exception {
        Holder h = new Holder();
        h.value = 3.0f;
        Field f = valueField();
        Object boxed = f.get(h);
        if (!(boxed instanceof Float)) {
            throw new RuntimeException("expected Float but got " + boxed);
        }
        if (((Float) boxed).floatValue() != f.getFloat(h)) {
            throw new RuntimeException("get() and getFloat() disagree");
        }
    }

    static void testGetFloatWithWrongObjectTypeThrows() throws Exception {
        Field f = valueField();
        try {
            f.getFloat(new Object());
            throw new RuntimeException("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected: object is not an instance of the declaring class
        }
    }
}
