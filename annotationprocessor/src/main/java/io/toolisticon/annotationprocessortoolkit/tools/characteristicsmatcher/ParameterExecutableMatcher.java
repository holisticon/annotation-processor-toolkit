package io.toolisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

import io.toolisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;
import io.toolisticon.annotationprocessortoolkit.tools.ElementUtils;
import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;

/**
 * Matcher to check Parameters of ExecutableElement.
 */
public class ParameterExecutableMatcher extends GenericMatcherWithToolsSupport<Class[]> {

    public ParameterExecutableMatcher(FrameworkToolWrapper tools) {
        super(tools);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkForMatchingCharacteristic(Element element, Class[] toCheckFor) {

        if (element == null || toCheckFor == null) {
            return false;
        }

        // check if element is ExecutableElement
        if (!ElementUtils.CastElement.isExecutableElement(element)) {
            return false;
        }

        // cast to executable element for further checks
        ExecutableElement executableElement = ElementUtils.CastElement.castToExecutableElement(element);

        // check if number of parameters is the same
        if (executableElement.getParameters().size() != toCheckFor.length) {
            return false;
        }


        for (int i = 0; i < executableElement.getParameters().size(); i++) {
            if (!executableElement.getParameters().get(i).asType().equals(
                    TypeUtils.getTypeUtils(getFrameworkTools()).doTypeRetrieval().getTypeMirror(toCheckFor[i]))
                    ) {
                return false;
            }
        }


        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringRepresentationOfPassedCharacteristic(Class[] toGetStringRepresentationFor) {
        return toGetStringRepresentationFor != null ? "" : null;
    }

}