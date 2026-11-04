/* @test    @summary Test for Formatter.toString() method, including exception path.    @run main FormatterWitupTest */

import java.util.Formatter;

public class FormatterWitupTest {

    public static void main(String[] args) {
        testToStringWithNull();
        testToStringWithOpenFormatter();
    }

    private static void testToStringWithNull() {
        Formatter formatter = null;
        try {
            formatter = new Formatter();
            // Close the formatter to simulate closed state
            formatter.close();
            // Attempt to call toString() on closed formatter
            String result = formatter.toString();
            System.out.println("Unexpected success: " + result);
        } catch (IllegalStateException e) {
            System.out.println("Caught expected IllegalStateException when calling toString() on closed Formatter.");
        } catch (Exception e) {
            System.out.println("Caught unexpected exception: " + e);
        }
    }

    private static void testToStringWithOpenFormatter() {
        Formatter formatter = null;
        try {
            formatter = new Formatter();
            // Append some data
            formatter.format("Test: %d", 123);
            // Call toString() on open formatter
            String result = formatter.toString();
            System.out.println("toString() output: " + result);
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e);
        } finally {
            if (formatter != null) {
                formatter.close();
            }
        }
    }
}
