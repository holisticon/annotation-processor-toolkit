package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;

import de.holisticon.annotationprocessortoolkit.templating.expressions.Operand;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperandType;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperationType;


/**
 * Dynamic operand queried from models.
 */
public class DynamicOperand extends Operand<Object> {

    public DynamicOperand(OperandType operandType, String expressionString,  OperationType[] unaryOperationsToBeApplied) {
        super(operandType, expressionString, unaryOperationsToBeApplied);

    }

    @Override
    public Class<Object> getOperandsJavaType() {
        return null;
    }

    @Override
    public Object value() {
        return null;
    }

}