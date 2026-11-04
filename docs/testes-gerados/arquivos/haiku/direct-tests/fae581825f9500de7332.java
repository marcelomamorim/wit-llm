/*
 * @test
 * @summary Verifies ClassFileAssembler.cpi() throws before any constant pool entry and returns the current one-based index afterwards
 * @modules java.base/jdk.internal.reflect
 * @run main/othervm --add-opens java.base/jdk.internal.reflect=ALL-UNNAMED ClassFileAssemblerWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassFileAssemblerWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("jdk.internal.reflect.ClassFileAssembler");

        Constructor<?> ctor = cls.getDeclaredConstructor();
        ctor.setAccessible(true);
        Object asm = ctor.newInstance();

        Method cpi = cls.getMethod("cpi");
        cpi.setAccessible(true);

        Method emitUtf8 = cls.getMethod("emitConstantPoolUTF8", String.class);
        emitUtf8.setAccessible(true);

        // Before any constant pool entry, cpi() must fail.
        try {
            cpi.invoke(asm);
            throw new RuntimeException("cpi() should have thrown before any constant pool entry");
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            check(cause instanceof RuntimeException, "unexpected cause type: " + cause);
            check("Illegal use of ClassFileAssembler".equals(cause.getMessage()),
                  "unexpected message: " + cause.getMessage());
        }

        // After one constant pool entry, the index is 1.
        emitUtf8.invoke(asm, "a");
        Object first = cpi.invoke(asm);
        check(first instanceof Short, "cpi() should return short");
        check(((Short) first).shortValue() == 1, "expected cpi()==1 but was " + first);

        // After a second entry, the index is 2.
        emitUtf8.invoke(asm, "bb");
        Object second = cpi.invoke(asm);
        check(((Short) second).shortValue() == 2, "expected cpi()==2 but was " + second);

        // Repeated calls without emitting must not change the index.
        Object again = cpi.invoke(asm);
        check(((Short) again).shortValue() == 2, "cpi() changed without emission: " + again);
    }
}
