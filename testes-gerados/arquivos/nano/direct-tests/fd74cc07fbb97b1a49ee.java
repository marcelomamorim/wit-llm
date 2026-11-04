/* @test    @summary Test for visitRequire method in Textifier class    @run main TextifierWitupTest */

import java.util.List;
import java.util.ArrayList;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitRequireWithAllFlags();
        testVisitRequireWithNullVersion();
        testVisitRequireWithNoFlags();
    }

    static void testVisitRequireWithAllFlags() {
        Textifier textifier = new Textifier();
        List<String> text = new ArrayList<>();
        // Opcodes.ACC_TRANSITIVE = 0x0020, Opcodes.ACC_STATIC_PHASE = 0x0040
        int access = 0x0020 | 0x0040;
        String require = "java.base";
        String version = "11";
        textifier.visitRequire(require, access, version);
        String output = text.get(0);
        assert output.contains("requires java.base;") : "Expected 'requires java.base;', got: " + output;
        assert output.contains("transitive") : "Expected 'transitive' flag in output";
        assert output.contains("static") : "Expected 'static' flag in output";
        assert output.contains("// access flags 0x60") : "Expected access flags in hex";
        assert output.contains("// version 11") : "Expected version comment";
    }

    static void testVisitRequireWithNullVersion() {
        Textifier textifier = new Textifier();
        List<String> text = new ArrayList<>();
        int access = 0x0000;
        String require = "java.logging";
        String version = null;
        textifier.visitRequire(require, access, version);
        String output = text.get(0);
        assert output.contains("requires java.logging;") : "Expected 'requires java.logging;', got: " + output;
        assert !output.contains("// version") : "Did not expect version comment";
        assert output.contains("// access flags 0x0") : "Expected access flags in hex";
    }

    static void testVisitRequireWithNoFlags() {
        Textifier textifier = new Textifier();
        List<String> text = new ArrayList<>();
        int access = 0x0000;
        String require = "java.sql";
        String version = "8";
        textifier.visitRequire(require, access, version);
        String output = text.get(0);
        assert output.contains("requires java.sql;") : "Expected 'requires java.sql;', got: " + output;
        assert output.contains("// access flags 0x0") : "Expected access flags in hex";
        assert output.contains("// version 8") : "Expected version comment";
    }
}
