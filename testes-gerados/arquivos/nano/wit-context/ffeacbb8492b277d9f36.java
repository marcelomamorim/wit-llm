/*
 * @test
 * @summary Tests for java.lang.invoke.ConstantBootstraps methods
 * @run main ConstantBootstrapsWitupTest
 */

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.ConstantBootstraps;

public class ConstantBootstrapsWitupTest {

    public static void main(String[] args) {
        testEnumConstant_NullLookup();
        testEnumConstant_NullName();
        testEnumConstant_NullType();
    }

    private static void testEnumConstant_NullLookup() {
        try {
            ConstantBootstraps.enumConstant(null, "ENUM_NAME", SampleEnum.class);
            System.out.println("Failed: Expected NullPointerException for null lookup");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException for null lookup");
        } catch (Throwable t) {
            System.out.println("Failed: Unexpected exception for null lookup: " + t);
        }
    }

    private static void testEnumConstant_NullName() {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            ConstantBootstraps.enumConstant(lookup, null, SampleEnum.class);
            System.out.println("Failed: Expected NullPointerException for null name");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException for null name");
        } catch (Throwable t) {
            System.out.println("Failed: Unexpected exception for null name: " + t);
        }
    }

    private static void testEnumConstant_NullType() {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            ConstantBootstraps.enumConstant(lookup, "ENUM_NAME", null);
            System.out.println("Failed: Expected NullPointerException for null type");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException for null type");
        } catch (Throwable t) {
            System.out.println("Failed: Unexpected exception for null type: " + t);
        }
    }

    // Sample enum for testing
    public enum SampleEnum {
        VALUE1, VALUE2
    }
}
