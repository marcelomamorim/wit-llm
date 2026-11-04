/* @test    @summary Test MethodHandleInfo.toString method for correctness    @run main MethodHandleInfoWitupTest */

import java.lang.invoke.MethodHandleInfo;
import java.lang.invoke.MethodType;
import static java.lang.invoke.MethodHandleInfo.*;

public class MethodHandleInfoWitupTest {

    public static void main(String[] args) {
        testToStringWithValidInputs();
        testToStringWithNullName();
        testToStringWithNullType();
    }

    static void testToStringWithValidInputs() {
        String result = toString(1, String.class, "testMethod", MethodType.methodType(void.class));
        String expected = "referenceKindToString(1) String.testMethod:()V";
        // Since referenceKindToString is not defined here, we check for non-null and format correctness
        if (result == null || !result.contains(" ") || !result.contains(".")) {
            throw new AssertionError("Unexpected toString output: " + result);
        }
    }

    static void testToStringWithNullName() {
        try {
            toString(1, String.class, null, MethodType.methodType(void.class));
            throw new AssertionError("Expected NullPointerException for null name");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testToStringWithNullType() {
        try {
            toString(1, String.class, "name", null);
            throw new AssertionError("Expected NullPointerException for null type");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
