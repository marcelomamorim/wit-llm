/*
 * @test
 * @summary Test SerializedLambda.toString() method for correct formatting and content
 * @run main SerializedLambdaWitupTest
 */

import java.lang.invoke.SerializedLambda;
import java.lang.invoke.MethodHandleInfo;

public class SerializedLambdaWitupTest {
    public static void main(String[] args) {
        // Prepare test data
        Class<?> capturingClass = SerializedLambdaWitupTest.class;
        String functionalInterfaceClass = "java/lang/Runnable";
        String functionalInterfaceMethodName = "run";
        String functionalInterfaceMethodSignature = "()V";
        int implMethodKind = MethodHandleInfo.REF_invokeVirtual;
        String implClass = "java/lang/String";
        String implMethodName = "length";
        String implMethodSignature = "()I";
        String instantiatedMethodType = "()I";
        Object[] capturedArgs = new Object[] {"arg1", 42};

        SerializedLambda sl = new SerializedLambda(
            capturingClass,
            functionalInterfaceClass,
            functionalInterfaceMethodName,
            functionalInterfaceMethodSignature,
            implMethodKind,
            implClass,
            implMethodName,
            implMethodSignature,
            instantiatedMethodType,
            capturedArgs
        );

        String result = sl.toString();

        // Check that the string contains expected substrings
        if (!result.contains("capturingClass=" + capturingClass)) {
            throw new AssertionError("toString missing capturingClass");
        }
        if (!result.contains("functionalInterfaceMethod=" + functionalInterfaceClass + "." + functionalInterfaceMethodName + ":" + functionalInterfaceMethodSignature)) {
            throw new AssertionError("toString missing functionalInterfaceMethod");
        }
        String implKindStr = MethodHandleInfo.referenceKindToString(implMethodKind);
        if (!result.contains("implementation=" + implKindStr + " " + implClass + "." + implMethodName + ":" + implMethodSignature)) {
            throw new AssertionError("toString missing implementation");
        }
        if (!result.contains("instantiatedMethodType=" + instantiatedMethodType)) {
            throw new AssertionError("toString missing instantiatedMethodType");
        }
        if (!result.contains("numCaptured=" + capturedArgs.length)) {
            throw new AssertionError("toString missing numCaptured");
        }

        // Check that the format matches the expected pattern
        String expectedPrefix = "SerializedLambda[";
        if (!result.startsWith(expectedPrefix)) {
            throw new AssertionError("toString does not start with 'SerializedLambda['");
        }
        if (!result.endsWith("]")) {
            throw new AssertionError("toString does not end with ']'");
        }

        System.out.println("PASS");
    }
}
