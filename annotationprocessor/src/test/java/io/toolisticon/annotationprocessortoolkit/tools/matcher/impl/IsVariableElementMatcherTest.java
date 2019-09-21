package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.tools.ElementUtils;
import io.toolisticon.annotationprocessortoolkit.tools.MessagerUtils;
import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.compiletesting.CompileTestBuilder;
import io.toolisticon.compiletesting.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.List;


/**
 * Unit test for {@link IsVariableElementMatcher}.
 */

public class IsVariableElementMatcherTest {

    private String field = "sds";

    private CompileTestBuilder.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationClassAttributeTestClass.java"));

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @Test
    public void checkMatchingVariableElement_field() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // find field
                List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsOfKind(TypeUtils.TypeRetrieval.getTypeElement(IsVariableElementMatcherTest.class), ElementKind.FIELD);
                MatcherAssert.assertThat("Precondition: should have found one field", result.size() >= 1);
                MatcherAssert.assertThat("Precondition: found method has to be of type VariableElement", result.get(0) instanceof VariableElement);

                MatcherAssert.assertThat("Should return true for field : ", CoreMatchers.IS_VARIABLE_ELEMENT.getMatcher().check(result.get(0)));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void checkMismatchingVariableElement_class() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("Should return false for non VariableElement : ", !CoreMatchers.IS_VARIABLE_ELEMENT.getMatcher().check(element));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void checkMismatchingVariableElement_nullValuedElement() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("Should return false for null valued element : ", !CoreMatchers.IS_VARIABLE_ELEMENT.getMatcher().check(null));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

}
