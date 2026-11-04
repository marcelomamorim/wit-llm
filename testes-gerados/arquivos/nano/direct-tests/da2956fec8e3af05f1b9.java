/* @test    @summary Test referenceKindToString with valid and invalid inputs    @run main MethodHandleInfoWitupTest */

import java.lang.invoke.MethodHandleInfo;

public class MethodHandleInfoWitupTest {

    public static void main(String[] args) {
        testValidReferenceKinds();
        testInvalidReferenceKind();
    }

    private static void testValidReferenceKinds() {
        String result0 = MethodHandleInfo.referenceKindToString(1);
        String result1 = MethodHandleInfo.referenceKindToString(2);
        String result2 = MethodHandleInfo.referenceKindToString(3);
        String result3 = MethodHandleInfo.referenceKindToString(4);
        String result4 = MethodHandleInfo.referenceKindToString(5);
        String result5 = MethodHandleInfo.referenceKindToString(6);
        String result6 = MethodHandleInfo.referenceKindToString(7);
        String result7 = MethodHandleInfo.referenceKindToString(8);
        String result8 = MethodHandleInfo.referenceKindToString(9);
        String result9 = MethodHandleInfo.referenceKindToString(10);
        String result10 = MethodHandleInfo.referenceKindToString(11);
        System.out.println("referenceKindToString(1): " + result0);
        System.out.println("referenceKindToString(2): " + result1);
        System.out.println("referenceKindToString(3): " + result2);
        System.out.println("referenceKindToString(4): " + result3);
        System.out.println("referenceKindToString(5): " + result4);
        System.out.println("referenceKindToString(6): " + result5);
        System.out.println("referenceKindToString(7): " + result6);
        System.out.println("referenceKindToString(8): " + result7);
        System.out.println("referenceKindToString(9): " + result8);
        System.out.println("referenceKindToString(10): " + result9);
        System.out.println("referenceKindToString(11): " + result10);
    }

    private static void testInvalidReferenceKind() {
        try {
            MethodHandleInfo.referenceKindToString(0);
            System.out.println("Expected exception for invalid reference kind 0, but none thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected IllegalArgumentException for reference kind 0.");
        }
        try {
            MethodHandleInfo.referenceKindToString(12);
            System.out.println("Expected exception for invalid reference kind 12, but none thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected IllegalArgumentException for reference kind 12.");
        }
    }
}
