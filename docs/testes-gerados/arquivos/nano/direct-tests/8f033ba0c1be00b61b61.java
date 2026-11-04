/*
 * @test
 * @summary Test SerializedLambda.toString() method for correctness.
 * @run main SerializedLambdaWitupTest
 */

import java.lang.invoke.SerializedLambda;

public class SerializedLambdaWitupTest {
    public static void main(String[] args) {
        testToStringOutput();
    }

    public static void testToStringOutput() {
        SerializedLambda lambda = createSampleSerializedLambda();
        String result = lambda.toString();
        if (result == null || !result.contains("SerializedLambda")) {
            throw new AssertionError("toString() output is invalid: " + result);
        }
        System.out.println("toString() output: " + result);
    }

    private static SerializedLambda createSampleSerializedLambda() {
        // Create a dummy SerializedLambda with minimal required fields for testing
        return new SerializedLambda(
            Object.class,
            "java/lang/Runnable",
            "run",
            "()V",
            0,
            "java/lang/Object",
            "run",
            "()V",
            "()V",
            new Object[0]
        );
    }
}