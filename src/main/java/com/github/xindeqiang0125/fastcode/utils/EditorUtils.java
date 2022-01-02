package com.github.xindeqiang0125.fastcode.utils;

import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorModificationUtil;

public class EditorUtils {
    public static void insertStringAtCaretThenSelect(Editor editor, String target) {
        if (editor == null || target == null) {
            return;
        }
        Caret primaryCaret = editor.getCaretModel().getPrimaryCaret();
        int start = primaryCaret.getSelectionStart();
        int end = primaryCaret.getSelectionEnd();
        EditorModificationUtil.insertStringAtCaret(editor, target);
        primaryCaret.setSelection(start, start + target.length());
    }
}
