package com.github.xindeqiang0125.fastcode.actions;

import com.intellij.openapi.actionSystem.ActionGroupUtil;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.application.impl.LaterInvocator;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import org.jetbrains.annotations.NotNull;

public class FastCodeGenerateAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull final AnActionEvent e) {
        DataContext dataContext = e.getDataContext();
        final ListPopup popup =
                JBPopupFactory.getInstance().createActionGroupPopup(
                        "FastCodeGenerate",
                        getGroup(),
                        dataContext,
                        JBPopupFactory.ActionSelectionAid.SPEEDSEARCH,
                        false);

        popup.showInBestPositionFor(dataContext);
    }

    @Override
    public void update(@NotNull AnActionEvent event){
        Presentation presentation = event.getPresentation();
        if (ActionPlaces.isPopupPlace(event.getPlace())) {
            presentation.setEnabledAndVisible(isEnabled(event));
        }
        else {
            presentation.setEnabled(isEnabled(event));
        }
    }

    private static boolean isEnabled(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        if (project == null) {
            return false;
        }
        Editor editor = event.getData(CommonDataKeys.EDITOR);
        if (editor == null) {
            return false;
        }
        boolean groupEmpty = ActionGroupUtil.isGroupEmpty(getGroup(), event, LaterInvocator.isInModalContext());
        return !groupEmpty;
    }

    private static DefaultActionGroup getGroup() {
        return (DefaultActionGroup) ActionManager.getInstance().getAction("FastCodeGenerateGroup");
    }
}
