import java.util.*;

class EqE extends FunExp
{
	ExpList expList;
	
	EqE(ExpList e)
	{
		expList = e;
	}
	
	void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " <exp>");
		
		String indent1 = indent+" ";
		String indent2 = indent1+" ";
		
		IO.displayln(indent1 + indent1.length() + " <fun exp>");
		IO.displayln(indent2 + indent2.length() + " =");
		expList.printParseTree(indent2);	
	}

	TypeVal typeEval(HashMap<String,TypeVal> paramMap, HashMap<Integer,TypeVal> paramNumMap,HashMap<Integer,String> ParaNameMap)
	{
		if ( expList == null || expList.numArgs() != 2 )
		{
			IO.displayln("Error: = operator requires exactly two arguments");
			return null;
		}
		TypeVal exp1Type = expList.firstExp().typeEval(paramMap, paramNumMap, ParaNameMap);
		TypeVal exp2Type = expList.secondExp().typeEval(paramMap, paramNumMap, ParaNameMap);

		if ( (exp1Type == TypeVal.Int || exp1Type == TypeVal.Float) && (exp2Type == TypeVal.Int || exp2Type == TypeVal.Float) )
			return TypeVal.Boolean;
		else if ( exp1Type == TypeVal.Boolean && exp2Type == TypeVal.Boolean )
			return TypeVal.Boolean;
		else
		{
			IO.displayln("Type Error: one or both arguments of = have incompatible types");
			return TypeVal.Error;
		}
	}

	@Override
	Val Eval(HashMap<String, Val> eValMap) {
		if (expList!=null) {
			return expList.Eval_Eqe(eValMap);			
		}
		return null;
	}
}