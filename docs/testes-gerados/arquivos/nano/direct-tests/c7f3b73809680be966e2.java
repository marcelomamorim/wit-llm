/* @test    @summary Test visitMainClass method of Textifier class    @run main TextifierWitupTest */

import java.util.List;
import java.util.ArrayList;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitMainClassWithValidInput();
        testVisitMainClassWithEmptyString();
    }

    public static void testVisitMainClassWithValidInput() {
        jdk.internal.org.objectweb.asm.util.Textifier textifier = new jdk.internal.org.objectweb.asm.util.Textifier();
        String className = "com/example/MyClass";
        textifier.visitMainClass(className);
        List<String> output = getTextifierOutput(textifier);
        boolean containsExpected = false;
        for (String line : output) {
            if (line.contains("// main class") && line.contains(className)) {
                containsExpected = true;
                break;
            }
        }
        if (!containsExpected) {
            throw new AssertionError("Expected output to contain main class comment with class name") ;
        }
    }

    public static void testVisitMainClassWithEmptyString() {
        jdk.internal.org.objectweb.asm.util.Textifier textifier = new jdk.internal.org.objectweb.asm.util.Textifier();
        String className = "";
        textifier.visitMainClass(className);
        List<String> output = getTextifierOutput(textifier);
        boolean containsEmpty = false;
        for (String line : output) {
            if (line.contains("// main class") && line.contains(className)) {
                containsEmpty = true;
                break;
            }
        }
        if (!containsEmpty) {
            throw new AssertionError("Expected output to contain main class comment with empty class name") ;
        }
    }

    private static List<String> getTextifierOutput(jdk.internal.org.objectweb.asm.util.Textifier textifier) {
        // Access the protected 'text' field via reflection, as no public getter is available
        try {
            java.lang.reflect.Field textField = jdk.internal.org.objectweb.asm.util.Textifier.class.getDeclaredField("text");
            textField.setAccessible(true);
            @SuppressWarnings("unchecked")
            List<String> output = (List<String>) textField.get(textifier);
            return new ArrayList<>(output);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Unable to access Textifier output", e);
        }
    }
}
