/* @test    @summary Test for GetPropertyAction.run() method to verify behavior with null and empty property names    @run main GetPropertyActionWitupTest */

import sun.security.action.GetPropertyAction;
import java.security.AccessController;

public class GetPropertyActionWitupTest {

    public static void main(String[] args) {
        testNullPropertyName();
        testEmptyPropertyName();
        testValidPropertyName();
    }

    private static void testNullPropertyName() {
        try {
            GetPropertyAction action = new GetPropertyAction(null);
            String result = action.run();
            if (result != null) {
                System.out.println("FAIL: Expected null for null property name, but got: " + result);
            } else {
                System.out.println("PASS: Null property name returns null as expected.");
            }
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null property name.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for null property name: " + e);
        }
    }

    private static void testEmptyPropertyName() {
        try {
            GetPropertyAction action = new GetPropertyAction("");
            String result = action.run();
            System.out.println("Result for empty property name: '" + result + "'");
            // No specific expected value; just ensure no exception
            System.out.println("PASS: Empty property name handled without exception.");
        } catch (Exception e) {
            System.out.println("FAIL: Exception for empty property name: " + e);
        }
    }

    private static void testValidPropertyName() {
        String testProp = "java.version";
        String expected = System.getProperty(testProp);
        try {
            GetPropertyAction action = new GetPropertyAction(testProp);
            String result = action.run();
            if (expected == null) {
                if (result == null) {
                    System.out.println("PASS: Property '" + testProp + "' correctly returns null.");
                } else {
                    System.out.println("FAIL: Expected null for property '" + testProp + "', but got: " + result);
                }
            } else {
                if (expected.equals(result)) {
                    System.out.println("PASS: Property '" + testProp + "' returned expected value.");
                } else {
                    System.out.println("FAIL: Property '" + testProp + "' returned unexpected value: " + result);
                }
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception while testing valid property: " + e);
        }
    }
}
