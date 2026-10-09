/* @test    @summary Test for sun.security.util.BitArray.get(int index) handling valid and invalid indices    @run main BitArrayWitupTest_Pf43679ad95b3 */

import sun.security.util.BitArray;

public class BitArrayWitupTest_Pf43679ad95b3 {

    public static void main(String[] args) {
        testValidIndex();
        testNegativeIndex();
        testIndexEqualToLength();
        testIndexGreaterThanLength();
    }

    static void testValidIndex() {
        try {
            boolean[] bits = {true, false, true};
            BitArray ba = new BitArray(bits);
            boolean result = ba.get(0);
            boolean result2 = ba.get(2);
            if (result != true || result2 != true) {
                throw new RuntimeException("Unexpected value for valid indices");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for valid indices", e);
        }
    }

    static void testNegativeIndex() {
        try {
            boolean[] bits = {true, false};
            BitArray ba = new BitArray(bits);
            ba.get(-1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException not thrown for negative index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type for negative index", e);
        }
    }

    static void testIndexEqualToLength() {
        try {
            boolean[] bits = {true, false};
            BitArray ba = new BitArray(bits);
            ba.get(ba.length); // length is private, but assuming method or reflection access
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException not thrown for index == length");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type for index == length", e);
        }
    }

    static void testIndexGreaterThanLength() {
        try {
            boolean[] bits = {true, false};
            BitArray ba = new BitArray(bits);
            // As length is private, create a subclass or use reflection if needed
            int invalidIndex = 9999; // Arbitrary large index
            // Using reflection because length is private
            java.lang.reflect.Method getMethod = BitArray.class.getMethod("get", int.class);
            getMethod.invoke(ba, invalidIndex);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException not thrown for large index");
        } catch (java.lang.reflect.InvocationTargetException e) {
            if (e.getCause() instanceof ArrayIndexOutOfBoundsException) {
                // Expected
            } else {
                throw new RuntimeException("Unexpected exception cause for large index", e.getCause());
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type for large index", e);
        }
    }
}
