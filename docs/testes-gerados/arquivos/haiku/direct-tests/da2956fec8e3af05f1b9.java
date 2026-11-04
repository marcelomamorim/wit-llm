/*
 * @test
 * @summary Tests MethodHandleInfo.referenceKindToString for valid and invalid reference kinds
 * @run main MethodHandleInfoWitupTest
 */

import java.lang.invoke.MethodHandleInfo;

public class MethodHandleInfoWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testValidKinds() {
        String[] expected = {
            null,
            "getField",
            "getStatic",
            "putField",
            "putStatic",
            "invokeVirtual",
            "invokeStatic",
            "invokeSpecial",
            "newInvokeSpecial",
            "invokeInterface"
        };
        for (int kind = 1; kind < expected.length; kind++) {
            String name = MethodHandleInfo.referenceKindToString(kind);
            check(name != null && !name.isEmpty(), "empty name for kind " + kind);
            check(name.equals(expected[kind]),
                  "unexpected name for kind " + kind + ": " + name);
        }
    }

    static void testValidNamesAreDistinct() {
        java.util.Set<String> seen = new java.util.HashSet<>();
        for (int kind = 1; kind <= 9; kind++) {
            String name = MethodHandleInfo.referenceKindToString(kind);
            check(seen.add(name), "duplicate name: " + name);
        }
    }

    static void expectIllegalArgument(int kind) {
        try {
            String result = MethodHandleInfo.referenceKindToString(kind);
            throw new RuntimeException("no exception for kind " + kind + ": " + result);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testInvalidKinds() {
        expectIllegalArgument(0);
        expectIllegalArgument(-1);
        expectIllegalArgument(10);
        expectIllegalArgument(Integer.MAX_VALUE);
        expectIllegalArgument(Integer.MIN_VALUE);
    }

    static void testOutOfByteRangeKindIsRejected() {
        // 257 truncates to 1 if cast to byte; the public method must still reject it
        expectIllegalArgument(257);
        expectIllegalArgument(256 + 5);
    }

    public static void main(String[] args) {
        testValidKinds();
        testValidNamesAreDistinct();
        testInvalidKinds();
        testOutOfByteRangeKindIsRejected();
    }
}
