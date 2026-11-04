/*
 * @test
 * @summary Test ASMifier.visitAttribute method behavior
 * @run main ASMifierWitupTest
 */

import java.util.HashMap;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.util.ASMifier;
import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.util.ASMifiable;

public class ASMifierWitupTest {

    static class DummyAttribute extends Attribute {
        DummyAttribute() {
            super("DummyType");
        }
    }

    static class DummyASMifiableAttribute extends Attribute implements ASMifiable {
        DummyASMifiableAttribute() {
            super("ASMifiableType");
        }

        @Override
        public void asmify(StringBuilder buf, String varName, java.util.Map<Label, String> labelNames) {
            buf.append("// asmify called\n");
            buf.append(varName).append(".dummyAsmify();\n");
        }
    }

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Test with null labelNames and non-ASMifiable attribute
        DummyAttribute attr1 = new DummyAttribute();
        asmifier.visitAttribute(attr1);
        String output1 = asmifier.getText().get(asmifier.getText().size() - 1);
        if (!output1.contains("// ATTRIBUTE DummyType")) {
            throw new Error("visitAttribute failed for non-ASMifiable attribute");
        }

        // Test with ASMifiable attribute and null labelNames
        DummyASMifiableAttribute attr2 = new DummyASMifiableAttribute();
        asmifier.labelNames = null; // explicitly null to test initialization
        asmifier.visitAttribute(attr2);
        String output2 = asmifier.getText().get(asmifier.getText().size() - 1);
        if (!output2.contains("// ATTRIBUTE ASMifiableType")) {
            throw new Error("visitAttribute failed to print attribute type for ASMifiable");
        }
        if (!output2.contains("// asmify called")) {
            throw new Error("asmify method was not called on ASMifiable attribute");
        }
        if (!output2.contains("attr.dummyAsmify();")) {
            throw new Error("asmify output missing expected dummyAsmify call");
        }
        if (!output2.contains(".visitAttribute(attr);")) {
            throw new Error("visitAttribute call missing in output");
        }

        // Test with ASMifiable attribute and pre-existing labelNames map
        asmifier.labelNames = new HashMap<Label, String>();
        DummyASMifiableAttribute attr3 = new DummyASMifiableAttribute();
        asmifier.visitAttribute(attr3);
        String output3 = asmifier.getText().get(asmifier.getText().size() - 1);
        if (!output3.contains("// ATTRIBUTE ASMifiableType")) {
            throw new Error("visitAttribute failed with existing labelNames map");
        }

        System.out.println("All tests passed.");
    }
}
