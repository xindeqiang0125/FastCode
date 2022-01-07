package com.github.xindeqiang0125.fastcode.actions;

import com.github.xindeqiang0125.fastcode.utils.PsiUtils;
import com.github.xindeqiang0125.fastcode.utils.TemplateUtils;
import com.google.common.collect.Lists;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMember;
import com.intellij.psi.codeStyle.NameUtil;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.java.generate.element.ClassElement;
import org.jetbrains.java.generate.element.Element;
import org.jetbrains.java.generate.element.ElementFactory;
import org.jetbrains.java.generate.element.ElementUtils;
import org.jetbrains.java.generate.element.FieldElement;
import org.jetbrains.java.generate.element.GenerationHelper;
import org.jetbrains.java.generate.velocity.VelocityFactory;

import java.io.StringWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class TemplateCodeAction extends EditorAction {

    @Override
    protected void execute(Editor editor, Project project, PsiFile psiFile) {
        PsiClass clazz = PsiUtils.findCaretClass(editor, psiFile);
        if (clazz == null) return;
        VelocityEngine engine = VelocityFactory.getVelocityEngine();
        String templateText = TemplateUtils.getTemplateText(getClass());
        Collection<PsiMember> selectedMembers = Lists.newArrayList();
        clazz.acceptChildren(new PsiElementVisitor() {
            @Override
            public void visitElement(@NotNull PsiElement element) {
                super.visitElement(element);
                if (element instanceof PsiMember) {
                    selectedMembers.add((PsiMember) element);
                }
            }
        });
        VelocityContext context = getVelocityContext(project, clazz, selectedMembers);
        customVelocityContext(context, clazz, editor, project, psiFile);
        StringWriter sw = new StringWriter();
        engine.evaluate(context, sw, "Generate Code", templateText);
        doExecute(clazz, sw.toString(), editor, project, psiFile);
    }

    protected abstract void doExecute(PsiClass clazz, String code, Editor editor, Project project, PsiFile psiFile);

    protected void customVelocityContext(VelocityContext context, PsiClass clazz, Editor editor, Project project, PsiFile psiFile) {

    }

    @NotNull
    private VelocityContext getVelocityContext(Project project, PsiClass clazz, Collection<PsiMember> selectedMembers) {
        VelocityContext context = new VelocityContext();
        // field information
        final List<FieldElement> fieldElements = ElementUtils.getOnlyAsFieldElements(selectedMembers, Collections.emptyList(), true);
        context.put("fields", fieldElements);
        if (fieldElements.size() == 1) {
            context.put("field", fieldElements.get(0));
        }
        // method information
        context.put("methods", ElementUtils.getOnlyAsMethodElements(selectedMembers));
        // element information (both fields and methods)
        List<Element> elements = ElementUtils.getOnlyAsFieldAndMethodElements(selectedMembers, Collections.emptyList(), true);
        context.put("members", elements);
        // class information
        ClassElement ce = ElementFactory.newClassElement(clazz);
        context.put("class", ce);
        context.put("classname", ce.getName());
        context.put("project", project);
        context.put("helper", GenerationHelper.class);
        context.put("StringUtil", StringUtil.class);
        context.put("NameUtil", NameUtil.class);
        return context;
    }
}
