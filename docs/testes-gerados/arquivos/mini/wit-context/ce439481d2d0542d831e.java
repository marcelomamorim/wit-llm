/*
 * @test
 * @summary Test java.lang.invoke.MethodHandleInfo.toString method for null checks and output format
 * @run main MethodHandleInfoWitupTest
 */

import java.lang.invoke.MethodHandleInfo;
import java.lang.invoke.MethodType;
import java.util.Objects;

public class MethodHandleInfoWitupTest {
    public static void main(String[] args) {
        testToStringNormal();
        testToStringNullName();
        testToStringNullType();
    }

    private static void testToStringNormal() {
        int kind = 1; // arbitrary valid kind
        Class<?> defc = String.class;
        String name = "substring";
        MethodType type = MethodType.methodType(String.class, int.class, int.class);

        String result = MethodHandleInfo.toString(kind, defc, name, type);

        if (result == null || !result.contains(defc.getName()) || !result.contains(name) || !result.contains(type.toString())) {
            throw new AssertionError("toString output does not contain expected substrings: " + result);
        }
    }

    private static void testToStringNullName() {
        int kind = 1;
        Class<?> defc = String.class;
        String name = null;
        MethodType type = MethodType.methodType(String.class);

        try {
            MethodHandleInfo.toString(kind, defc, name, type);
            throw new AssertionError("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testToStringNullType() {
        int kind = 1;
        Class<?> defc = String.class;
        String name = "length";
        MethodType type = null;

        try {
            MethodHandleInfo.toString(kind, defc, name, type);
            throw new AssertionError("Expected NullPointerException for null type");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
