/*
 * @test
 * @summary Test getByte method of UnsafeQualifiedStaticCharacterFieldAccessorImpl throws expected exception
 * @run main UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticCharacterFieldAccessorImpl;

public class UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest {
    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest.class.getDeclaredFields()[0];
            UnsafeQualifiedStaticCharacterFieldAccessorImpl accessor = new UnsafeQualifiedStaticCharacterFieldAccessorImpl(dummyField, false);

            // The getByte method is expected to always throw IllegalArgumentException
            try {
                accessor.getByte(null);
                throw new RuntimeException("getByte did not throw IllegalArgumentException as expected");
            } catch (IllegalArgumentException expected) {
                // Expected exception caught, test passes
            }

            System.out.println("Test passed.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Test failed due to unexpected exception: " + e);
        }
    }

    // Dummy field to obtain a Field instance for constructor
    @SuppressWarnings("unused")
    private static final char dummyCharField = 'a';
}
