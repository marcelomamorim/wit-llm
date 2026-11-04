/*
 * @test
 * @summary Tests for MethodHandleInfo class methods
 * @run main MethodHandleInfoWitupTest
 */

import java.lang.invoke.MethodHandleInfo;
import java.lang.invoke.MethodType;
import java.util.Objects;

public class MethodHandleInfoWitupTest {

    public static void main(String[] args) {
        testToString_NullName();
        testToString_NullType();
    }

    public static void testToString_NullName() {
        int kind = 1; // arbitrary valid kind
        Class<?> defc = String.class;
        String name = null;
        MethodType type = MethodType.methodType(void.class);
        try {
            MethodHandleInfo.toString(kind, defc, name, type);
            throw new AssertionError("Expected NullPointerException for null name");
        } catch (NullPointerException e) {
            // expected
        }
    }

    public static void testToString_NullType() {
        int kind = 1; // arbitrary valid kind
        Class<?> defc = String.class;
        String name = "testMethod";
        MethodType type = null;
        try {
            MethodHandleInfo.toString(kind, defc, name, type);
            throw new AssertionError("Expected NullPointerException for null type");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
