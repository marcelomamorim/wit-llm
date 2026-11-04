/*
 * @test
 * @summary Test visitRequire method of Textifier for correct output formatting and flags
 * @run main TextifierWitupTest
 */

import java.util.List;
import java.util.ArrayList;
import jdk.internal.org.objectweb.asm.util.Textifier;
import jdk.internal.org.objectweb.asm.Opcodes;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitRequireNullVersion();
        testVisitRequireWithFlags();
        testVisitRequireNoFlags();
    }

    private static void testVisitRequireNullVersion() {
        Textifier t = new Textifier();
        t.visitRequire("java.base", 0, null);
        List<String> text = t.getText();
        if (text.isEmpty()) {
            throw new AssertionError("Text list should not be empty");
        }
        String output = text.get(text.size() - 1);
        if (!output.contains("requires java.base")) {
            throw new AssertionError("Output should contain module name 'java.base'");
        }
        if (output.contains("version")) {
            throw new AssertionError("Output should not contain version comment when version is null");
        }
        if (!output.contains("access flags 0x0")) {
            throw new AssertionError("Output should contain access flags 0x0");
        }
    }

    private static void testVisitRequireWithFlags() {
        Textifier t = new Textifier();
        int access = Opcodes.ACC_TRANSITIVE | Opcodes.ACC_STATIC_PHASE;
        t.visitRequire("java.logging", access, "1.0");
        List<String> text = t.getText();
        if (text.isEmpty()) {
            throw new AssertionError("Text list should not be empty");
        }
        String output = text.get(text.size() - 1);
        if (!output.contains("requires java.logging")) {
            throw new AssertionError("Output should contain module name 'java.logging'");
        }
        if (!output.contains("transitive ")) {
            throw new AssertionError("Output should contain 'transitive '");
        }
        if (!output.contains("static ")) {
            throw new AssertionError("Output should contain 'static '");
        }
        if (!output.contains("version 1.0")) {
            throw new AssertionError("Output should contain version '1.0'");
        }
        if (!output.contains("access flags 0x")) {
            throw new AssertionError("Output should contain access flags hex");
        }
    }

    private static void testVisitRequireNoFlags() {
        Textifier t = new Textifier();
        t.visitRequire("java.sql", 0, "2.0");
        List<String> text = t.getText();
        if (text.isEmpty()) {
            throw new AssertionError("Text list should not be empty");
        }
        String output = text.get(text.size() - 1);
        if (!output.contains("requires java.sql")) {
            throw new AssertionError("Output should contain module name 'java.sql'");
        }
        if (output.contains("transitive ")) {
            throw new AssertionError("Output should not contain 'transitive '");
        }
        if (output.contains("static ")) {
            throw new AssertionError("Output should not contain 'static '");
        }
        if (!output.contains("version 2.0")) {
            throw new AssertionError("Output should contain version '2.0'");
        }
    }
}
