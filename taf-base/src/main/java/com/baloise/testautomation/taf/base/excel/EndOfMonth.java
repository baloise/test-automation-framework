package com.baloise.testautomation.taf.base.excel;

import java.util.Calendar;

import org.apache.poi.ss.formula.OperationEvaluationContext;
import org.apache.poi.ss.formula.eval.ErrorEval;
import org.apache.poi.ss.formula.eval.EvaluationException;
import org.apache.poi.ss.formula.eval.NumberEval;
import org.apache.poi.ss.formula.eval.OperandResolver;
import org.apache.poi.ss.formula.eval.ValueEval;
import org.apache.poi.ss.formula.functions.FreeRefFunction;
import org.apache.poi.ss.usermodel.DateUtil;

public class EndOfMonth implements FreeRefFunction {
  
  public static final FreeRefFunction instance = new EndOfMonth();

  @Override
  public ValueEval evaluate(ValueEval[] args, OperationEvaluationContext context) {
    if (args.length != 2) {
      return ErrorEval.VALUE_INVALID;
    }
    try {
      ValueEval veAusgangsdatum = args[0];
      ValueEval veMonate = args[1];

      int ausgangsdatum = OperandResolver.coerceValueToInt(veAusgangsdatum);
      int monate = OperandResolver.coerceValueToInt(veMonate);

      Calendar cal = DateUtil.getJavaCalendar(ausgangsdatum, false);
      cal.add(Calendar.MONTH, monate);
      cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
      double result = DateUtil.getExcelDate(cal.getTime());

      return new NumberEval(result);
    }
    catch (EvaluationException e) {
      return e.getErrorEval();
    }
  }

}
