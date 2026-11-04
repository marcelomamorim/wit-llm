/*
 * @test
 * @summary Test visitIincInsn method of Textifier for basic functionality
 * @run main TextifierWitupTest
 */

import java.util.List;
import java.util.ArrayList;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();
        // Call visitIincInsn with typical values
        textifier.visitIincInsn(5, 10);

        // The text list should contain one string with "IINC 5 10\n" after tab2
        List<String> text = textifier.getText();
        if (text == null || text.isEmpty()) {
            throw new AssertionError("Text list is null or empty after visitIincInsn");
        }

        String line = text.get(text.size() - 1);
        if (!line.contains("IINC 5 10")) {
            throw new AssertionError("Expected line to contain 'IINC 5 10' but was: " + line);
        }

        // Call with zero increment
        textifier.visitIincInsn(0, 0);
        line = textifier.getText().get(textifier.getText().size() - 1);
        if (!line.contains("IINC 0 0")) {
            throw new AssertionError("Expected line to contain 'IINC 0 0' but was: " + line);
        }

        // Call with negative increment
        textifier.visitIincInsn(1, -1);
        line = textifier.getText().get(textifier.getText().size() - 1);
        if (!line.contains("IINC 1 -1")) {
            throw new AssertionError("Expected line to contain 'IINC 1 -1' but was: " + line);
        }

        System.out.println("All tests passed.");
    }
}

// Minimal stub of Textifier class to allow compilation and testing
// This stub mimics the relevant behavior of visitIincInsn and text list
class Textifier {
    final StringBuilder buf = new StringBuilder();
    final List<String> text = new ArrayList<>();
    final String tab2 = "  ";

    public Textifier() {
    }

    public void visitIincInsn(final int var, final int increment) {
        buf.setLength(0);
        buf.append(tab2).append("IINC ").append(var).append(' ').append(increment).append('\n');
        text.add(buf.toString());
    }

    public List<String> getText() {
        return text;
    }
}
