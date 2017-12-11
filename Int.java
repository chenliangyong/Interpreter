import java.util.*;

class Int extends Exp
{
	int intElem;
	
	Int(int i)
	{
		intElem = i;
	}
	
	void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " <exp>");
		String indent1 = indent+" ";
		IO.displayln(indent1 + indent1.length() + " " + intElem);
	}

	TypeVal typeEval(HashMap<String,TypeVal> paramMap, HashMap<Integer,TypeVal> paramNumMap, HashMap<Integer,String> ParaNameMap)
	{
		return TypeVal.Int;
	}

	@Override
	Val Eval(HashMap<String, Val> eValMap) {		
		return new IntVal(intElem);
	}	
}