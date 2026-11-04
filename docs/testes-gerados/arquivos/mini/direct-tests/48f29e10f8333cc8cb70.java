/*
 * @test
 * @summary Test Textifier.visitAnnotation method behavior
 * @run main TextifierWitupTest
 */

import java.util.List;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitAnnotationVisible();
        testVisitAnnotationInvisible();
    }

    private static void testVisitAnnotationVisible() {
        Textifier textifier = new Textifier();
        String desc = "Ljava/lang/Deprecated;";
        boolean visible = true;

        Textifier result = textifier.visitAnnotation(desc, visible);

        // The returned Textifier should not be null
        if (result == null) {
            throw new AssertionError("visitAnnotation returned null for visible annotation");
        }

        // The original textifier's text list should contain the annotation line with visible marker
        boolean foundVisible = false;
        List<String> texts = textifier.getText();
        for (String s : texts) {
            if (s.contains("@") && s.contains(desc) && s.endsWith(")\n")) {
                foundVisible = true;
                break;
            }
        }
        if (!foundVisible) {
            throw new AssertionError("Text does not contain visible annotation line");
        }
    }

    private static void testVisitAnnotationInvisible() {
        Textifier textifier = new Textifier();
        String desc = "Ljava/lang/SuppressWarnings;";
        boolean visible = false;

        Textifier result = textifier.visitAnnotation(desc, visible);

        if (result == null) {
            throw new AssertionError("visitAnnotation returned null for invisible annotation");
        }

        // The original textifier's text list should contain the annotation line with invisible marker
        boolean foundInvisible = false;
        for (String s : textifier.getText()) {
            if (s.contains("@") && s.contains(desc) && s.endsWith(") // invisible\n")) {
                foundInvisible = true;
                break;
            }
        }
        if (!foundInvisible) {
            throw new AssertionError("Text does not contain invisible annotation line");
        }
    }
}
