/* @test    @summary Test visitLabel method of Textifier class    @run main TextifierWitupTest */

import java.util.List;
import java.util.ArrayList;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitLabelWithValidLabel();
        testVisitLabelWithNullLabel();
    }

    public static void testVisitLabelWithValidLabel() {
        Textifier textifier = new Textifier();
        Label label = new Label();
        List<String> text = new ArrayList<>();
        // Use reflection to access the protected method
        try {
            java.lang.reflect.Method method = Textifier.class.getDeclaredMethod("visitLabel", Label.class);
            method.setAccessible(true);
            method.invoke(textifier, label);
            // Since buf and text are private, verify that text contains expected string
            boolean found = false;
            for (String line : textifier.text) {
                if (line.contains("<")) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new AssertionError("visitLabel did not add expected label text.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Reflection failed in testVisitLabelWithValidLabel", e);
        }
    }

    public static void testVisitLabelWithNullLabel() {
        Textifier textifier = new Textifier();
        try {
            java.lang.reflect.Method method = Textifier.class.getDeclaredMethod("visitLabel", Label.class);
            method.setAccessible(true);
            method.invoke(textifier, (Object) null);
            // Expect no exception, verify that text list remains unchanged or empty
            if (!textifier.text.isEmpty()) {
                throw new AssertionError("visitLabel with null label should not add text.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Reflection failed in testVisitLabelWithNullLabel", e);
        }
    }
}
