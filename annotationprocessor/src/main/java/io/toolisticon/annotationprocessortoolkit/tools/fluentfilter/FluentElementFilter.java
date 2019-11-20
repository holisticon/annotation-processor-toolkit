package io.toolisticon.annotationprocessortoolkit.tools.fluentfilter;

import io.toolisticon.annotationprocessortoolkit.tools.command.Command;
import io.toolisticon.annotationprocessortoolkit.tools.command.CommandWithReturnType;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.ExclusiveCriteriaCoreMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.ExclusiveCriteriaElementBasedCoreMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.ImplicitCoreMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.ImplicitElementBasedCoreMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.InclusiveCharacteristicElementBasedCoreMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.InclusiveCriteriaCoreMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.IsCoreMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.IsElementBasedCoreMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.filter.ExclusiveCriteriaElementFilter;
import io.toolisticon.annotationprocessortoolkit.tools.filter.InclusiveCriteriaElementFilter;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.impl.ElementBasedTransitionFilter;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.impl.TransitionFilter;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.CriteriaMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.validator.ExclusiveCriteriaElementValidator;
import io.toolisticon.annotationprocessortoolkit.tools.validator.InclusiveCriteriaElementValidator;

import javax.lang.model.element.Element;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Fluent Element Filter.
 */
@SuppressWarnings("unchecked")
public class FluentElementFilter<ELEMENT extends Element> {


    /**
     * The element list to filter.
     */
    private final List<ELEMENT> elements;


    /**
     * Constructor to be called from inside the static builder function.
     *
     * @param elements the element list to filter
     */
    private FluentElementFilter(List<ELEMENT> elements) {
        this.elements = elements;
    }


    /**
     * Validator step for exclusive criteria validators.
     *
     * @param <FILTER_ELEMENT> the filters element type
     * @param <CRITERIA>       the criteria type
     */
    public class ExclusiveCharacteristicFluentFilter<FILTER_ELEMENT extends Element, CRITERIA> {

        private final boolean inverted;
        private final ExclusiveCriteriaElementFilter<FILTER_ELEMENT, CRITERIA, ExclusiveCriteriaElementValidator<FILTER_ELEMENT, CRITERIA, CriteriaMatcher<FILTER_ELEMENT, CRITERIA>>> filter;

        private ExclusiveCharacteristicFluentFilter(ExclusiveCriteriaElementFilter<FILTER_ELEMENT, CRITERIA, ExclusiveCriteriaElementValidator<FILTER_ELEMENT, CRITERIA, CriteriaMatcher<FILTER_ELEMENT, CRITERIA>>> filter, boolean inverted) {
            this.filter = filter;
            this.inverted = inverted;
        }

        /**
         * Filters by one of the passed criteria.
         * elements won't be discarded if element matches exactly one of the passed criteria.
         *
         * @param criteria the criteria to be used for filtering
         * @return the FluentElementFilter instance
         */
        @SafeVarargs
        public final FluentElementFilter<ELEMENT> filterByOneOf(CRITERIA... criteria) {
            return new FluentElementFilter<>((List<ELEMENT>) filter.filterByOneOf((List<FILTER_ELEMENT>) elements, inverted, criteria));
        }

        /**
         * Filters by none of the passed criteria.
         * elements won't be discarded if element matches none of the passed criteria.
         *
         * @param criteria the criteria to be used for filtering
         * @return the FluentElementFilter instance
         */
        @SafeVarargs
        public final FluentElementFilter<ELEMENT> filterByNoneOf(CRITERIA... criteria) {
            return new FluentElementFilter<>((List<ELEMENT>) filter.filterByNoneOf((List<FILTER_ELEMENT>) elements, inverted, criteria));
        }


    }

    /**
     * Validator step for inclusive characteristics validators.
     *
     * @param <FILTER_ELEMENT>
     * @param <CHARACTERISTIC>
     */
    public class InclusiveCriteriaFluentFilter<FILTER_ELEMENT extends Element, CHARACTERISTIC> {

        private final boolean inverted;
        private final InclusiveCriteriaElementFilter<FILTER_ELEMENT, CHARACTERISTIC, InclusiveCriteriaElementValidator<FILTER_ELEMENT, CHARACTERISTIC, CriteriaMatcher<FILTER_ELEMENT, CHARACTERISTIC>>> filter;

        private InclusiveCriteriaFluentFilter(InclusiveCriteriaElementFilter<FILTER_ELEMENT, CHARACTERISTIC, InclusiveCriteriaElementValidator<FILTER_ELEMENT, CHARACTERISTIC, CriteriaMatcher<FILTER_ELEMENT, CHARACTERISTIC>>> filter, boolean inverted) {
            this.filter = filter;
            this.inverted = inverted;
        }

        /**
         * Filters by one of the passed criteria.
         * elements won't be discarded if element matches exactly one of the passed criteria.
         *
         * @param criteria the criteria to be used for filtering
         * @return the FluentElementFilter instance
         */
        @SafeVarargs
        public final FluentElementFilter<ELEMENT> filterByOneOf(CHARACTERISTIC... criteria) {
            return new FluentElementFilter<>((List<ELEMENT>) filter.filterByOneOf((List<FILTER_ELEMENT>) elements, inverted, criteria));
        }

        /**
         * Filters by none of the passed criteria.
         * elements won't be discarded if element matches none of the passed criteria.
         *
         * @param criteria the criteria to be used for filtering
         * @return the FluentElementFilter instance
         */
        @SafeVarargs
        public final FluentElementFilter<ELEMENT> filterByNoneOf(CHARACTERISTIC... criteria) {
            return new FluentElementFilter<>((List<ELEMENT>) filter.filterByNoneOf((List<FILTER_ELEMENT>) elements, inverted, criteria));
        }

        /**
         * Filters by at least one of the passed criteria.
         * elements won't be discarded if element matches at least one of the passed criteria.
         *
         * @param criteria the criteria to be used for filtering
         * @return the FluentElementFilter instance
         */
        @SafeVarargs
        public final FluentElementFilter<ELEMENT> filterByAtLeastOneOf(CHARACTERISTIC... criteria) {
            return new FluentElementFilter<>((List<ELEMENT>) filter.filterByAtLeastOneOf((List<FILTER_ELEMENT>) elements, inverted, criteria));
        }

        /**
         * Filters by all of the passed criteria.
         * elements won't be discarded if element matches all of the passed criteria.
         *
         * @param criteria the criteria to be used for filtering
         * @return the FluentElementFilter instance
         */
        @SafeVarargs
        public final FluentElementFilter<ELEMENT> filterByAllOf(CHARACTERISTIC... criteria) {
            return new FluentElementFilter<>((List<ELEMENT>) filter.filterByAllOf((List<FILTER_ELEMENT>) elements, inverted, criteria));
        }


    }

    // -----------------------------------------------
    // -- IS FILTERS
    // -----------------------------------------------

    /**
     * Applies is filter.
     * Changes generic type of fluent filter.
     *
     * @param coreMatcher the implicit core matcher to use
     * @return the FluentElementFilter instance
     */
    public <TARGET_ELEMENT extends Element> FluentElementFilter<TARGET_ELEMENT> applyFilter(IsCoreMatcher<ELEMENT, TARGET_ELEMENT> coreMatcher) {

        return new FluentElementFilter<>((List<TARGET_ELEMENT>) coreMatcher.getFilter().filter(elements));

    }

    /**
     * Applies inverted is filter.
     *
     * @param coreMatcher the implicit core matcher to use
     * @return the FluentElementFilter instance
     */
    public <TARGET_ELEMENT extends Element> FluentElementFilter<ELEMENT> applyInvertedFilter(IsCoreMatcher<ELEMENT, TARGET_ELEMENT> coreMatcher) {

        return new FluentElementFilter<>(coreMatcher.getFilter().filter(elements, true));

    }

    /**
     * Applies is element based filter.
     * Changes generic type of fluent filter.
     *
     * @param coreMatcher the implicit core matcher to use
     * @return the FluentElementFilter instance
     */
    public <TARGET_ELEMENT extends Element> FluentElementFilter<TARGET_ELEMENT> applyFilter(IsElementBasedCoreMatcher<TARGET_ELEMENT> coreMatcher) {

        return new FluentElementFilter<>((List<TARGET_ELEMENT>) coreMatcher.getFilter().filter((List<Element>) elements));


    }

    /**
     * Applies inverted implicit element based filter.
     *
     * @param coreMatcher the implicit core matcher to use
     * @return the FluentElementFilter instance
     */
    public <TARGET_ELEMENT extends Element> FluentElementFilter<ELEMENT> applyInvertedFilter(IsElementBasedCoreMatcher<TARGET_ELEMENT> coreMatcher) {

        return new FluentElementFilter<>((List<ELEMENT>) coreMatcher.getFilter().filter((List<Element>) elements, true));

    }

    // -----------------------------------------------
    // -- IMPLICIT FILTERS
    // -----------------------------------------------

    /**
     * Applies implicit filter.
     *
     * @param coreMatcher the implicit core matcher to use
     * @return the FluentElementFilter instance
     */
    public FluentElementFilter<ELEMENT> applyFilter(ImplicitCoreMatcher<ELEMENT> coreMatcher) {

        return new FluentElementFilter<>(coreMatcher.getFilter().filter(elements));

    }

    /**
     * Applies inverted implicit filter.
     *
     * @param coreMatcher the implicit core matcher to use
     * @return the FluentElementFilter instance
     */
    public FluentElementFilter<ELEMENT> applyInvertedFilter(ImplicitCoreMatcher<ELEMENT> coreMatcher) {

        return new FluentElementFilter<>(coreMatcher.getFilter().filter(elements, true));

    }

    /**
     * Applies implicit element based filter.
     *
     * @param coreMatcher the implicit core matcher to use
     * @return the FluentElementFilter instance
     */
    public FluentElementFilter<ELEMENT> applyFilter(ImplicitElementBasedCoreMatcher coreMatcher) {

        return new FluentElementFilter<>((List<ELEMENT>) coreMatcher.getFilter().filter((List<Element>) elements));

    }

    /**
     * Applies inverted implicit element based filter.
     *
     * @param coreMatcher the implicit core matcher to use
     * @return the FluentElementFilter instance
     */
    public FluentElementFilter<ELEMENT> applyInvertedFilter(ImplicitElementBasedCoreMatcher coreMatcher) {

        return new FluentElementFilter<>((List<ELEMENT>) coreMatcher.getFilter().filter((List<Element>) elements, true));

    }

    // -----------------------------------------------
    // -- INCLUSIVE CRITERIA FILTERS
    // -----------------------------------------------

    /**
     * Applies inclusive criteria filter.
     *
     * @param coreMatcher the implicit core matcher to use
     * @return the FluentElementFilter instance
     */
    public <CRITERIA> InclusiveCriteriaFluentFilter<ELEMENT, CRITERIA> applyFilter(InclusiveCriteriaCoreMatcher<ELEMENT, CRITERIA> coreMatcher) {

        return new InclusiveCriteriaFluentFilter<>(coreMatcher.getFilter(), false);

    }

    /**
     * Applies inverted inclusive criteria filter.
     *
     * @param coreMatcher the implicit core matcher to use
     * @return the FluentElementFilter instance
     */
    public <CRITERIA> InclusiveCriteriaFluentFilter<ELEMENT, CRITERIA> applyInvertedFilter(InclusiveCriteriaCoreMatcher<ELEMENT, CRITERIA> coreMatcher) {

        return new InclusiveCriteriaFluentFilter<>(coreMatcher.getFilter(), true);

    }

    /**
     * Applies inclusive criteria element based filter.
     *
     * @param coreMatcher the implicit core matcher to use
     * @return the FluentElementFilter instance
     */
    public <CRITERIA> InclusiveCriteriaFluentFilter<Element, CRITERIA> applyFilter(InclusiveCharacteristicElementBasedCoreMatcher<CRITERIA> coreMatcher) {

        return new InclusiveCriteriaFluentFilter<>(coreMatcher.getFilter(), false);


    }

    /**
     * Applies inverted inclusive element based criteria filter.
     *
     * @param coreMatcher the implicit core matcher to use
     * @return the FluentElementFilter instance
     */
    public <CRITERIA> InclusiveCriteriaFluentFilter<Element, CRITERIA> applyInvertedFilter(InclusiveCharacteristicElementBasedCoreMatcher<CRITERIA> coreMatcher) {

        return new InclusiveCriteriaFluentFilter<>(coreMatcher.getFilter(), true);

    }

    // -----------------------------------------------
    // -- EXPLICIT FILTERS
    // -----------------------------------------------

    /**
     * Applies exclusive criteria filter.
     *
     * @param coreMatcher the implicit core matcher to use
     * @return the FluentElementFilter instance
     */
    public <CHARACTERISTIC> ExclusiveCharacteristicFluentFilter<ELEMENT, CHARACTERISTIC> applyFilter(ExclusiveCriteriaCoreMatcher<ELEMENT, CHARACTERISTIC> coreMatcher) {

        return new ExclusiveCharacteristicFluentFilter<>(coreMatcher.getFilter(), false);

    }

    /**
     * Applies inverted exclusive criteria filter.
     *
     * @param coreMatcher the implicit core matcher to use
     * @return the FluentElementFilter instance
     */
    public <CHARACTERISTIC> ExclusiveCharacteristicFluentFilter<ELEMENT, CHARACTERISTIC> applyInvertedFilter(ExclusiveCriteriaCoreMatcher<ELEMENT, CHARACTERISTIC> coreMatcher) {

        return new ExclusiveCharacteristicFluentFilter<>(coreMatcher.getFilter(), true);

    }

    /**
     * Applies exclusive criteria element based filter.
     *
     * @param coreMatcher the implicit core matcher to use
     * @return the FluentElementFilter instance
     */
    public <CHARACTERISTIC> ExclusiveCharacteristicFluentFilter<Element, CHARACTERISTIC> applyFilter(ExclusiveCriteriaElementBasedCoreMatcher<CHARACTERISTIC> coreMatcher) {

        return new ExclusiveCharacteristicFluentFilter<>(coreMatcher.getFilter(), false);


    }

    /**
     * Applies inverted exclusive element based criteria filter.
     *
     * @param coreMatcher the implicit core matcher to use
     * @return the FluentElementFilter instance
     */
    public <CHARACTERISTIC> ExclusiveCharacteristicFluentFilter<Element, CHARACTERISTIC> applyInvertedFilter(ExclusiveCriteriaElementBasedCoreMatcher<CHARACTERISTIC> coreMatcher) {

        return new ExclusiveCharacteristicFluentFilter<>(coreMatcher.getFilter(), true);

    }

    // -------------------------------------------------------------
    // -------------------------------------------------------------

    /**
     * Applies transition filter.
     *
     * @param transitionFilter the transition filter to use
     * @param <TARGET_ELEMENT>
     * @return the new FluentElementFilter instance based on transition filter result
     */
    public <TARGET_ELEMENT extends Element> FluentElementFilter<TARGET_ELEMENT> applyTransitionFilter(TransitionFilter<ELEMENT, TARGET_ELEMENT> transitionFilter) {

        return new FluentElementFilter<>(transitionFilter.transition(elements));

    }


    /**
     * Applies transition filter.
     *
     * @param transitionFilter the transition filter to use
     * @param <TARGET_ELEMENT>
     * @return the new FluentElementFilter instance based on transition filter result
     */
    public <TARGET_ELEMENT extends Element> FluentElementFilter<TARGET_ELEMENT> applyTransitionFilter(ElementBasedTransitionFilter<TARGET_ELEMENT> transitionFilter) {

        return new FluentElementFilter<>(transitionFilter.transition(elements));

    }

    /**
     * Removes all duplicate values.
     *
     * @return this instance
     */
    public FluentElementFilter<ELEMENT> removeDuplicates() {
        return new FluentElementFilter<>((List<ELEMENT>) TransitionFilters.REMOVE_DUPLICATES_ELEMENTS.transition(elements));
    }

    /**
     * Executes commands.
     *
     * @param command the command to be executed
     */
    public void executeCommand(Command<ELEMENT> command) {

        if (command != null) {

            for (ELEMENT element : elements) {

                command.execute(element);

            }

        }

    }


    /**
     * Executes command with a return value.
     * Returns a list that contains all return values of executed commands.
     *
     * @param command       the command to be executed
     * @param <RETURN_TYPE> the return type
     * @return a list containing all results of executed commands
     */
    public <RETURN_TYPE> List<RETURN_TYPE> executeCommand(CommandWithReturnType<ELEMENT, RETURN_TYPE> command) {
        List<RETURN_TYPE> resultList = new ArrayList<>();

        if (command != null) {

            for (ELEMENT element : elements) {

                resultList.add(command.execute(element));

            }

        }

        return resultList;
    }


    /**
     * Gets the filter result.
     *
     * @return the filtered List
     */
    public List<ELEMENT> getResult() {

        return elements;

    }

    /**
     * Checks if filter result is empty.
     *
     * @return true if filter result is empty, otherwise false
     */
    public boolean isEmpty() {
        return elements == null || elements.isEmpty();
    }

    /**
     * Checks if filter result contains exactly one element.
     *
     * @return true if filter result contains exactly one element, otherwise false
     */
    public boolean hasSingleElement() {
        return elements != null && elements.size() == 1;
    }

    /**
     * Checks if filter result contains more than one element.
     *
     * @return true if filter result contains more than one element, otherwise false
     */
    public boolean hasMultipleElements() {
        return elements != null && elements.size() > 1;
    }

    /**
     * Checks whether the filter result has exactly the passed size.
     *
     * @param size the size to check for
     * @return true if passed size matches the filter results size, otherwise false
     */
    public boolean hasSize(int size) {
        return elements != null && elements.size() == size;
    }


    /**
     * Gets the result size.
     *
     * @return the filtered results size.
     */
    public int getResultSize() {
        return elements == null ? 0 : elements.size();
    }


    /**
     * Factory method to create FluentElementFilter instance.
     *
     * @param elements the element list to filter
     * @param <E>      The element type
     * @return the FluentElementFilter instance
     */
    public static <E extends Element> FluentElementFilter<E> createFluentElementFilter(List<E> elements) {
        return new FluentElementFilter<>(elements);
    }

    /**
     * Factory method to create FluentElementFilter instance.
     *
     * @param elements the elements to filter
     * @param <E>      The element type
     * @return the FluentElementFilter instance
     */
    public static <E extends Element> FluentElementFilter<E> createFluentElementFilter(E... elements) {
        return new FluentElementFilter<>(new ArrayList<E>(elements != null ? Arrays.asList(elements) : Collections.EMPTY_LIST));
    }


}