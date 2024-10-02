package com.annotation;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@SupportedAnnotationTypes("com.annotation.UseCrudUseCase")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@AutoService(Processor.class)
public class UseCrudUseCaseProcessor extends AbstractProcessor {

    public void print(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedClasses = roundEnv.getElementsAnnotatedWith(annotation);
            if (annotatedClasses.size() == 1) {
                boolean isMainClass = annotatedClasses.stream()
                        .findFirst()
                        .map(element2 -> element2
                                .getEnclosedElements()
                                .stream()
                                .anyMatch(element -> element.toString().equals("main(java.lang.String[])"))
                        )
                        .orElse(false);

                if (!isMainClass) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Class must be main class to use @UseCrudUseCase annotation");
                }

            }

            String className = annotatedClasses.stream().findAny().get().toString();

            try {
                writeUseCaseDependencies("com.annotation.domain.entity.BaseEntity", generateBaseEntity());
                writeUseCaseDependencies("com.annotation.domain.entity.Page", generatePageEntity());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return true;
    }

    private String generatePageEntity() {
        String useCaseFile = """
                package com.annotation.domain.entity;
                
                import java.util.List;
                
                public class Page<K, T extends BaseEntity<K>> {
                    private List<K> values;
                    private int pageSize;
                    private int pageIndex;
                
                    public List<K> getValues() {
                        return values;
                    }
                
                    public void setValues(List<K> values) {
                        this.values = values;
                    }
                
                    public int getPageSize() {
                        return pageSize;
                    }
                
                    public void setPageSize(int pageSize) {
                        this.pageSize = pageSize;
                    }
                
                    public int getPageIndex() {
                        return pageIndex;
                    }
                
                    public void setPageIndex(int pageIndex) {
                        this.pageIndex = pageIndex;
                    }
                }
                
                """;

        return useCaseFile;
    }

    private String generateBaseEntity() {
        String useCaseFile = """
                package com.annotation.domain.entity;
                
                public class BaseEntity<T> {
                    private T id;
                
                    public T getId() {
                        return id;
                    }
                
                    public void setId(T id) {
                        this.id = id;
                    }
                }
                """;

        return useCaseFile;
    }

    public String toCamelCase(String input) {
        // Divide a string usando espaços, underscores ou hífens como delimitadores
        String[] words = input.split("[\\s_-]+");

        // Usa StringBuilder para construir a string resultante
        StringBuilder camelCaseString = new StringBuilder();

        // Itera sobre as palavras
        for (int i = 0; i < words.length; i++) {
            String word = words[i].toLowerCase(); // Converte tudo para minúsculas inicialmente
            if (i == 0) {
                // Primeira palavra, mantém como minúscula
                camelCaseString.append(word);
            } else {
                // Para as demais, a primeira letra em maiúscula e o restante em minúscula
                camelCaseString.append(word.substring(0, 1).toUpperCase())
                        .append(word.substring(1));
            }
        }

        return camelCaseString.toString();
    }

    private void writeUseCaseDependencies(
            String className, String value)
            throws IOException {

        String packageName = null;
        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = className.substring(0, lastDot);
        }

        String simpleClassName = className.substring(lastDot + 1);
        String generatedClassName = "com.annotation.domain.entity." + simpleClassName;

        String builderSimpleClassName = generatedClassName
                .substring(lastDot + 1);

        JavaFileObject builderFile = processingEnv.getFiler()
                .createSourceFile(generatedClassName);

        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {

            out.println(value);
        }
    }

}