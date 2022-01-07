package com.github.xindeqiang0125.fastcode.postfix;

import com.google.common.collect.Sets;
import com.intellij.codeInsight.template.postfix.templates.JavaPostfixTemplateProvider;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class FastCodePostfixTemplateProvider extends JavaPostfixTemplateProvider {

    private final HashSet<PostfixTemplate> postfixTemplates = Sets.newHashSet(
            new ToIntPostfixTemplate(this)
    );

    @Override
    public @NotNull String getId() {
        return "FastCode.java";
    }

    @Override
    public @Nullable String getPresentableName() {
        return "FastCode";
    }

    @Override
    public @NotNull Set<PostfixTemplate> getTemplates() {
        return postfixTemplates;
    }

}
