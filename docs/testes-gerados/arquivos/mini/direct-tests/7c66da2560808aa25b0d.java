/*
 * @test
 * @summary Test visitIincInsn method of Textifier for correct output formatting
 * @run main TextifierWitupTest
 */

import java.util.List;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();

        // Test with zero var and increment
        textifier.visitIincInsn(0, 0);
        checkLastText(textifier, "  IINC 0 0\n");

        // Test with positive var and increment
        textifier.visitIincInsn(5, 10);
        checkLastText(textifier, "  IINC 5 10\n");

        // Test with negative var and increment (allowed by signature, no exception expected)
        textifier.visitIincInsn(-1, -20);
        checkLastText(textifier, "  IINC -1 -20\n");

        // Test with large values
        textifier.visitIincInsn(Integer.MAX_VALUE, Integer.MIN_VALUE);
        checkLastText(textifier, "  IINC 2147483647 -2147483648\n");
    }

    private static void checkLastText(Textifier textifier, String expected) {
        // The text field is protected in Printer, but Textifier inherits it.
        // We access it via reflection because it is not public.
        try {
            java.lang.reflect.Field textField = Textifier.class.getSuperclass().getDeclaredField("text");
            textField.setAccessible(true);
            @SuppressWarnings("unchecked")
            java.util.List<String> text = (List<String>) textField.get(textifier);
            if (text.isEmpty()) {
                throw new AssertionError("Text list is empty");
            }
            String last = text.get(text.size() - 1);
            if (!last.equals(expected)) {
                throw new AssertionError("Expected: '" + expected + "' but got: '" + last + "'");
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new AssertionError("Reflection failed: " + e);
        }
    }
}
