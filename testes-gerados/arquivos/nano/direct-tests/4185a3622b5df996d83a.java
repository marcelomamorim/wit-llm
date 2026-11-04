/* @test    @summary Test visitSource method of Textifier class    @run main TextifierWitupTest */

import java.util.List;
import java.util.ArrayList;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitSourceWithFileAndDebug();
        testVisitSourceWithNullFile();
        testVisitSourceWithNullDebug();
        testVisitSourceWithBothNull();
    }

    static void testVisitSourceWithFileAndDebug() {
        Textifier t = new Textifier();
        List<String> output = new ArrayList<>();
        t.text = output;
        t.visitSource("TestFile.java", "DebugInfo");
        String result = output.get(0);
        assert result.contains("// compiled from: TestFile.java") : "Expected file info in output";
        assert result.contains("// debug info: DebugInfo") : "Expected debug info in output";
    }

    static void testVisitSourceWithNullFile() {
        Textifier t = new Textifier();
        List<String> output = new ArrayList<>();
        t.text = output;
        t.visitSource(null, "DebugInfo");
        String result = output.get(0);
        assert result.contains("// debug info: DebugInfo") : "Expected debug info in output";
        assert !result.contains("// compiled from:") : "Should not contain file info";
    }

    static void testVisitSourceWithNullDebug() {
        Textifier t = new Textifier();
        List<String> output = new ArrayList<>();
        t.text = output;
        t.visitSource("TestFile.java", null);
        String result = output.get(0);
        assert result.contains("// compiled from: TestFile.java") : "Expected file info in output";
        assert !result.contains("// debug info:") : "Should not contain debug info";
    }

    static void testVisitSourceWithBothNull() {
        Textifier t = new Textifier();
        List<String> output = new ArrayList<>();
        t.text = output;
        t.visitSource(null, null);
        assert output.isEmpty() : "No output expected when both parameters are null";
    }
}
