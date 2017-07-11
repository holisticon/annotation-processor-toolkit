package de.holisticon.annotationprocessortoolkit.testhelper;

import de.holisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import de.holisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import de.holisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.List;


@RunWith(Parameterized.class)
public class AbstractAnnotationProcessorUnitTestTest extends AbstractAnnotationProcessorUnitTest {

    private final Class<Throwable> throwable;

    public AbstractAnnotationProcessorUnitTestTest(
            String description,
            AnnotationProcessorUnitTestConfiguration configuration,
            Class<Throwable> throwable) {

        super(configuration);
        this.throwable = throwable;

    }


    @Parameterized.Parameters(name = "{index}: \"{0}\"")
    public static List<Object[]> data() {

        return Arrays.asList(new Object[][]{
                {
                        "Test compilation that succeeds",
                        AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                .setProcessor(
                                        new AbstractUnitTestAnnotationProcessorClass() {
                                            @Override
                                            protected void testCase(TypeElement element) {

                                            }
                                        }
                                )
                                .compilationShouldSucceed()
                                .build(),
                        null
                },
                {
                        "Test compilation that succeeds with warning",
                        AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                .setProcessor(
                                        new AbstractUnitTestAnnotationProcessorClass() {
                                            @Override
                                            protected void testCase(TypeElement element) {
                                                getMessager().warning(element, "MURKS");
                                            }
                                        }
                                )
                                .compilationShouldSucceed()
                                .build()
                        ,
                        null
                },
                {
                        "Test compilation that succeeds with warning - check warning message",
                        AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                .setProcessor(
                                        new AbstractUnitTestAnnotationProcessorClass() {
                                            @Override
                                            protected void testCase(TypeElement element) {
                                                getMessager().warning(element, "MURKS");
                                            }
                                        }
                                )
                                .compilationShouldSucceed()
                                .addMessageValidator()
                                .setWarningChecks("MURKS")
                                .finishMessageValidator()
                                .build()
                        ,
                        null
                },
                {
                        "Test compilation that throws error",
                        AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                .setProcessor(
                                        new AbstractUnitTestAnnotationProcessorClass() {
                                            @Override
                                            protected void testCase(TypeElement element) {
                                                getMessager().error(element, "MURKS");
                                            }
                                        }
                                )
                                .compilationShouldFail()
                                .build()
                        ,
                        null
                },
                {
                        "Test compilation that throws error - check error message",
                        AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                .setProcessor(
                                        new AbstractUnitTestAnnotationProcessorClass() {
                                            @Override
                                            protected void testCase(TypeElement element) {
                                                getMessager().error(element, "MURKS");
                                            }
                                        }
                                )
                                .compilationShouldFail()
                                .addMessageValidator()
                                .setErrorChecks("MURKS")
                                .finishMessageValidator()
                                .build()
                        ,
                        null
                },
                {
                        "Test invalid test configuration: compilationShouldSucceed and error messages in MessageValidator",
                        AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                .setProcessor(
                                        new AbstractUnitTestAnnotationProcessorClass() {
                                            @Override
                                            protected void testCase(TypeElement element) {
                                                getMessager().error(element, "MURKS");
                                            }
                                        }
                                )
                                .compilationShouldSucceed()
                                .addMessageValidator()
                                .setErrorChecks("MURKS")
                                .finishMessageValidator()
                                .build()
                        ,
                        InvalidTestConfigurationException.class
                },


        });

    }


    @Test
    public void test() {

        boolean exceptionThrown = false;

        try {
            super.test();
        } catch (Throwable e) {

            if (throwable != null) {

                MatcherAssert.assertThat(throwable, Matchers.equalTo((Class) e.getClass()));

                exceptionThrown = true;

            } else {
                throw new RuntimeException("Unexpected exception", e);
            }

        }

        if (throwable != null) {
            MatcherAssert.assertThat("Expected exception " + throwable.getSimpleName() + " , but it was not thrown", exceptionThrown);
        }
    }

}
