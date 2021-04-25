package com.forever.test.generate;

import com.sun.source.tree.Tree;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Names;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Arrays;
import java.util.Set;

/**
 * @author WJX
 * @date 2021/1/27 10:30
 */
@SupportedAnnotationTypes("com.test.Transform")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MyProcessor extends AbstractProcessor {

    private Names names;
    private JavacTrees trees;
    private Messager messager;
    private TreeMaker treeMaker;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();

        // 创建标识符
        this.names = Names.instance(context);
        // 编译期log
        this.messager = processingEnv.getMessager();
        // 创建AST节点
        this.treeMaker = TreeMaker.instance(context);
        // 处理抽象语法树
        this.trees = JavacTrees.instance(processingEnv);
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(Transform.class);

        set.forEach(element -> {

            messager.printMessage(Diagnostic.Kind.NOTE, element.getEnclosedElements().size() + "");

            for (Element element1 : element.getEnclosedElements()) {
                messager.printMessage(Diagnostic.Kind.NOTE, element1.getEnclosedElements().size() + "");
            }

            JCTree jcTree = trees.getTree(element);

            jcTree.accept(new TreeTranslator() {
                @Override
                public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
                    List<JCTree.JCMethodDecl> jcMethodDeclList = List.nil();
                    List<JCTree.JCVariableDecl> jcVariableDeclList = List.nil();

                    for (JCTree tree : jcClassDecl.defs) {
                        if (tree.getKind().equals(Tree.Kind.VARIABLE)) {
                            JCTree.JCVariableDecl jcVariableDecl = (JCTree.JCVariableDecl) tree;
                            jcVariableDeclList = jcVariableDeclList.append(jcVariableDecl);
                        } else if (tree.getKind().equals(Tree.Kind.METHOD)) {
                            JCTree.JCMethodDecl jcMethodDecl = (JCTree.JCMethodDecl) tree;
                            jcMethodDeclList = jcMethodDeclList.append(jcMethodDecl);
                        }
                    }

                    ListBuffer<JCTree.JCStatement> testStatement = new ListBuffer<>();
                    testStatement.append(treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER), names.fromString("string"), treeMaker.Ident(names.fromString("String")), treeMaker.Literal(element.getSimpleName().toString() + "{")));

                    jcVariableDeclList.forEach(jcVariableDecl -> {
                        messager.printMessage(Diagnostic.Kind.NOTE, jcVariableDecl.getName() + " has been processed");
                        testStatement.append(treeMaker.Exec(treeMaker.Assignop(JCTree.Tag.PLUS_ASG, treeMaker.Ident(names.fromString("string")), treeMaker.Binary(JCTree.Tag.PLUS,treeMaker.Literal(jcVariableDecl.name.toString() + "="), treeMaker.Ident(jcVariableDecl.name)))));
                        testStatement.append(treeMaker.Exec(treeMaker.Assignop(JCTree.Tag.PLUS_ASG, treeMaker.Ident(names.fromString("string")), treeMaker.Literal(","))));
                    });

                    testStatement.append(treeMaker.Exec(treeMaker.Assignop(JCTree.Tag.PLUS_ASG, treeMaker.Ident(names.fromString("string")), treeMaker.Literal("}"))));

                    testStatement.append(treeMaker.Return(treeMaker.Ident(names.fromString("string"))));

                    JCTree.JCBlock body = treeMaker.Block(0, testStatement.toList());


                    JCTree.JCMethodDecl method = treeMaker.MethodDef(treeMaker.Modifiers(Flags.PUBLIC), // 访问标志
                            names.fromString("toString"), // 方法名
                            treeMaker.Ident(names.fromString("String")), // 返回类型
                            List.nil(), // 泛型参数列表
                            List.nil(), // 参数列表
                            List.nil(), // 异常声明列表
                            body, // 方法体
                            null
                    );

                    jcMethodDeclList.forEach(jcMethodDecl -> {

                        for (JCTree.JCStatement statement : jcMethodDecl.body.stats) {
                            messager.printMessage(Diagnostic.Kind.NOTE, Arrays.toString(statement.getKind().asInterface().getAnnotations()));
                        }

                    });


                    jcClassDecl.defs = jcClassDecl.defs.prepend(method);
                    super.visitClassDef(jcClassDecl);
                }

            });
        });

        return true;
    }





}
