package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.TestAnnotation;
import io.toolisticon.annotationprocessortoolkit.tools.MessagerUtils;
import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.compiletesting.CompileTestBuilder;
import io.toolisticon.compiletesting.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import javax.lang.model.element.TypeElement;


/**
 * Unit test for {@link ByRawTypeMatcher}.
 */
public class ByRawTypeMatcherTest {


    private CompileTestBuilder.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationClassAttributeTestClass.java"));


    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @Test
    public void getStringRepresentationOfPassedCharacteristic_happyPath() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("Should return cannonical class name of annotation class", CoreMatchers.BY_RAW_TYPE.getMatcher().getStringRepresentationOfPassedCharacteristic(ByRawTypeMatcherTest.class).equals(ByRawTypeMatcherTest.class.getCanonicalName()));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void getStringRepresentationOfPassedCharacteristic_passedNullValue_shouldReturnNull() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("Should return null for null valued parameter", CoreMatchers.BY_RAW_TYPE.getMatcher().getStringRepresentationOfPassedCharacteristic(null) == null);

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void checkForMatchingCharacteristic_match() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeElement tmpElement = TypeUtils.TypeRetrieval.getTypeElement(ByRawTypeMatcherTest.class);
                MatcherAssert.assertThat("Should find match correctly", CoreMatchers.BY_RAW_TYPE.getMatcher().checkForMatchingCharacteristic(tmpElement, ByRawTypeMatcherTest.class));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void checkForMatchingCharacteristic_mismatch() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeElement tmpElement = TypeUtils.TypeRetrieval.getTypeElement(String.class);
                MatcherAssert.assertThat("Should find match correctly", !CoreMatchers.BY_RAW_TYPE.getMatcher().checkForMatchingCharacteristic(tmpElement, ByRawTypeMatcherTest.class));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void checkForMatchingCharacteristic_nullValuedElement() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("Should return false in case of null valued element", !CoreMatchers.BY_RAW_TYPE.getMatcher().checkForMatchingCharacteristic(null, TestAnnotation.class));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void checkForMatchingCharacteristic_nullValuedRawType() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("Should return false in case of null valued annotation", !CoreMatchers.BY_RAW_TYPE.getMatcher().checkForMatchingCharacteristic(element, null));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void checkForMatchingCharacteristic_nullValuedElementAndRawType() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("Should return false in case of null valued parameters", !CoreMatchers.BY_RAW_TYPE.getMatcher().checkForMatchingCharacteristic(null, null));


            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }


}





