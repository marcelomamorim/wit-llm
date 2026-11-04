/*
 * @test
 * @summary Field.getChar on an int field must throw IllegalArgumentException through the qualified static integer accessor
 * @run main UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest {
    static volatile int vField = 42;
    static int plainField = 7;
    volatile int instVField = 5;

    public static void main(String[] args) throws Exception {
        Field v = UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest.class.getDeclaredField("vField");
        if (v.getInt(null) != 42) {
            throw new AssertionError("getInt on static volatile int changed");
        }
        try {
            v.getChar(null);
            throw new AssertionError("expected IllegalArgumentException from getChar");
        } catch (IllegalArgumentException expected) {
        }

        Field p = UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest.class.getDeclaredField("plainField");
        try {
            p.getChar(null);
            throw new AssertionError("expected IllegalArgumentException from getChar on plain int");
        } catch (IllegalArgumentException expected) {
        }

        Field i = UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest.class.getDeclaredField("instVField");
        UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest obj = new UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest();
        try {
            i.getChar(obj);
            throw new AssertionError("expected IllegalArgumentException from getChar on instance field");
        } catch (IllegalArgumentException expected) {
        }
    }
}
