/*
 * @test
 * @summary Verify ChoiceCallback.setSelectedIndexes rejects selections when multiple selections are not allowed
 * @run main ChoiceCallbackWitupTest
 */

import javax.security.auth.callback.ChoiceCallback;

public class ChoiceCallbackWitupTest {

    public static void main(String[] args) {
        ChoiceCallback single = new ChoiceCallback("Pick", new String[]{"a", "b"}, 0, false);

        boolean thrown = false;
        try {
            single.setSelectedIndexes(new int[]{1});
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException when multiple selections are not allowed");
        }

        thrown = false;
        try {
            single.setSelectedIndexes(null);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException for null selections when multiple selections are not allowed");
        }

        ChoiceCallback multi = new ChoiceCallback("Pick", new String[]{"a", "b"}, 0, true);
        multi.setSelectedIndexes(new int[]{0, 1});
        multi.setSelectedIndexes(null);
    }
}
