/* @test    @summary Test ASMifier.visitAnnotationEnd() method for correct behavior    @run main ASMifierWitupTest */

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitAnnotationEnd();
    }

    public static void testVisitAnnotationEnd() {
        // Arrange
        class DummyASMifier extends jdk.internal.org.objectweb.asm.util.ASMifier {
            StringBuilder buf = new StringBuilder();
            List<String> text = new ArrayList<>();
            @Override
            public void visitAnnotationEnd() {
                buf.setLength(0);
                buf.append("av").append(id).append(".visitEnd();\n");
                text.add(buf.toString());
            }
        }
        DummyASMifier asmifier = new DummyASMifier();
        asmifier.id = 42; // set id to a test value

        // Act
        asmifier.visitAnnotationEnd();

        // Assert
        String expected = "av42.visitEnd();\n";
        if (asmifier.text.size() != 1) {
            throw new AssertionError("Expected 1 entry in text list, but found: " + asmifier.text.size());
        }
        String actual = asmifier.text.get(0);
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + actual);
        }
        System.out.println("testVisitAnnotationEnd passed.");
    }
}
