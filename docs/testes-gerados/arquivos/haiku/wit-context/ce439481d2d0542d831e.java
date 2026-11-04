/*
 * @test
 * @summary Witup-generated checks for MethodHandleInfo.toString null guards and formatting
 * @run main MethodHandleInfoWitupTest
 */

import java.lang.invoke.MethodHandleInfo;
import java.lang.invoke.MethodType;

public class MethodHandleInfoWitupTest {

    private interface Action {
        void run();
    }

    public static void main(String[] args) {
        String s = MethodHandleInfo.toString(MethodHandleInfo.REF_invokeStatic,
                String.class, "valueOf", MethodType.methodType(String.class, Object.class));
        if (!"invokeStatic java.lang.String.valueOf:(Object)String".equals(s)) {
            throw new RuntimeException("unexpected result: " + s);
        }

        expectNpe(() -> MethodHandleInfo.toString(MethodHandleInfo.REF_invokeStatic,
                String.class, null, MethodType.methodType(String.class)));

        expectNpe(() -> MethodHandleInfo.toString(MethodHandleInfo.REF_invokeStatic,
                String.class, "valueOf", null));

        expectNpe(() -> MethodHandleInfo.toString(MethodHandleInfo.REF_invokeStatic,
                String.class, null, null));
    }

    private static void expectNpe(Action action) {
        try {
            action.run();
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("NullPointerException expected");
    }
}
