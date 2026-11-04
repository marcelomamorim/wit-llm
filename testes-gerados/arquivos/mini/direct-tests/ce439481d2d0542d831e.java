/*
 * @test
 * @summary Test MethodHandleInfo.toString method behavior
 * @run main MethodHandleInfoWitupTest
 */

import java.lang.invoke.MethodHandleInfo;
import java.lang.invoke.MethodType;
import java.util.Objects;

public class MethodHandleInfoWitupTest {
    public static void main(String[] args) {
        testToStringBasic();
        testToStringNullName();
        testToStringNullType();
    }

    private static void testToStringBasic() {
        int kind = 1; // arbitrary valid kind
        Class<?> defc = String.class;
        String name = "substring";
        MethodType type = MethodType.methodType(String.class, int.class, int.class);

        String result = MethodHandleInfo.toString(kind, defc, name, type);

        // Expected format: "referenceKindToString(kind) defc.getName().name:type"
        // We cannot call referenceKindToString (not public), so check known parts
        if (!result.contains(defc.getName()) || !result.contains(name) || !result.contains(type.toString())) {
            throw new AssertionError("toString result missing expected parts: " + result);
        }
        if (!result.startsWith("REF_")) {
            throw new AssertionError("toString result does not start with REF_: " + result);
        }
    }

    private static void testToStringNullName() {
        int kind = 1;
        Class<?> defc = String.class;
        MethodType type = MethodType.methodType(String.class, int.class);

        try {
            MethodHandleInfo.toString(kind, defc, null, type);
            throw new AssertionError("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testToStringNullType() {
        int kind = 1;
        Class<?> defc = String.class;
        String name = "length";

        try {
            MethodHandleInfo.toString(kind, defc, name, null);
            throw new AssertionError("Expected NullPointerException for null type");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
