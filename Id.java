import java.util.*;

class Id extends Exp
{
	String id;
	
	Id(String s)
	{
		id = s;
	}
	
	void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " <exp>");
		String indent1 = indent+" ";
		IO.displayln(indent1 + indent1.length() + " " + id);
	}

	TypeVal typeEval(HashMap<String,TypeVal> paramMap, HashMap<Integer,TypeVal> paramNumMap, HashMap<Integer,String> ParaNameMap)
	{
		TypeVal idType = paramMap.get(id);
		if ( idType != null ) {
			//System.out.println("idType: " +id);
			return idType;
		}
		else
		{
			IO.displayln("Error: undefined variable "+id+" found");
			return null;			
		}
	}

	@Override
	Val Eval(HashMap<String, Val> eValMap) {
		if (eValMap.get(id) != null) {
			//System.out.println("id : " +id + " = "+ eValMap.get(id).toString()   );
			return eValMap.get(id).cloneVal();
		} 
		System.out.println("Error : Variable " + id + " miss match." );
		System.out.println("get id.."+TypeChecker.ParaNameMap.get(id));
		
		return null;
	}
}