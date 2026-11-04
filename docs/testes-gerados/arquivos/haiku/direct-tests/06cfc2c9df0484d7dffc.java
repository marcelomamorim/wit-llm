/*
 * @test
 * @summary Verifies ConstantBootstraps.nullConstant returns null for reference types and rejects primitive types
 * @run main ConstantBootstrapsWitupTest
 */

import java.lang.invoke.ConstantBootstraps;
import java.lang.invoke.MethodHandles;

public class ConstantBootstrapsWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        Object s = ConstantBootstraps.nullConstant(lookup, "n", String.class);
        check(s == null, "String.class should yield null");

        Object o = ConstantBootstraps.nullConstant(lookup, "n", Object.class);
        check(o == null, "Object.class should yield null");

        Object arr = ConstantBootstraps.nullConstant(lookup, "n", int[].class);
        check(arr == null, "array reference type should yield null");

        Object nullLookup = ConstantBootstraps.nullConstant(null, "n", String.class);
        check(nullLookup == null, "unused lookup argument should not affect null result");

        Object nullName = ConstantBootstraps.nullConstant(lookup, null, String.class);
        check(nullName == null, "unused name argument should not affect null result");

        boolean iae = false;
        try {
            ConstantBootstraps.nullConstant(lookup, "n", int.class);
        } catch (IllegalArgumentException e) {
            iae = true;
        }
        check(iae, "primitive type int.class should throw IllegalArgumentException");

        boolean iaeVoid = false;
        try {
            ConstantBootstraps.nullConstant(lookup, "n", double.class);
        } catch (IllegalArgumentException e) {
            iaeVoid = true;
        }
        check(iaeVoid, "primitive type double.class should throw IllegalArgumentException");

        boolean npe = false;
        try {
            ConstantBootstraps.nullConstant(lookup, "n", null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null type should throw NullPointerException");
    }
}
