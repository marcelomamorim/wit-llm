/* @test    @summary Test replaceAll method in ImmutableCollections to ensure it throws UnsupportedOperationException    @run main ImmutableCollectionsWitupTest */

import java.util.function.BiFunction;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testReplaceAllThrows();
    }

    public static void testReplaceAllThrows() {
        try {
            java.util.ImmutableCollections.replaceAll(new BiFunction<Object, Object, Object>() {
                @Override
                public Object apply(Object t, Object u) {
                    return u;
                }
            });
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: replaceAll correctly threw UnsupportedOperationException.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
