/*
 * @test
 * @summary Checks MethodHandleInfo.toString(int, Class, String, MethodType) formatting and null handling
 * @run main MethodHandleInfoWitupTest
 */

import java.lang.invoke.MethodHandleInfo;
import java.lang.invoke.MethodType;

public class MethodHandleInfoWitupTest {

    public static void main(String[] args) throws Exception {
        String s1 = MethodHandleInfo.toString(MethodHandleInfo.REF_invokeVirtual,
                String.class, "length", MethodType.methodType(int.class));
        check("invokeVirtual java.lang.String.length:()int".equals(s1), s1);

        String s2 = MethodHandleInfo.toString(MethodHandleInfo.REF_invokeStatic,
                Integer.class, "parseInt", MethodType.methodType(int.class, String.class));
        check("invokeStatic java.lang.Integer.parseInt:(String)int".equals(s2), s2);

        String s3 = MethodHandleInfo.toString(MethodHandleInfo.REF_newInvokeSpecial,
                String.class, "<init>", MethodType.methodType(void.class));
        check("newInvokeSpecial java.lang.String.<init>:()void".equals(s3), s3);

        expectNpe(() -> MethodHandleInfo.toString(MethodHandleInfo.REF_invokeVirtual,
                String.class, null, MethodType.methodType(int.class)));
        expectNpe(() -> MethodHandleInfo.toString(MethodHandleInfo.REF_invokeVirtual,
                String.class, "length", null));
    }

    interface Action {
        void run() throws Exception;
    }

    static void expectNpe(Action a) throws Exception {
        try {
            a.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("NullPointerException expected");
    }

    static void check(boolean ok, String actual) {
        if (!ok) {
            throw new RuntimeException("Unexpected value: " + actual);
        }
    }
}
