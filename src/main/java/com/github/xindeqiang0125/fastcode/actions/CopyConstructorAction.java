package com.github.xindeqiang0125.fastcode.actions;

import com.github.xindeqiang0125.fastcode.utils.PsiUtils;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;

public class CopyConstructorAction extends TemplateCodeAction {

    @Override
    protected boolean isVisible(Editor editor, Project project, PsiFile psiFile) {
        PsiClass clazz = PsiUtils.findCaretClass(editor, psiFile);
        if (clazz == null) return false;
        PsiField[] fields = clazz.getFields();
        if (fields.length == 0) return false;
        return super.isVisible(editor, project, psiFile);
    }

    @Override
    protected void doExecute(PsiClass clazz, String code, Editor editor, Project project, PsiFile psiFile) {
        PsiMethod method = PsiUtils.createMethod(project, code);
        clazz.add(method);
    }
}
