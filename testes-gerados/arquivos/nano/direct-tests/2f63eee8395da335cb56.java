/* @test    @summary Test for Attributes.Name constructor with valid and invalid inputs    @run main AttributesWitupTest */

import java.util.jar.Attributes;

public class AttributesWitupTest {

    public static void main(String[] args) {
        testValidName();
        testNullName();
        testEmptyName();
    }

    static void testValidName() {
        String validName = "MyAttribute";
        Attributes.Name name = new Attributes.Name(validName);
        assert name != null : "Name object should not be null for valid input";
        assert name.toString().equals(validName) : "Name string should match input";
    }

    static void testNullName() {
        try {
            Attributes.Name name = new Attributes.Name(null);
            assert false : "Constructor should throw NullPointerException for null input";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testEmptyName() {
        String emptyName = "";
        Attributes.Name name = new Attributes.Name(emptyName);
        assert name != null : "Name object should be created for empty string";
        assert name.toString().equals(emptyName) : "Name string should match empty input";
    }
}
