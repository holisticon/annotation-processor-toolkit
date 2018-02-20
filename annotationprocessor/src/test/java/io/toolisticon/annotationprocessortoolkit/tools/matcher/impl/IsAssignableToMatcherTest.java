package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.List;


/**
 * Unit test for {@link IsAssignableToMatcher}.
 */
@RunWith(Parameterized.class)
public class IsAssignableToMatcherTest extends AbstractAnnotationProcessorUnitTest {

    public IsAssignableToMatcherTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{


                        {
                                "check : matching assignable to case)",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(
                                                new AbstractUnitTestAnnotationProcessorClass() {
                                                    @Override
                                                    protected void testCase(TypeElement element) {

                                                        MatcherAssert.assertThat("Should return true for matching assignable to case : ", CoreMatchers.IS_ASSIGNABLE_TO.getMatcher().checkForMatchingCharacteristic(TypeUtils.getTypeUtils().doTypeRetrieval().getTypeElement(String.class), Object.class));


                                                    }

                                                }
                                        )
                                        .build()


                        },
                        {
                                "check : mismatching assignable to case",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(
                                                new AbstractUnitTestAnnotationProcessorClass() {
                                                    @Override
                                                    protected void testCase(TypeElement element) {


                                                        MatcherAssert.assertThat("Should return false for mismatching assignable to case : ", !CoreMatchers.IS_ASSIGNABLE_TO.getMatcher().checkForMatchingCharacteristic(TypeUtils.getTypeUtils().doTypeRetrieval().getTypeElement(Object.class), String.class));


                                                    }

                                                }
                                        )
                                        .build()


                        },
                        {
                                "check : null valued element and class",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(
                                                new AbstractUnitTestAnnotationProcessorClass() {
                                                    @Override
                                                    protected void testCase(TypeElement element) {


                                                        MatcherAssert.assertThat("Should return false for null valued element : ", !CoreMatchers.IS_ASSIGNABLE_TO.getMatcher().checkForMatchingCharacteristic(null, String.class));
                                                        MatcherAssert.assertThat("Should return false for null valued assignable to class : ", !CoreMatchers.IS_ASSIGNABLE_TO.getMatcher().checkForMatchingCharacteristic(element, null));
                                                        MatcherAssert.assertThat("Should return false for null valued element and assignable to class : ", !CoreMatchers.IS_ASSIGNABLE_TO.getMatcher().checkForMatchingCharacteristic(null, null));

                                                    }

                                                }
                                        )
                                        .build()


                        },


                }

        );


    }

    @Test
    public void test() {
        super.test();
    }

}

