/*
 * @test
 * @summary Test StringJoiner constructor with single delimiter argument
 * @run main StringJoinerWitupTest
 */
public class StringJoinerWitupTest {
    public static void main(String[] args) {
        // Test with a normal delimiter
        java.util.StringJoiner sj = new java.util.StringJoiner(",");
        String result = sj.toString();
        if (!result.equals("")) {
            throw new AssertionError("Expected empty StringJoiner toString() to be empty string but was: " + result);
        }

        // Test that adding elements works with default prefix and suffix (empty strings)
        sj.add("a");
        sj.add("b");
        String joined = sj.toString();
        if (!joined.equals("a,b")) {
            throw new AssertionError("Expected joined string to be 'a,b' but was: " + joined);
        }

        // Test with empty delimiter
        java.util.StringJoiner sj2 = new java.util.StringJoiner("");
        if (!sj2.toString().equals("")) {
            throw new AssertionError("Expected empty StringJoiner with empty delimiter toString() to be empty string");
        }
        sj2.add("x");
        sj2.add("y");
        if (!sj2.toString().equals("xy")) {
            throw new AssertionError("Expected joined string to be 'xy' but was: " + sj2.toString());
        }

        // Test with delimiter as empty string and no elements added
        java.util.StringJoiner sj3 = new java.util.StringJoiner("");
        if (!sj3.toString().equals("")) {
            throw new AssertionError("Expected empty StringJoiner with empty delimiter toString() to be empty string");
        }

        // Test with delimiter as null - should throw NullPointerException
        boolean npeThrown = false;
        try {
            new java.util.StringJoiner(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when delimiter is null");
        }
    }
}
