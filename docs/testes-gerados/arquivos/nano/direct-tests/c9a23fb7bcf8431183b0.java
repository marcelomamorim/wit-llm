/* @test    @summary Test ChoiceFormat constructor with valid limits and formats    @run main ChoiceFormatWitupTest */

import java.text.ChoiceFormat;

public class ChoiceFormatWitupTest {

    public static void main(String[] args) {
        testValidLimitsAndFormats();
        testEmptyLimitsAndFormats();
        testSingleElementLimitsAndFormats();
        testLimitsWithInfinity();
    }

    static void testValidLimitsAndFormats() {
        double[] limits = {0.0, 1.0, 2.0};
        String[] formats = {"Zero", "One", "Two"};
        ChoiceFormat cf = new ChoiceFormat(limits, formats);
        assert cf != null : "ChoiceFormat instance should not be null";
        double testValue = 1.5;
        String result = cf.format(testValue);
        assert "Two".equals(result) : "Expected 'Two' for value 1.5, got " + result;
    }

    static void testEmptyLimitsAndFormats() {
        double[] limits = {};
        String[] formats = {};
        ChoiceFormat cf = new ChoiceFormat(limits, formats);
        assert cf != null : "ChoiceFormat instance should not be null";
        double testValue = 10.0;
        String result = cf.format(testValue);
        assert result != null : "Result should not be null";
    }

    static void testSingleElementLimitsAndFormats() {
        double[] limits = {0.0};
        String[] formats = {"Only"};
        ChoiceFormat cf = new ChoiceFormat(limits, formats);
        assert cf != null : "ChoiceFormat instance should not be null";
        double testValue = -5.0;
        String result = cf.format(testValue);
        assert "Only".equals(result) : "Expected 'Only' for value -5.0, got " + result;
        testValue = 100.0;
        result = cf.format(testValue);
        assert "Only".equals(result) : "Expected 'Only' for value 100.0, got " + result;
    }

    static void testLimitsWithInfinity() {
        double[] limits = {Double.NEGATIVE_INFINITY, 0.0, Double.POSITIVE_INFINITY};
        String[] formats = {"NegInf", "Zero", "PosInf"};
        ChoiceFormat cf = new ChoiceFormat(limits, formats);
        assert cf != null : "ChoiceFormat instance should not be null";
        double testValue = -1000.0;
        String result = cf.format(testValue);
        assert "NegInf".equals(result) : "Expected 'NegInf' for value -1000.0, got " + result;
        testValue = 0.0;
        result = cf.format(testValue);
        assert "Zero".equals(result) : "Expected 'Zero' for value 0.0, got " + result;
        testValue = 1000.0;
        result = cf.format(testValue);
        assert "PosInf".equals(result) : "Expected 'PosInf' for value 1000.0, got " + result;
    }
}
