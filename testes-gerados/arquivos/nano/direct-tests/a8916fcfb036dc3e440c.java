/* @test    @summary Test visitOpen method of ASMifier class    @run main ASMifierWitupTest */

import java.util.ArrayList;
import java.util.List;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitOpenWithModules();
        testVisitOpenWithoutModules();
    }

    public static void testVisitOpenWithModules() {
        jdk.internal.org.objectweb.asm.util.ASMifier asmifier = new jdk.internal.org.objectweb.asm.util.ASMifier();
        List<String> textOutput = new ArrayList<>();
        // Mock buf and text as in the original method context
        StringBuilder buf = new StringBuilder();
        List<String> text = textOutput;
        // Call method with modules
        asmifier.visitOpen("com.example", 0x0001, new String[] {"module1", "module2"});
        // Validate output contains expected substrings
        boolean containsModule1 = false;
        boolean containsModule2 = false;
        for (String line : textOutput) {
            if (line.contains("com.example")) {
                containsModule1 = line.contains("module1");
                containsModule2 = line.contains("module2");
            }
        }
        if (!containsModule1 || !containsModule2) {
            throw new AssertionError("visitOpen with modules did not produce expected output");
        }
    }

    public static void testVisitOpenWithoutModules() {
        jdk.internal.org.objectweb.asm.util.ASMifier asmifier = new jdk.internal.org.objectweb.asm.util.ASMifier();
        List<String> textOutput = new ArrayList<>();
        StringBuilder buf = new StringBuilder();
        List<String> text = textOutput;
        // Call method without modules
        asmifier.visitOpen("com.example", 0x0002, null);
        boolean foundLine = false;
        for (String line : textOutput) {
            if (line.contains("com.example")) {
                foundLine = true;
                if (line.contains("new String[]")) {
                    throw new AssertionError("Unexpected modules array in output when modules are null");
                }
            }
        }
        if (!foundLine) {
            throw new AssertionError("visitOpen without modules did not produce expected output");
        }
    }
}
