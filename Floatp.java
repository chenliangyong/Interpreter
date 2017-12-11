import java.util.*;

class Floatp extends Exp
{
	float floatElem;
	
	Floatp(float f)
	{
		floatElem = f;
	}
	
	void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " <exp>");
		String indent1 = indent+" ";
		IO.displayln(indent1 + indent1.length() + " " + floatElem);
	}

	TypeVal typeEval(HashMap<String,TypeVal> paramMap, HashMap<Integer,TypeVal> paramNumMap,HashMap<Integer,String> ParaNameMap)
	{
		return TypeVal.Float;
	}

	@Override
	Val Eval(HashMap<String, Val> eValMap) {
		return new FloatVal(floatElem);
	}
}