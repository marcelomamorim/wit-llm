/*
 * @test
 * @summary Checks Runtime.Version.compareToIgnoreOptional null handling and self-comparison
 * @run main RuntimeWitupTest
 */

public class RuntimeWitupTest {
    public static void main(String[] args) {
        Runtime.Version v = Runtime.getRuntime().version();

        int self = v.compareToIgnoreOptional(v);
        if (self != 0) {
            throw new AssertionError("Expected 0 for self comparison but got " + self);
        }

        boolean npe = false;
        try {
            v.compareToIgnoreOptional(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new AssertionError("Expected NullPointerException for null argument");
        }
    }
}
