/*
 * @test
 * @summary Witup-derived tests for SerializedLambda.toString formatting and invalid reference kind handling
 * @run main SerializedLambdaWitupTest
 */
import java.lang.invoke.SerializedLambda;

public class SerializedLambdaWitupTest {

    public static void main(String[] args) {
        testValidKindFormatting();
        testNoCapturedArgs();
        expectIllegalArgument(0);
        expectIllegalArgument(10);
    }

    static SerializedLambda make(int kind, Object[] captured) {
        return new SerializedLambda(SerializedLambdaWitupTest.class,
                "java/lang/Runnable", "run", "()V",
                kind,
                "SerializedLambdaWitupTest", "lambda$main$0", "()V",
                "()V",
                captured);
    }

    static void testValidKindFormatting() {
        SerializedLambda l = make(6, new Object[] {"a", Integer.valueOf(1)});
        String expected = "SerializedLambda[capturingClass=class SerializedLambdaWitupTest, "
                + "functionalInterfaceMethod=java/lang/Runnable.run:()V, "
                + "implementation=invokeStatic SerializedLambdaWitupTest.lambda$main$0:()V, "
                + "instantiatedMethodType=()V, numCaptured=2]";
        check(expected, l.toString());
    }

    static void testNoCapturedArgs() {
        SerializedLambda l = make(6, new Object[0]);
        String s = l.toString();
        check(true, s.endsWith("instantiatedMethodType=()V, numCaptured=0]"));
    }

    static void expectIllegalArgument(int kind) {
        try {
            make(kind, new Object[0]).toString();
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for kind " + kind);
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected: " + expected + " but was: " + actual);
        }
    }

    static void check(boolean expected, boolean actual) {
        if (expected != actual) {
            throw new RuntimeException("Assertion failed");
        }
    }
}
