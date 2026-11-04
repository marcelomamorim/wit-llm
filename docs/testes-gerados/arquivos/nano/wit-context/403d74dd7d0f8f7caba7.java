/*
 * @test
 * @summary Test for ImmutableCollections.remove(int) method to verify exception throwing behavior.
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testRemoveThrowsUOE();
    }

    public static void testRemoveThrowsUOE() {
        try {
            ImmutableCollections col = new ImmutableCollections();
            // Since remove is a method of a class, but in the provided code it's static or instance?
            // The method is an instance method, but the class is package-private. For testing, assume a subclass or reflection.
            // Given the context, we simulate calling the method via a subclass or reflection.
            // But as per the code, remove is an instance method, so we need an instance.
            // Since the class is package-private, we cannot instantiate directly. For test, assume a subclass or mock.
            // Alternatively, test via a subclass or reflection.
            // Here, we will create an anonymous subclass for testing purposes.
            class TestImmutableCollections extends ImmutableCollections {
                public E remove(int index) {
                    return super.remove(index);
                }
            }
            TestImmutableCollections testInstance = new TestImmutableCollections();
            testInstance.remove(0);
            // If no exception, fail the test
            System.err.println("Failed: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception
            System.out.println("Passed: UnsupportedOperationException thrown as expected.");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
        }
    }
}
