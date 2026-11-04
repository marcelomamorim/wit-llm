/*
 * @test
 * @summary Verifies ChoiceCallback.setSelectedIndexes rejects selection when multiple selections are not allowed and stores selections when allowed
 * @run main ChoiceCallbackWitupTest
 */

import javax.security.auth.callback.ChoiceCallback;

public class ChoiceCallbackWitupTest {

    public static void main(String[] args) {
        String[] choices = {"a", "b", "c"};

        ChoiceCallback single = new ChoiceCallback("prompt", choices, 0, false);
        boolean thrown = false;
        try {
            single.setSelectedIndexes(new int[]{1});
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException when multiple selections are not allowed");
        }

        ChoiceCallback multi = new ChoiceCallback("prompt", choices, 0, true);
        multi.setSelectedIndexes(new int[]{0, 2});
        int[] got = multi.getSelectedIndexes();
        if (got == null || got.length != 2 || got[0] != 0 || got[1] != 2) {
            throw new RuntimeException("Selections {0, 2} were not stored correctly");
        }

        multi.setSelectedIndexes(new int[]{1});
        got = multi.getSelectedIndexes();
        if (got == null || got.length != 1 || got[0] != 1) {
            throw new RuntimeException("Selections were not replaced by {1}");
        }

        multi.setSelectedIndexes(new int[0]);
        got = multi.getSelectedIndexes();
        if (got == null || got.length != 0) {
            throw new RuntimeException("Empty selection array was not stored");
        }
    }
}
