/* @test    @summary Test for ClassFileAssembler.cpi() method to verify exception when cpIdx is zero    @run main ClassFileAssemblerWitupTest */

import jdk.internal.reflect.ClassFileAssembler;

public class ClassFileAssemblerWitupTest {

    public static void main(String[] args) {
        testCpiThrowsExceptionWhenCpIdxIsZero();
        testCpiReturnsValueWhenCpIdxIsNonZero();
    }

    private static void testCpiThrowsExceptionWhenCpIdxIsZero() {
        ClassFileAssembler assembler = new ClassFileAssembler();
        // Reflection or package access needed to set cpIdx to 0, as constructor initializes it to 1
        // Since cpIdx is private and no public setter, use reflection
        try {
            java.lang.reflect.Field cpIdxField = ClassFileAssembler.class.getDeclaredField("cpIdx");
            cpIdxField.setAccessible(true);
            cpIdxField.setShort(assembler, (short) 0);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Fail the test if reflection fails
            System.err.println("Reflection setup failed: " + e);
            return;
        }
        try {
            assembler.cpi();
            System.err.println("Expected RuntimeException was not thrown");
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Illegal use of ClassFileAssembler")) {
                System.out.println("Passed: cpi() throws RuntimeException when cpIdx is zero");
            } else {
                System.err.println("Failed: Unexpected exception message: " + e.getMessage());
            }
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
        }
    }

    private static void testCpiReturnsValueWhenCpIdxIsNonZero() {
        ClassFileAssembler assembler = new ClassFileAssembler();
        // Reflection to set cpIdx to a non-zero value, e.g., 5
        try {
            java.lang.reflect.Field cpIdxField = ClassFileAssembler.class.getDeclaredField("cpIdx");
            cpIdxField.setAccessible(true);
            cpIdxField.setShort(assembler, (short) 5);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.err.println("Reflection setup failed: " + e);
            return;
        }
        short result = assembler.cpi();
        if (result == 5) {
            System.out.println("Passed: cpi() returns correct cpIdx value when non-zero");
        } else {
            System.err.println("Failed: cpi() returned " + result + " instead of 5");
        }
    }
}
