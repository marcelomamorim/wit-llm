/*
 * @test
 * @summary Test ASMifier.visitMethodEnd() appends correct visitEnd call
 * @run main ASMifierWitupTest
 */

import java.util.ArrayList;
import java.util.List;

public class ASMifierWitupTest {

    public static class ASMifier {
        protected final String name;
        protected final List<String> text = new ArrayList<>();
        protected final StringBuilder buf = new StringBuilder();

        public ASMifier() {
            this.name = "mv";
        }

        public void visitMethodEnd() {
            buf.setLength(0);
            buf.append(name).append(".visitEnd();\n");
            text.add(buf.toString());
        }
    }

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();
        asmifier.visitMethodEnd();
        if (asmifier.text.size() != 1) {
            throw new AssertionError("Expected text size 1 but got " + asmifier.text.size());
        }
        String expected = "mv.visitEnd();\n";
        String actual = asmifier.text.get(0);
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected '" + expected + "' but got '" + actual + "'");
        }
    }
}
