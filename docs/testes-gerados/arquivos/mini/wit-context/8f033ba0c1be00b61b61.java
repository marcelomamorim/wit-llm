/*
 * @test
 * @summary Test java.lang.invoke.SerializedLambda.toString() method behavior and exception paths
 * @run main SerializedLambdaWitupTest
 */

import java.lang.invoke.SerializedLambda;
import java.lang.invoke.MethodHandleInfo;

public class SerializedLambdaWitupTest {
    public static void main(String[] args) {
        testToStringValid();
        testToStringInvalidImplMethodKind();
    }

    private static void testToStringValid() {
        // Use a valid implMethodKind within the allowed range
        int validKind = MethodHandleInfo.REF_invokeVirtual; // known valid kind (5)
        SerializedLambda sl = new SerializedLambda(
            SerializedLambda.class,
            "java/lang/Runnable",
            "run",
            "()V",
            validKind,
            "java/lang/String",
            "length",
            "()I",
            "()I",
            new Object[0]
        );
        String result = sl.toString();
        if (!result.contains("capturingClass=class java.lang.invoke.SerializedLambda")) {
            throw new AssertionError("toString output missing capturingClass");
        }
        if (!result.contains("functionalInterfaceMethod=java/lang/Runnable.run:()V")) {
            throw new AssertionError("toString output missing functionalInterfaceMethod");
        }
        if (!result.contains("implementation=invokeVirtual")) {
            throw new AssertionError("toString output missing implementation kind string");
        }
        if (!result.contains("instantiatedMethodType=()I")) {
            throw new AssertionError("toString output missing instantiatedMethodType");
        }
        if (!result.contains("numCaptured=0")) {
            throw new AssertionError("toString output missing numCaptured");
        }
    }

    private static void testToStringInvalidImplMethodKind() {
        // The toString method calls MethodHandleInfo.referenceKindToString(implMethodKind)
        // which throws IllegalArgumentException if implMethodKind is invalid
        // We test with an invalid implMethodKind (e.g. 0 or negative or >= REF_LIMIT)

        int[] invalidKinds = {0, -1, MethodHandleInfo.REF_LIMIT, MethodHandleInfo.REF_LIMIT + 1, 100};
        for (int invalidKind : invalidKinds) {
            try {
                SerializedLambda sl = new SerializedLambda(
                    SerializedLambda.class,
                    "java/lang/Runnable",
                    "run",
                    "()V",
                    invalidKind,
                    "java/lang/String",
                    "length",
                    "()I",
                    "()I",
                    new Object[0]
                );
                // toString should throw IllegalArgumentException for invalid implMethodKind
                sl.toString();
                throw new AssertionError("Expected IllegalArgumentException for implMethodKind=" + invalidKind);
            } catch (IllegalArgumentException iae) {
                // Expected exception
                String msg = iae.getMessage();
                if (msg == null || !msg.contains("invalid reference kind")) {
                    throw new AssertionError("IllegalArgumentException message unexpected: " + msg);
                }
            } catch (Exception e) {
                throw new AssertionError("Unexpected exception type " + e.getClass() + " for implMethodKind=" + invalidKind);
            }
        }
    }
}
