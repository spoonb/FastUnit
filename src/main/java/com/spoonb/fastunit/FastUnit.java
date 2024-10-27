package com.spoonb.fastunit;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;

public class FastUnit extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) return;

        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        if (!(psiFile instanceof PsiJavaFile)) return;

        PsiJavaFile javaFile = (PsiJavaFile) psiFile;
        PsiClass[] classes = javaFile.getClasses();
        if (classes.length == 0) return;

        PsiClass mainClass = classes[0];

        String name = mainClass.getName();
        System.out.printf("class -> %s\n", name);

        PsiMethod[] methods = mainClass.getMethods();
        for (PsiMethod method : methods) {
            System.out.printf("method -> %s\n", method.getName());
        }

        PsiField[] allFields = mainClass.getAllFields();
        for (PsiField allField : allFields) {
            System.out.printf("field -> %s\n", allField.getName());
        }
    }
}
