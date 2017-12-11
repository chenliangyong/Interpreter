import java.util.*;

public abstract class Interpreter extends TypeChecker
{
	public static void main(String argv[])
	{
		// argv[0]: input file containing function definitions
		// argv[1]: output file displaying lexical/syntax/type error messages
		// argv[2]: single expression to be evaluated
		// argv[3]: lexical/syntax/type error messages for the expression in argv[2]

		// The evaluation result and runtime errors will be displayed on the terminal.

		setIO( argv[0], argv[1] );
		setLex();

		getToken();
		FunDefList funDefList = funDefList(); // build a parse tree of the function definitions	                    
		if ( ! t.isEmpty() )
			displayln(t + " : Syntax Error, unexpected symbol");
		else if ( ! syntaxErrorFound ) // The function definitions have no lexical/syntax errors.
		{
			funDefList.buildTypeMaps(); // build the three type maps
			TypeVal funDefListType = funDefList.typeEval(); // perform type checking
			if ( funDefListType == TypeVal.Correct ) // The function definitions have no type errors.
			{
				closeIO();
				setIO( argv[2], argv[3] );
				getToken();
				Exp exp = exp(); // build a parse tree of the expression in argv[2]
				if ( ! t.isEmpty() )
					displayln(t + " : Syntax Error, unexpected symbol");
				else if ( ! syntaxErrorFound ) // The expression has no lexical/syntax errors.
				{
					//exp.printParseTree("");
					//System.out.println(exp.getClass().getName());
					displayln("Staring EXP Type checking");
					// perform type checking
					HashMap<String,TypeVal> testmap =new HashMap<String,TypeVal>();
					HashMap<Integer, TypeVal> testmap2 = new HashMap<Integer,TypeVal>();
					HashMap<Integer,String> testmap3 = new HashMap<Integer,String>();
					TypeVal expType = exp.typeEval(testmap, testmap2, testmap3);
					expType=TypeVal.Correct;
					if ( expType != TypeVal.Error && expType != null ) // The expression has no type errors.
					{
						displayln("Staring EXP Evaluate expression");
						Val v = exp.Eval(new HashMap<String,Val>());  // evaluate the expression
						
						if ( v != null )  // The expression evaluated properly without runtime errors.
							System.out.println("Result : " + v.toString() );   // print the value on the terminal
					
					}
				}
			}
		}

		closeIO();
	}
}