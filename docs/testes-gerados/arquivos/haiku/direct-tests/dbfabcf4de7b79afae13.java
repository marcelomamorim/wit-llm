/*
 * @test
 * @summary Verifies that Textifier.visitJumpInsn appends a textual jump instruction to its text buffer
 * @modules java.base/jdk.internal.org.objectweb.asm
 *          java.base/jdk.internal.org.objectweb.asm.util
 * @run main/othervm --add-opens java.base/jdk.internal.org.objectweb.asm.util=ALL-UNNAMED TextifierWitupTest
 */

import java.lang.reflect.Field;
import java.util.List;

import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    private static String lastText(Textifier t) throws Exception {
        Field f = Textifier.class.getDeclaredField("text");
        f.setAccessible(true);
        List<?> list = (List<?>) f.get(t);
        if (list.isEmpty()) {
            throw new AssertionError("text list is empty");
        }
        return String.valueOf(list.get(list.size() - 1));
    }

    private static int size(Textifier t) throws Exception {
        Field f = Textifier.class.getDeclaredField("text");
        f.setAccessible(true);
        return ((List<?>) f.get(t)).size();
    }

    private static void check(boolean cond, String msg) {
        if (!cond) {
            throw new AssertionError(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        testIfeqProducesOpcodeName();
        testGotoProducesOpcodeName();
        testEachCallAppendsOneEntry();
    }

    static void testIfeqProducesOpcodeName() throws Exception {
        Textifier t = new Textifier();
        Label label = new Label();
        t.visitJumpInsn(Opcodes.IFEQ, label);
        String s = lastText(t);
        check(s.contains("IFEQ"), "expected IFEQ in output, got: " + s);
        check(s.endsWith("\n"), "expected newline terminator, got: " + s);
    }

    static void testGotoProducesOpcodeName() throws Exception {
        Textifier t = new Textifier();
        Label label = new Label();
        t.visitJumpInsn(Opcodes.GOTO, label);
        String s = lastText(t);
        check(s.contains("GOTO"), "expected GOTO in output, got: " + s);
        check(!s.contains("IFEQ"), "unexpected IFEQ in output: " + s);
    }

    static void testEachCallAppendsOneEntry() throws Exception {
        Textifier t = new Textifier();
        int before = size(t);
        t.visitJumpInsn(Opcodes.IFNE, new Label());
        check(size(t) == before + 1, "expected exactly one appended entry");
        t.visitJumpInsn(Opcodes.IFEQ, new Label());
        check(size(t) == before + 2, "expected second entry to be appended");
        check(lastText(t).contains("IFEQ"), "last entry should be IFEQ");
    }
}
