/*
 * @test
 * @summary Witup-generated check that ClassFileAssembler.cpi() throws before any constant pool entry and returns the index after emission
 * @modules java.base/jdk.internal.reflect
 * @run main/othervm --add-opens java.base/jdk.internal.reflect=ALL-UNNAMED ClassFileAssemblerWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassFileAssemblerWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("jdk.internal.reflect.ClassFileAssembler");
        Constructor<?> ctor = c.getDeclaredConstructor();
        ctor.setAccessible(true);
        Object asm = ctor.newInstance();

        Method cpi = c.getMethod("cpi");
        cpi.setAccessible(true);

        try {
            cpi.invoke(asm);
            throw new AssertionError("cpi() should throw before any constant pool entry");
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof RuntimeException)) {
                throw new AssertionError("unexpected cause: " + cause);
            }
            if (!"Illegal use of ClassFileAssembler".equals(cause.getMessage())) {
                throw new AssertionError("unexpected message: " + cause.getMessage());
            }
        }

        Method emitUtf8 = c.getMethod("emitConstantPoolUTF8", String.class);
        emitUtf8.invoke(asm, "a");
        Object idx1 = cpi.invoke(asm);
        if (!Short.valueOf((short) 1).equals(idx1)) {
            throw new AssertionError("expected cpi()=1 after one entry, got " + idx1);
        }

        emitUtf8.invoke(asm, "b");
        Object idx2 = cpi.invoke(asm);
        if (!Short.valueOf((short) 2).equals(idx2)) {
            throw new AssertionError("expected cpi()=2 after two entries, got " + idx2);
        }
    }
}
