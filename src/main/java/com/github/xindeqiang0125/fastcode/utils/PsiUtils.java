package com.github.xindeqiang0125.fastcode.utils;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiParameterList;
import com.intellij.psi.PsiStatement;
import com.intellij.psi.PsiType;
import com.intellij.psi.PsiVariable;
import com.intellij.psi.SyntheticElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class PsiUtils {


    // 判断类方法

    /**
     * 校验是否含有拷贝构造方法
     */
    public static boolean hasCopyConstructor(PsiClass psiClass) {
        if (psiClass == null || psiClass.getName() == null) return false;
        @NotNull PsiMethod[] constructors = psiClass.getConstructors();
        for (PsiMethod constructor : constructors) {
            PsiParameterList parameterList = constructor.getParameterList();
            if (parameterList.getParametersCount() == 1) {
                PsiParameter parameter = parameterList.getParameter(0);
                if (parameter == null) return false;
                if (parameter.getType().equalsToText(psiClass.getName())) {
                    return true;
                }
            }
        }
        return false;
    }


    // 获取类方法

    /**
     * 获取光标所在的类
     */
    public static PsiClass findCaretClass(Editor editor, PsiFile psiFile) {
        PsiClass psiClass = findPsiElement(editor, psiFile, PsiClass.class);
        if (psiClass == null) return null;
        return psiClass instanceof SyntheticElement ? null : psiClass;
    }

    /**
     * 获取光标所在的变量
     */
    public static PsiVariable findCaretVariable(Editor editor, PsiFile psiFile) {
        return findPsiElement(editor, psiFile, PsiVariable.class);
    }

    /**
     * 获取光标所在的方法
     */
    public static PsiMethod findCaretMethod(Editor editor, PsiFile psiFile) {
        return findPsiElement(editor, psiFile, PsiMethod.class);
    }

    /**
     * 获取光标所在的函数体
     */
    public static PsiCodeBlock findCaretCodeBlock(Editor editor, PsiFile psiFile) {
        return findPsiElement(editor, psiFile, PsiCodeBlock.class);
    }

    /**
     * 获取光标所在的函数体
     */
    public static PsiStatement findCaretStatement(Editor editor, PsiFile psiFile) {
        return findPsiElement(editor, psiFile, PsiStatement.class);
    }

    @Nullable
    private static <T extends PsiElement> T findPsiElement(Editor editor, PsiFile psiFile, Class<T> tClass) {
        if (editor == null || psiFile == null) {
            return null;
        }
        int offset = editor.getCaretModel().getOffset();
        PsiElement element = psiFile.findElementAt(offset);
        if (element == null) {
            return null;
        } else {
            return PsiTreeUtil.getParentOfType(element, tClass);
        }
    }

    /**
     * 获取类型对应的规范文本(带包名)
     */
    public static String getCanonicalText(PsiType type) {
        return type.getCanonicalText();
    }

    /**
     * 获取类型对应的文本
     */
    public static String getPresentableText(PsiType type) {
        return type.getPresentableText();
    }

    // 遍历类方法

    /**
     * 遍历PsiClass中的PsiField
     */
    public static void iteratorFields(PsiClass psiClass, Consumer<PsiField> consumer) {
        if (psiClass == null) {
            return;
        }
        for (PsiField psiField : psiClass.getFields()) {
            consumer.accept(psiField);
        }
    }

    /**
     * 遍历PsiClass中的PsiField(含父类)
     */
    public static void iteratorAllFields(PsiClass psiClass, Consumer<PsiField> consumer) {
        if (psiClass == null) {
            return;
        }
        for (PsiField psiField : psiClass.getAllFields()) {
            consumer.accept(psiField);
        }
    }

    /**
     * 遍历PsiClass中的PsiMethod(含父类)
     */
    public static void iteratorAllMethods(PsiClass psiClass, Consumer<PsiMethod> consumer) {
        if (psiClass == null) {
            return;
        }
        for (PsiMethod method : psiClass.getAllMethods()) {
            consumer.accept(method);
        }
    }

    //


    // 创建类方法

    /**
     * 创建默认构造方法
     */
    public static PsiMethod createConstructor(Project project) {
        PsiElementFactory factory = JavaPsiFacade.getElementFactory(project);
        return factory.createConstructor();
    }

    /**
     * 创建方法
     */
    public static PsiMethod createMethod(Project project, String signature, String content) {
        PsiElementFactory factory = JavaPsiFacade.getElementFactory(project);
        return factory.createMethodFromText(signature + "{" + content + "}", null);
    }

    public static PsiMethod createMethod(Project project, String methodText) {
        PsiElementFactory factory = JavaPsiFacade.getElementFactory(project);
        return factory.createMethodFromText(methodText, null);
    }

    /**
     * 创建语句
     */
    public static PsiStatement createStatement(Project project, String content) {
        PsiElementFactory factory = JavaPsiFacade.getElementFactory(project);
        return factory.createStatementFromText(content, null);
    }

    /**
     * 创建代码块
     */
    public static PsiCodeBlock createCodeBlock(Project project, String content) {
        PsiElementFactory factory = JavaPsiFacade.getElementFactory(project);
        return factory.createCodeBlockFromText(content, null);
    }
}
