/*
 * @test
 * @summary Verifies the format produced by SerializedLambda.toString() and the numCaptured value
 * @run main SerializedLambdaWitupTest
 */
import java.lang.invoke.MethodHandleInfo;
import java.lang.invoke.SerializedLambda;

public class SerializedLambdaWitupTest {
    public static void main(String[] args) {
        String cap = String.valueOf(SerializedLambdaWitupTest.class);

        SerializedLambda s1 = new SerializedLambda(SerializedLambdaWitupTest.class,
                "java/util/function/Supplier", "get", "()Ljava/lang/Object;",
                MethodHandleInfo.REF_invokeStatic,
                "SerializedLambdaWitupTest", "run", "()V",
                "()Ljava/lang/Object;",
                new Object[]{"a", Integer.valueOf(1)});
        check(s1.toString(), expected(cap, "java/util/function/Supplier", "get", "()Ljava/lang/Object;",
                MethodHandleInfo.REF_invokeStatic, "SerializedLambdaWitupTest", "run", "()V",
                "()Ljava/lang/Object;", 2));

        SerializedLambda s2 = new SerializedLambda(null,
                "java/lang/Runnable", "run", "()V",
                MethodHandleInfo.REF_invokeVirtual,
                "java/lang/String", "length", "()I",
                "()I",
                new Object[0]);
        check(s2.toString(), expected("null", "java/lang/Runnable", "run", "()V",
                MethodHandleInfo.REF_invokeVirtual, "java/lang/String", "length", "()I",
                "()I", 0));

        SerializedLambda s3 = new SerializedLambda(SerializedLambdaWitupTest.class,
                "java/util/function/Function", "apply", "(Ljava/lang/Object;)Ljava/lang/Object;",
                MethodHandleInfo.REF_invokeStatic,
                "SerializedLambdaWitupTest", "lambda$0", "(Ljava/lang/Object;)Ljava/lang/Object;",
                "(Ljava/lang/Object;)Ljava/lang/Object;",
                new Object[]{null, null, null});
        check(s3.toString(), expected(cap, "java/util/function/Function", "apply",
                "(Ljava/lang/Object;)Ljava/lang/Object;", MethodHandleInfo.REF_invokeStatic,
                "SerializedLambdaWitupTest", "lambda$0", "(Ljava/lang/Object;)Ljava/lang/Object;",
                "(Ljava/lang/Object;)Ljava/lang/Object;", 3));

        String first = s1.toString();
        String second = s1.toString();
        if (!first.equals(second)) {
            throw new RuntimeException("toString() not deterministic: " + first + " vs " + second);
        }
    }

    static String expected(String cap, String fic, String fin, String fis, int kind,
                           String ic, String in, String is, String imt, int n) {
        return "SerializedLambda[capturingClass=" + cap
                + ", functionalInterfaceMethod=" + fic + "." + fin + ":" + fis
                + ", implementation=" + MethodHandleInfo.referenceKindToString(kind)
                + " " + ic + "." + in + ":" + is
                + ", instantiatedMethodType=" + imt
                + ", numCaptured=" + n + "]";
    }

    static void check(String actual, String exp) {
        if (!exp.equals(actual)) {
            throw new RuntimeException("Expected: " + exp + " but was: " + actual);
        }
    }
}
