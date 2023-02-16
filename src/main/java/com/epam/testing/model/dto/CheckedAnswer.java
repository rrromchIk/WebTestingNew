package com.epam.testing.model.dto;

/**
 * Checked answer dto class. Used to store answers with checked state,
 * that means user have answered it earlier.
 */
public class CheckedAnswer {
    private final String text;
    private final boolean checked;

    public CheckedAnswer(String text, boolean checked) {
        this.text = text;
        this.checked = checked;
    }

    public String getText() {
        return text;
    }

    public boolean getChecked() {
        return checked;
    }
}
