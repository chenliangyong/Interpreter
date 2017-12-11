import java.util.*;

abstract class Exp
{
	abstract void printParseTree(String indent);
	abstract TypeVal typeEval(HashMap<String,TypeVal> paramMap, HashMap<Integer,TypeVal> paramNumMap, HashMap<Integer,String> ParaNameMap);
	abstract Val Eval(HashMap<String,Val> eValMap);
}