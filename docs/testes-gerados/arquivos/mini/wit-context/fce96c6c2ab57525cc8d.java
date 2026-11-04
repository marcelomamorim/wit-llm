/*
 * @test
 * @summary Test StringJoiner(CharSequence) constructor for basic behavior and no exceptions
 * @run main StringJoinerWitupTest
 */
public class StringJoinerWitupTest {
    public static void main(String[] args) {
        // Test normal construction with non-null delimiter
        java.util.StringJoiner sj = new java.util.StringJoiner(",");
        if (sj == null) {
            throw new AssertionError("StringJoiner instance is null");
        }
        // The toString() of a new StringJoiner with no elements should be prefix + suffix, which is "" + "" = ""
        String result = sj.toString();
        if (!"".equals(result)) {
            throw new AssertionError("Expected empty string but got: " + result);
        }
    }
}
