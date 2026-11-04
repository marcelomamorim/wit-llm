/* @test    @summary Test parseUnsignedInt with valid decimal string    @run main IntegerWitupTest */

public class IntegerWitupTest {

    public static void main(String[] args) {
        testParseUnsignedIntValid();
        testParseUnsignedIntInvalid();
    }

    static void testParseUnsignedIntValid() {
        String s = "12345";
        int result = Integer.parseUnsignedInt(s);
        assert result == 12345 : "Expected 12345, but got " + result;
    }

    static void testParseUnsignedIntInvalid() {
        String s = "notANumber";
        try {
            Integer.parseUnsignedInt(s);
            assert false : "Expected NumberFormatException for input: " + s;
        } catch (NumberFormatException e) {
            // expected
        }
    }
}
