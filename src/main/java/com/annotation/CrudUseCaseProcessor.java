package com.annotation;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Set;

@SupportedAnnotationTypes("com.annotation.CrudUseCase")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@AutoService(Processor.class)
public class CrudUseCaseProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedClasses = roundEnv.getElementsAnnotatedWith(annotation);

            String className = annotatedClasses.stream().findAny().get().toString();

            try {
                writeBuilderFile(className);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return true;
    }

    private String generateUseCase(String className) {
        String simpleName = className.substring(className.lastIndexOf('.') + 1);
        String useCaseFile = MessageFormat.format("""
                package com.annotation.domain.usecase;
                
                import com.annotation.domain.entity.BaseEntity;
                import com.annotation.domain.entity.Page;
                import com.annotation.domain.entity.Pageable;
                
                import {0};
                import java.util.List;
                import java.util.UUID;
                import java.util.Optional;
                
                public interface {1}UseCase '{'
                   {1} create({1} {2});
                   {1} update({1} {2});
                   void delete({1} {2});
                   List<{1}> findAll();
                   Page<{1}> findAll(Pageable pageable);
                   Optional<{1}> findById(UUID id);
                '}'
                """, className, simpleName, toCamelCase(simpleName));

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

    private void writeBuilderFile(
            String className)
            throws IOException {

        String packageName = null;
        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = className.substring(0, lastDot);
        }

        String simpleClassName = className.substring(lastDot + 1);
        String generatedClassName = "com.annotation.domain.usecase." + simpleClassName + "UseCase";
        String builderSimpleClassName = generatedClassName
                .substring(lastDot + 1);

        JavaFileObject builderFile = processingEnv.getFiler()
                .createSourceFile(generatedClassName);

        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {

            out.println(generateUseCase(className));
        }
    }

}