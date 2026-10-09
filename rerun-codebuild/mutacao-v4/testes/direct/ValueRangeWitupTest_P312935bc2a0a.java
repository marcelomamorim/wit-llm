/* @test
   @summary Tests checkValidIntValue method for valid and invalid inputs.
   @run main ValueRangeWitupTest_P312935bc2a0a
 */
import java.time.temporal.ValueRange;
import java.time.temporal.TemporalField;
import java.time.DateTimeException;

public class ValueRangeWitupTest_P312935bc2a0a {
    public static void main(String[] args) {
        testCheckValidIntValueValid();
        testCheckValidIntValueInvalid();
    }

    static void testCheckValidIntValueValid() {
        ValueRange range = ValueRange.of(1, 10);
        long validValue = 5;
        TemporalField dummyField = new DummyTemporalField();
        int result = range.checkValidIntValue(validValue, dummyField);
        if (result != (int) validValue) {
            throw new AssertionError("Expected " + (int) validValue + " but got " + result);
        }
    }

    static void testCheckValidIntValueInvalid() {
        ValueRange range = ValueRange.of(1, 10);
        long invalidValue = 15;
        TemporalField dummyField = new DummyTemporalField();
        try {
            range.checkValidIntValue(invalidValue, dummyField);
            throw new AssertionError("Expected DateTimeException for value " + invalidValue);
        } catch (DateTimeException e) {
            // expected
        }
    }

    static class DummyTemporalField implements TemporalField {
        @Override
        public String toString() { return "DummyField"; }
        // All other methods are not used in this test and can be left unimplemented or with dummy returns
        @Override public String getName() { return "Dummy"; }
        @Override public Class<?> getType() { return null; }
        @Override public boolean isDateBased() { return false; }
        @Override public boolean isTimeBased() { return false; }
        @Override public boolean isSupportedBy(Object temporal) { return false; }
        @Override public ValueRange rangeRefinedBy(Object temporal) { return null; }
        @Override public long getFrom(Object temporal) { return 0; }
        @Override public <R extends java.time.temporal.Temporal> R adjustInto(R temporal, long newValue) { return null; }
        @Override public String getDisplayName(java.util.Locale locale) { return null; }
    }
}
