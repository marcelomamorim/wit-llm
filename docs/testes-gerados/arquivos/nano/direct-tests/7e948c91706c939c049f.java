/* @test    @summary Test ChoiceFormat.setChoices with valid inputs    @run main ChoiceFormatWitupTest */

import java.text.ChoiceFormat;

public class ChoiceFormatWitupTest {

    public static void main(String[] args) {
        testSetChoicesValid();
        testSetChoicesMismatchedLengths();
        testSetChoicesEmptyLimits();
        testSetChoicesEmptyFormats();
        testSetChoicesWithInfinity();
    }

    public static void testSetChoicesValid() {
        ChoiceFormat cf = new ChoiceFormat();
        double[] limits = {0.0, 1.0, 2.0};
        String[] formats = {"Zero", "One", "Two"};
        cf.setChoices(limits, formats);
        assert "Zero".equals(cf.format(0.0)) : "Expected 'Zero' for 0.0";
        assert "One".equals(cf.format(1.0)) : "Expected 'One' for 1.0";
        assert "Two".equals(cf.format(2.0)) : "Expected 'Two' for 2.0";
    }

    public static void testSetChoicesMismatchedLengths() {
        ChoiceFormat cf = new ChoiceFormat();
        double[] limits = {0.0, 1.0};
        String[] formats = {"Zero"};
        boolean exceptionThrown = false;
        try {
            cf.setChoices(limits, formats);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IllegalArgumentException for mismatched array lengths";
    }

    public static void testSetChoicesEmptyLimits() {
        ChoiceFormat cf = new ChoiceFormat();
        double[] limits = {};
        String[] formats = {};
        cf.setChoices(limits, formats);
        assert "".equals(cf.format(0.0)) : "Expected empty string for empty choices";
    }

    public static void testSetChoicesEmptyFormats() {
        ChoiceFormat cf = new ChoiceFormat();
        double[] limits = {0.0};
        String[] formats = {};
        boolean exceptionThrown = false;
        try {
            cf.setChoices(limits, formats);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IllegalArgumentException for empty formats array";
    }

    public static void testSetChoicesWithInfinity() {
        ChoiceFormat cf = new ChoiceFormat();
        double[] limits = {Double.NEGATIVE_INFINITY, 1.0, Double.POSITIVE_INFINITY};
        String[] formats = {"NegInf", "Mid", "PosInf"};
        cf.setChoices(limits, formats);
        assert "NegInf".equals(cf.format(Double.NEGATIVE_INFINITY)) : "Expected 'NegInf' for -Infinity";
        assert "Mid".equals(cf.format(0.5)) : "Expected 'Mid' for 0.5";
        assert "PosInf".equals(cf.format(Double.POSITIVE_INFINITY)) : "Expected 'PosInf' for +Infinity";
    }
}
