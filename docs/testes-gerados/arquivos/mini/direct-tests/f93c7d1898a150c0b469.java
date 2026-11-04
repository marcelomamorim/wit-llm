/*
 * @test
 * @summary Test ChoiceCallback.setSelectedIndexes behavior with multipleSelectionsAllowed true and false
 * @run main javax.security.auth.callback.ChoiceCallbackWitupTest
 */

import javax.security.auth.callback.ChoiceCallback;

public class ChoiceCallbackWitupTest {
    public static void main(String[] args) throws Exception {
        testSetSelectedIndexesWithMultipleSelectionsAllowed();
        testSetSelectedIndexesWithoutMultipleSelectionsAllowed();
    }

    private static void testSetSelectedIndexesWithMultipleSelectionsAllowed() {
        String prompt = "Select options:";
        String[] choices = {"A", "B", "C"};
        int defaultChoice = 0;
        boolean multipleSelectionsAllowed = true;

        ChoiceCallback cc = new ChoiceCallback(prompt, choices, defaultChoice, multipleSelectionsAllowed);

        int[] selections = {1, 2};
        cc.setSelectedIndexes(selections);

        int[] returnedSelections = cc.getSelectedIndexes();
        if (returnedSelections == null || returnedSelections.length != 2 || returnedSelections[0] != 1 || returnedSelections[1] != 2) {
            throw new RuntimeException("setSelectedIndexes did not set selections correctly when multipleSelectionsAllowed is true");
        }
    }

    private static void testSetSelectedIndexesWithoutMultipleSelectionsAllowed() {
        String prompt = "Select one:";
        String[] choices = {"X", "Y"};
        int defaultChoice = 1;
        boolean multipleSelectionsAllowed = false;

        ChoiceCallback cc = new ChoiceCallback(prompt, choices, defaultChoice, multipleSelectionsAllowed);

        int[] selections = {0};

        boolean threw = false;
        try {
            cc.setSelectedIndexes(selections);
        } catch (UnsupportedOperationException e) {
            threw = true;
        }

        if (!threw) {
            throw new RuntimeException("setSelectedIndexes did not throw UnsupportedOperationException when multipleSelectionsAllowed is false");
        }
    }
}
