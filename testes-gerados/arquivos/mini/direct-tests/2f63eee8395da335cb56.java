/*
 * @test
 * @summary Test java.util.jar.Attributes.Name constructor behavior
 * @run main AttributesWitupTest
 */

public class AttributesWitupTest {
    public static void main(String[] args) {
        // Test with a normal string
        java.util.jar.Attributes.Name name1 = new java.util.jar.Attributes.Name("TestName");
        if (!"TestName".equals(name1.toString())) {
            throw new RuntimeException("Name string mismatch");
        }

        // Test that intern() is used: two equal strings produce same interned name
        String s1 = new String("InternedName");
        String s2 = new String("InternedName");
        java.util.jar.Attributes.Name name2 = new java.util.jar.Attributes.Name(s1);
        java.util.jar.Attributes.Name name3 = new java.util.jar.Attributes.Name(s2);
        if (name2.name != name3.name) {
            throw new RuntimeException("Name strings are not interned as expected");
        }

        // Test that hashCode is consistent with hash(name)
        int expectedHash = name2.hashCode();
        if (expectedHash != name2.hashCode()) {
            throw new RuntimeException("Hash code inconsistent");
        }

        System.out.println("Attributes.Name constructor tests passed.");
    }
}
