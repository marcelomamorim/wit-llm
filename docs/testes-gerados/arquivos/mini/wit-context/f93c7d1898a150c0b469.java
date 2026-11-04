/*
 * @test
 * @summary Test ChoiceCallback.setSelectedIndexes behavior including exception when multiple selections not allowed
 * @run main ChoiceCallbackWitupTest
 */

import javax.security.auth.callback.ChoiceCallback;

public class ChoiceCallbackWitupTest {
    public static void main(String[] args) {
        testSetSelectedIndexesWithMultipleSelectionsAllowed();
        testSetSelectedIndexesThrowsWhenMultipleSelectionsNotAllowed();
    }

    private static void testSetSelectedIndexesWithMultipleSelectionsAllowed() {
        String prompt = "Select options:";
        String[] choices = {"One", "Two", "Three"};
        int defaultChoice = 1;
        boolean multipleSelectionsAllowed = true;

        ChoiceCallback cc = new ChoiceCallback(prompt, choices, defaultChoice, multipleSelectionsAllowed);

        int[] selections = {0, 2};
        cc.setSelectedIndexes(selections);

        int[] returnedSelections = cc.getSelectedIndexes();
        if (returnedSelections == null || returnedSelections.length != selections.length) {
            throw new RuntimeException("Returned selections length mismatch");
        }
        for (int i = 0; i < selections.length; i++) {
            if (returnedSelections[i] != selections[i]) {
                throw new RuntimeException("Returned selections content mismatch at index " + i);
            }
        }
    }

    private static void testSetSelectedIndexesThrowsWhenMultipleSelectionsNotAllowed() {
        String prompt = "Select one option:";
        String[] choices = {"Alpha", "Beta"};
        int defaultChoice = 0;
        boolean multipleSelectionsAllowed = false;

        ChoiceCallback cc = new ChoiceCallback(prompt, choices, defaultChoice, multipleSelectionsAllowed);

        int[] selections = {1};

        boolean thrown = false;
        try {
            cc.setSelectedIndexes(selections);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException was not thrown when multiple selections not allowed");
        }
    }
}
