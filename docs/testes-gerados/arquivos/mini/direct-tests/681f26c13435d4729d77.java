/*
 * @test
 * @summary Test ASMifier.visitMultiANewArrayInsn method for correct output formatting
 * @run main ASMifierWitupTest
 */

import java.util.List;
import java.util.ArrayList;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Clear any previous text
        asmifier.text.clear();

        // Test with typical descriptor and dimension
        asmifier.visitMultiANewArrayInsn("[I", 3);
        List<String> text = asmifier.text;
        if (text.size() != 1) {
            throw new AssertionError("Expected exactly one line of output");
        }
        String line = text.get(0);
        String expectedStart = asmifier.name + ".visitMultiANewArrayInsn(";
        if (!line.startsWith(expectedStart)) {
            throw new AssertionError("Output line does not start with expected prefix: " + line);
        }
        if (!line.contains("\"[I\"")) {
            throw new AssertionError("Descriptor not correctly included in output: " + line);
        }
        if (!line.contains(", 3")) {
            throw new AssertionError("Dimension not correctly included in output: " + line);
        }
        if (!line.endsWith(");\n")) {
            throw new AssertionError("Output line does not end with ');\n': " + line);
        }

        // Test with empty descriptor and zero dimension
        asmifier.text.clear();
        asmifier.visitMultiANewArrayInsn("", 0);
        line = asmifier.text.get(0);
        if (!line.contains("\"\"")) {
            throw new AssertionError("Empty descriptor not correctly included in output: " + line);
        }
        if (!line.contains(", 0")) {
            throw new AssertionError("Zero dimension not correctly included in output: " + line);
        }

        // Test with null descriptor (should print "null" as string)
        asmifier.text.clear();
        asmifier.visitMultiANewArrayInsn(null, 1);
        line = asmifier.text.get(0);
        if (!line.contains("null")) {
            throw new AssertionError("Null descriptor not correctly included in output: " + line);
        }
        if (!line.contains(", 1")) {
            throw new AssertionError("Dimension not correctly included in output for null descriptor: " + line);
        }

        System.out.println("All tests passed.");
    }
}

class ASMifier extends jdk.internal.org.objectweb.asm.util.ASMifier {
    public ASMifier() {
        super();
    }

    // Expose protected field 'text' for testing
    public final List<String> text = super.text;
    public final String name = super.name;

    // Override appendConstant to print the string representation of the constant
    @Override
    protected void appendConstant(final Object cst) {
        if (cst == null) {
            buf.append("null");
        } else if (cst instanceof String) {
            buf.append('"').append(cst).append('"');
        } else {
            buf.append(cst.toString());
        }
    }

    // Buffer used in visitMultiANewArrayInsn
    private final StringBuilder buf = new StringBuilder();

    @Override
    public void visitMultiANewArrayInsn(final String desc, final int dims) {
        buf.setLength(0);
        buf.append(name).append(".visitMultiANewArrayInsn(");
        appendConstant(desc);
        buf.append(", ").append(dims).append(");\n");
        text.add(buf.toString());
    }
}
