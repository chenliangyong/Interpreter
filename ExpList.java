import java.util.*;

class ExpList
{
	Exp exp;
	ExpList expList; // expList is null at the end of the list.
	
	ExpList(Exp e, ExpList el)
	{
		exp = e;
		expList = el;
	}

	Exp firstExp()
	{
		return exp;
	}
	
	Exp secondExp()
	{
		return expList.firstExp();
	}

	ExpList tailExpList()
	{
		return expList;
	}

	int numArgs()
	{
		if ( expList == null )
			return 1;
		else
			return 1 + expList.numArgs();
	}
		
	void printParseTree(String indent)
	{
		exp.printParseTree(indent);
		if ( expList != null )
			expList.printParseTree(indent);	
	}

	TypeVal typeEvalArith(HashMap<String,TypeVal> paramMap, HashMap<Integer,TypeVal> paramNumMap, HashMap<Integer,String> ParaNameMap)
	{
		TypeVal expType = exp.typeEval(paramMap, paramNumMap, ParaNameMap);

		if ( expList == null )
			return expType;
		else
		{
			TypeVal expListType = expList.typeEvalArith(paramMap, paramNumMap, ParaNameMap);
			if ( expType == TypeVal.Int && expListType == TypeVal.Int )
				return TypeVal.Int;
                	else if ( expType == TypeVal.Float && expListType == TypeVal.Float )
				return TypeVal.Float;
			else
				return TypeVal.Error;
		}
	}

	TypeVal typeEvalBool(HashMap<String,TypeVal> paramMap, HashMap<Integer,TypeVal> paramNumMap,HashMap<Integer,String> ParaNameMap)
	{
		TypeVal expType = exp.typeEval(paramMap, paramNumMap,ParaNameMap);

		if ( expList == null )
			return expType;
		else
		{
			TypeVal expListType = expList.typeEvalBool(paramMap, paramNumMap,ParaNameMap);
			if ( expType == TypeVal.Boolean && expListType == TypeVal.Boolean )
				return TypeVal.Boolean;
			else
				return TypeVal.Error;
		}
	}
	
	TypeVal typeEvalFunCall(HashMap<String,TypeVal> paramMap, HashMap<Integer,TypeVal> paramNumMap,
			       int i, HashMap<Integer,TypeVal> paramNumTypeMap, HashMap<Integer,String> ParaNameMap)
	{
		TypeVal expType = exp.typeEval(paramMap, paramNumMap,ParaNameMap);
		TypeVal paramType = paramNumTypeMap.get(i);

		if ( expList == null )
		{
			if ( expType == paramType )
				return TypeVal.Correct;
			else
			{
				IO.displayln("Type Error: incompatible type for parameter # "+i);
				return TypeVal.Error;
			}
		}
		else
		{
			TypeVal expListType = expList.typeEvalFunCall(paramMap, paramNumMap, i+1, paramNumTypeMap, ParaNameMap);

			if ( expType == paramType && expListType == TypeVal.Correct )
				return TypeVal.Correct;
			else if ( expType != paramType )
			{
				IO.displayln("Type Error: incompatible type for parameter # "+i);
				return TypeVal.Error;
			}
			else
				return TypeVal.Error;
		}
	}

	Val Eval_Eqe(HashMap<String, Val> eValMap) {
		if ( exp == null && expList == null ) {
			System.out.println(" Error: none parameter ");
			return null;
		}
		return new BoolVal(firstExp().Eval(eValMap).floatVal() == secondExp().Eval(eValMap).floatVal());
	}
	Val Eval_GeE(HashMap<String, Val> eValMap) {
		if ( exp == null && expList == null ) {
			System.out.println(" Error: none parameter ");
			return null;
		}
		return new BoolVal(firstExp().Eval(eValMap).floatVal() >= secondExp().Eval(eValMap).floatVal());
	}
	Val Eval_GtE(HashMap<String, Val> eValMap) {
		if ( exp == null && expList == null ) {
			System.out.println(" Error: none parameter ");
			return null;
		}
		return new BoolVal(firstExp().Eval(eValMap).floatVal() > secondExp().Eval(eValMap).floatVal());
	}
	Val Eval_LeE(HashMap<String, Val> eValMap) {
		if ( exp == null && expList == null ) {
			System.out.println(" Error: none parameter ");
			return null;
		}
		return new BoolVal(firstExp().Eval(eValMap).floatVal() <= secondExp().Eval(eValMap).floatVal());
	}
	Val Eval_LtE(HashMap<String, Val> eValMap) {
		if ( exp == null && expList == null ) {
			System.out.println(" Error: none parameter ");
			return null;
		}
		return new BoolVal(firstExp().Eval(eValMap).floatVal() < secondExp().Eval(eValMap).floatVal());
	}
	Val Eval_And(HashMap<String, Val> eValMap) {
		if ( exp == null && expList == null ) {
			System.out.println(" Error: none parameter ");
			return null;
		}
		if (tailExpList() == null) return new BoolVal(firstExp().Eval(eValMap).floatVal() == 1.0f);
		return new BoolVal((firstExp().Eval(eValMap).floatVal() == 1.0f) && (tailExpList().Eval_And(eValMap).floatVal() == 1.0f));
	}
	Val Eval_Or(HashMap<String, Val> eValMap) {
		if ( exp == null && expList == null ) {
			System.out.println(" Error: none parameter ");
			return null;
		}
		if (tailExpList() == null) return new BoolVal(firstExp().Eval(eValMap).floatVal() == 1.0f);
		return new BoolVal((firstExp().Eval(eValMap).floatVal() == 1.0f) || (tailExpList().Eval_And(eValMap).floatVal() == 1.0f));
	}
	Val Eval_Not(HashMap<String, Val> eValMap) {
		if ( exp == null ) {
			System.out.println(" Error: none parameter ");
			return null;
		}
		return new BoolVal(exp.Eval(eValMap).floatVal() == 1.0f);
	}
	
	Val Eval_Add(HashMap<String, Val> eValMap) {
		if ( exp == null && expList == null ) {
			System.out.println(" Error: none parameter ");
			return null;
		}
		if ( expList == null ) return exp.Eval(eValMap);
		//System.out.println("result " + new FloatVal(exp.Eval(eValMap).floatVal() + expList.Eval_Mul(eValMap).floatVal()).floatVal());
		return new FloatVal(exp.Eval(eValMap).floatVal() + expList.Eval_Add(eValMap).floatVal());
	}
	Val Eval_Sub(HashMap<String, Val> eValMap) {
		if ( exp == null && expList == null ) {
			System.out.println(" Error: none parameter ");
			return null;
		}
		if ( expList == null ) return exp.Eval(eValMap);
		return new FloatVal(exp.Eval(eValMap).floatVal() - expList.Eval_Sub(eValMap).floatVal());
	}
	Val Eval_Mul(HashMap<String, Val> eValMap) {
		if ( exp == null && expList == null ) {
			System.out.println(" Error: none parameter ");
			return null;
		}
		if ( expList == null ) return exp.Eval(eValMap);
		//System.out.println("result " + new FloatVal(exp.Eval(eValMap).floatVal() * expList.Eval_Mul(eValMap).floatVal()).floatVal());
		return new FloatVal(exp.Eval(eValMap).floatVal() * expList.Eval_Mul(eValMap).floatVal());
	}
	Val Eval_Div(HashMap<String, Val> eValMap) {
		if ( exp == null && expList == null ) {
			System.out.println(" Error: none parameter ");
			return null;
		}
		if ( expList == null ) return exp.Eval(eValMap);
		if ( expList.Eval_Div(eValMap).isZero()) {
			System.out.println(" Error: divisor is 0.");
			return null;
		}
		return new FloatVal(exp.Eval(eValMap).floatVal() / expList.Eval_Div(eValMap).floatVal());
	}
	
	Val Eval_FunCall(HashMap<String, Val> eValMap, String funName) {
		//output the parameters request for this funCall
		System.out.println("<"+ funName +"> request paramater list: " + TypeChecker.ParaNameMap.get(funName).toString());
		//System.out.println("paramater : " + TypeChecker.ParaNameMap.get(funName).get(1) + " = " + exp.Eval(eValMap).floatVal());
		//setup the parameter and Value pair to eValMap
		
		HashMap<String, Val> backup = new HashMap<>();
		SeteValMap(eValMap, funName, 1 ,backup);
		Val ret =Parser.funMap.get(funName).bodyExp.Eval(eValMap);
		//put old paramater back to eValmap
		for(Map.Entry<String, Val> pair : backup.entrySet()) {
			eValMap.put(pair.getKey(), pair.getValue());
		}
		System.out.println("map: " + eValMap + "\t" + ret.floatVal());
		return ret;
	}
	
	void SeteValMap(HashMap<String, Val> eValMap, String funName, int pos, HashMap<String, Val> backup) {
		String key=TypeChecker.ParaNameMap.get(funName).get(pos);
		
		if (eValMap.containsKey(key)){
			backup.put(key, eValMap.get(key));
		}
		
		eValMap.put(key, exp.Eval(eValMap));
	
		//
		if (expList != null) {
			expList.SeteValMap(eValMap, funName, ++pos, backup);
			//expList.Eval_FunCall(eValMap, funName);
		}
	}
	
	
}