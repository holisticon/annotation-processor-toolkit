package io.toolisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

import javax.lang.model.element.Element;
import java.util.regex.Pattern;

/**
 * Class for checking for find matching elements by name and regular expressions.
 */
public class RegexNameMatcher implements GenericMatcher<String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkForMatchingCharacteristic(Element element, String toCheckFor) {
        if (element != null && toCheckFor != null) {

            Pattern pattern = Pattern.compile(toCheckFor);
            return pattern.matcher(element.getSimpleName().toString()).matches();

        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringRepresentationOfPassedCharacteristic(String toGetStringRepresentationFor) {
        return toGetStringRepresentationFor != null ? toGetStringRepresentationFor : null;
    }

}