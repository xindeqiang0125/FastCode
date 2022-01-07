package com.github.xindeqiang0125.fastcode.postfix;

import com.intellij.codeInsight.template.postfix.templates.JavaPostfixTemplateProvider;
import com.intellij.codeInsight.template.postfix.templates.editable.JavaEditablePostfixTemplate;
import com.intellij.pom.java.LanguageLevel;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class ToIntPostfixTemplate extends JavaEditablePostfixTemplate {

    public ToIntPostfixTemplate(@NotNull JavaPostfixTemplateProvider provider) {
        super("toint",
                "Integer.parseInt($EXPR$)",
                "Integer.parseInt(expr)",
                Collections.emptySet(),
                LanguageLevel.JDK_1_3,
                true, provider);
    }

    @Override
    public boolean isBuiltin() {
        return false;
    }
}
