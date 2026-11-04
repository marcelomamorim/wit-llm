/*
 * @test
 * @summary Test Textifier.visitMaxs method for correct output and no exceptions
 * @run main TextifierWitupTest
 */

import java.util.List;
import java.util.ArrayList;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();
        // Test with typical values
        textifier.visitMaxs(5, 10);
        List<String> output = textifier.getText();

        if (output == null) {
            throw new AssertionError("getText() returned null");
        }

        if (output.size() < 2) {
            throw new AssertionError("Expected at least 2 lines of output, got " + output.size());
        }

        String maxStackLine = output.get(output.size() - 2);
        String maxLocalsLine = output.get(output.size() - 1);

        if (!maxStackLine.contains("MAXSTACK = 5")) {
            throw new AssertionError("MAXSTACK line incorrect: " + maxStackLine);
        }

        if (!maxLocalsLine.contains("MAXLOCALS = 10")) {
            throw new AssertionError("MAXLOCALS line incorrect: " + maxLocalsLine);
        }

        // Test with zero values
        textifier.visitMaxs(0, 0);
        output = textifier.getText();

        maxStackLine = output.get(output.size() - 2);
        maxLocalsLine = output.get(output.size() - 1);

        if (!maxStackLine.contains("MAXSTACK = 0")) {
            throw new AssertionError("MAXSTACK line incorrect for zero: " + maxStackLine);
        }

        if (!maxLocalsLine.contains("MAXLOCALS = 0")) {
            throw new AssertionError("MAXLOCALS line incorrect for zero: " + maxLocalsLine);
        }

        // Test with negative values - should not throw but produce output
        textifier.visitMaxs(-1, -1);
        output = textifier.getText();

        maxStackLine = output.get(output.size() - 2);
        maxLocalsLine = output.get(output.size() - 1);

        if (!maxStackLine.contains("MAXSTACK = -1")) {
            throw new AssertionError("MAXSTACK line incorrect for negative: " + maxStackLine);
        }

        if (!maxLocalsLine.contains("MAXLOCALS = -1")) {
            throw new AssertionError("MAXLOCALS line incorrect for negative: " + maxLocalsLine);
        }

        System.out.println("All tests passed.");
    }
}
