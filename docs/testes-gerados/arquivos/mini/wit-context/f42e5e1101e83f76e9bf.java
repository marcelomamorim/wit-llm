/*
 * @test
 * @summary Test Textifier.visitOpen method for correct output and no exceptions
 * @run main TextifierWitupTest
 */

import java.util.List;
import java.util.ArrayList;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();

        // Test 1: Normal call with export and no modules
        textifier.visitOpen("com.example.package", 0x1, (String[]) null);
        List<String> text1 = textifier.getText();
        if (text1.isEmpty()) {
            throw new AssertionError("Text should not be empty after visitOpen");
        }
        String output1 = text1.get(text1.size() - 1);
        if (!output1.contains("opens com.example.package")) {
            throw new AssertionError("Output should contain the export string");
        }
        if (!output1.contains("0x1")) {
            throw new AssertionError("Output should contain the access flags in hex");
        }
        if (!output1.trim().endsWith(";  // access flags 0x1")) {
            // The exact formatting may vary but should end with semicolon and access flags
            throw new AssertionError("Output should end with ';' and access flags comment");
        }

        // Test 2: Call with export and empty modules array
        textifier.visitOpen("com.example.package", 0x2, new String[0]);
        List<String> text2 = textifier.getText();
        String output2 = text2.get(text2.size() - 1);
        if (!output2.contains("opens com.example.package")) {
            throw new AssertionError("Output should contain the export string");
        }
        if (!output2.contains("0x2")) {
            throw new AssertionError("Output should contain the access flags in hex");
        }
        if (!output2.trim().endsWith(";  // access flags 0x2")) {
            throw new AssertionError("Output should end with ';' and access flags comment");
        }

        // Test 3: Call with export and multiple modules
        String[] modules = new String[] {"mod1", "mod2"};
        textifier.visitOpen("com.example.package", 0x4, modules);
        List<String> text3 = textifier.getText();
        String output3 = text3.get(text3.size() - 1);
        if (!output3.contains("opens com.example.package to")) {
            throw new AssertionError("Output should contain 'opens <export> to' when modules present");
        }
        if (!output3.contains("mod1") || !output3.contains("mod2")) {
            throw new AssertionError("Output should list all modules");
        }
        if (!output3.contains("0x4")) {
            throw new AssertionError("Output should contain the access flags in hex");
        }

        // Test 4: Call with empty export string and no modules
        textifier.visitOpen("", 0, (String[]) null);
        List<String> text4 = textifier.getText();
        String output4 = text4.get(text4.size() - 1);
        if (!output4.contains("opens ")) {
            throw new AssertionError("Output should contain 'opens ' even if export is empty");
        }
        if (!output4.contains("0x0")) {
            throw new AssertionError("Output should contain access flags 0x0");
        }

        // No exceptions should be thrown during these calls
        System.out.println("All tests passed.");
    }
}
