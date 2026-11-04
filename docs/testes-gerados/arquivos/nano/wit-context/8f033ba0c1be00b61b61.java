/* @test    @summary Test SerializedLambda.toString() behavior    @run main SerializedLambdaWitupTest */

import java.lang.invoke.SerializedLambda;

public class SerializedLambdaWitupTest {

    public static void main(String[] args) {
        testToStringWithValidData();
        testToStringWithInvalidReferenceKind();
    }

    private static void testToStringWithValidData() {
        try {
            // Create a SerializedLambda instance with valid data
            SerializedLambda lambda = new SerializedLambda(
                Object.class,
                "java/lang/Runnable",
                "run",
                "()V",
                0,
                "java/lang/Object",
                "run",
                "()V",
                "(Ljava/lang/Object;)V",
                new Object[] {}
            );
            String result = lambda.toString();
            if (result == null || !result.contains("SerializedLambda")) {
                throw new AssertionError("toString() returned unexpected result: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Exception during valid data test", e);
        }
    }

    private static void testToStringWithInvalidReferenceKind() {
        try {
            // Use reflection or a factory method if available to create an invalid implMethodKind
            // Since constructor is public, create an instance with invalid implMethodKind
            SerializedLambda lambda = new SerializedLambda(
                Object.class,
                "java/lang/Runnable",
                "run",
                "()V",
                999, // invalid implMethodKind
                "java/lang/Object",
                "run",
                "()V",
                "(Ljava/lang/Object;)V",
                new Object[] {}
            );
            String result = lambda.toString();
            if (result == null || !result.contains("SerializedLambda")) {
                throw new AssertionError("toString() returned unexpected result for invalid implMethodKind: " + result);
            }
        } catch (IllegalArgumentException e) {
            // Expected if constructor or method throws for invalid data
            // Acceptable, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception during invalid implMethodKind test", e);
        }
    }
}
