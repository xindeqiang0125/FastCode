package com.github.xindeqiang0125.fastcode.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.DocCommandGroupId;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public abstract class EditorAction extends AnAction {
    
    @Override
    public void update(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        boolean visible = isVisible(editor, project, psiFile);
        e.getPresentation().setEnabledAndVisible(visible);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        if (editor == null) {
            return;
        }
        CommandProcessor.getInstance().executeCommand(project, () -> {
            WriteCommandAction.runWriteCommandAction(project, () ->
                    execute(editor, project, psiFile)
            );
        }, this.getClass().getName(), DocCommandGroupId.noneGroupId(editor.getDocument()));
    }

    protected boolean isVisible(Editor editor, Project project, PsiFile psiFile) {
        if (editor == null) return false;
        if (project == null) return false;
        if (psiFile == null) return false;
        return true;
    }
    protected abstract void execute(Editor editor, Project project, PsiFile psiFile);
}
