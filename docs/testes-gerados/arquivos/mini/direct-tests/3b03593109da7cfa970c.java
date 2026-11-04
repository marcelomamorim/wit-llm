/*
 * @test
 * @summary Test jdk.internal.org.objectweb.asm.util.Textifier.visitAnnotation behavior
 * @run main TextifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Textifier;
import java.util.List;

public class TextifierWitupTest {

    public static void main(String[] args) throws Exception {
        testVisitAnnotationWithNameAndDesc();
        testVisitAnnotationWithNullName();
    }

    private static void testVisitAnnotationWithNameAndDesc() {
        Textifier textifier = new Textifier();
        Textifier returned = textifier.visitAnnotation("testName", "Ljava/lang/String;");

        // The returned object is a new Textifier instance, not the same
        if (returned == textifier) {
            throw new AssertionError("visitAnnotation should return a new Textifier instance");
        }

        // The original text list should contain 3 entries after visitAnnotation
        List<String> text = textifier.getText();
        if (text.size() != 3) {
            throw new AssertionError("Expected 3 text entries, got " + text.size());
        }

        // The first entry should start with a number and contain 'testName=' and '@'
        String first = text.get(0);
        if (!first.contains("testName=") || !first.contains("@")) {
            throw new AssertionError("First text entry does not contain expected name and '@': " + first);
        }

        // The last entry should be a closing parenthesis ")"
        String last = text.get(2);
        if (!")".equals(last)) {
            throw new AssertionError("Last text entry should be ')', got: " + last);
        }

        // The second entry should be the text of the returned Textifier (a List<String>)
        List<String> nestedText = returned.getText();
        if (nestedText == null) {
            throw new AssertionError("Returned Textifier's text should not be null");
        }
    }

    private static void testVisitAnnotationWithNullName() {
        Textifier textifier = new Textifier();
        Textifier returned = textifier.visitAnnotation(null, "I");

        // The returned object is a new Textifier instance, not the same
        if (returned == textifier) {
            throw new AssertionError("visitAnnotation should return a new Textifier instance");
        }

        // The original text list should contain 3 entries after visitAnnotation
        List<String> text = textifier.getText();
        if (text.size() != 3) {
            throw new AssertionError("Expected 3 text entries, got " + text.size());
        }

        // The first entry should start with a number and contain '@' but not '='
        String first = text.get(0);
        if (first.contains("=")) {
            throw new AssertionError("First text entry should not contain '=', got: " + first);
        }
        if (!first.contains("@")) {
            throw new AssertionError("First text entry should contain '@', got: " + first);
        }

        // The last entry should be a closing parenthesis ")"
        String last = text.get(2);
        if (!")".equals(last)) {
            throw new AssertionError("Last text entry should be ')', got: " + last);
        }

        // The second entry should be the text of the returned Textifier (a List<String>)
        List<String> nestedText = returned.getText();
        if (nestedText == null) {
            throw new AssertionError("Returned Textifier's text should not be null");
        }
    }
}
