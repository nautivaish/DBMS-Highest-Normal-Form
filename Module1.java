import java.util.*;
import java.io.*; 
import java.lang.*;
import java.awt.*;

class globalVariables
{
	public static Vector dependencies0 = new Vector();
	public static Vector<Vector<Integer>> dependencies1 = new Vector<Vector<Integer>>();
	public static Vector inputDependency0 = new Vector();
	public static Vector<Vector<Integer>> inputDependency1 = new Vector<Vector<Integer>>();
	public static int n,i,fd,x,flag,j,y,length,k, nf=1,maxNF=1,maxNFindex;
	public static int[][] visited = new int[100][100];
	public static char ch;
	public static Vector<String> fdstring = new Vector<String>();
	public static char[] input = new char[10];
	public static String fdep = new String();
	//public static Vector<Vector<Integer>> v = new Vector<Vector<Integer>>();
	//public static Vector<Vector<Integer>> vi = new Vector<Vector<Integer>>();
	public static Vector<Integer> candidateKeys = new Vector();
	//public static Vector vec = new Vector();
	public static int location;
}


class functions
{
public
	static int substring( String s1, String s2)
	{
	    int l1=s1.length();
	    int l2=s2.length(),i,j,flag;
	    for(i=0;i<l2;i++)
	    {
	        flag=0;
	        for(j=0;j<l1;j++)
	        {
	        
	            if(s1.charAt(j)==s2.charAt(i))
	            {
	                flag++;
	                break;
	            }
	            
	        }
	        if(flag==0)
	            return 0;
	    }
	    return 1;

	}

	static void createCandidateKey()
	{
	    int i,j,z,index=0,maxsize=0,sz;
	    int[]  leftOut = new int[globalVariables.n];
	    z=globalVariables.dependencies0.size();
	    for(i=0;i<z;i++)
	    {
	        sz=globalVariables.dependencies1.get(i).size();
	        if(sz>maxsize)
	        {
	            index=i;
	            maxsize=sz;
	            
	        }
	    }
	    //System.out.println("maxsize = " + maxsize + " at index " + index);
	    for(i=0;i< globalVariables.n;i++)
	        leftOut[i]=0;
	    for(i=0;i<maxsize;i++)
	    {
	    	j=(int)((Vector)globalVariables.dependencies1.get(index)).get(i);
	        leftOut[j]++;
	        //System.out.println("included = " + j);
	    }
	    char[] cKey = new char[globalVariables.n];    
	    z=(int)(((String)globalVariables.fdstring.get(index)).length());
	    for(i=0;i<z;i++)
	    {
	    	if(globalVariables.fdstring.get(index).charAt(i)>='A' && globalVariables.fdstring.get(index).charAt(i) <='Z')
	    	{
		    	cKey[i]=globalVariables.fdstring.get(index).charAt(i);
		    	//System.out.println("i = " +i + "z = " + z + " char = " + globalVariables.fdstring.get(index).charAt(i));
	    	}
	    	else break;
	    }
	    for(j=0;j< globalVariables.n;j++)
	    {
	        if(leftOut[j]==0)
	        {
	            cKey[i]=(char) (65+j);
	            i++;
	        }
	    }
	    cKey[i]='\0';
	    String cKeystr = new String(cKey);
	    globalVariables.fdstring.add(cKeystr);
	    z=globalVariables.dependencies0.size();
	    Vector<Integer> temp = new Vector();
	    globalVariables.inputDependency1.add(temp);
	    globalVariables.inputDependency0.add(z);
	    for(i=0;i< globalVariables.n;i++)
	        temp.add(i);
	    globalVariables.dependencies0.add(z);
	    globalVariables.dependencies1.add(temp);
	    globalVariables.candidateKeys.add(z);
	}

	static void decompose2NF()
	{
	    int i,z,j,index,k,flag,length,l;
	    index=globalVariables.candidateKeys.get(0);
	    z=globalVariables.inputDependency0.size();
	    Vector<String> decomposedRelations = new Vector<String>();
	    char[] decompose = new char [20];
	    char[] leftOut = new char [globalVariables.n];
	    String compareWith;
	    flag=0;
	    compareWith=globalVariables.fdstring.get(index);
	    length = 0;
	    while(compareWith.charAt(length)>='A' && compareWith.charAt(length)<='Z')
	    	length++;
	    for(j=0;j<globalVariables.fdstring.size();j++)
	    {
	    	l=0;
	    	while(globalVariables.fdstring.get(j).charAt(l)>='A' && globalVariables.fdstring.get(j).charAt(l)<='Z')
	    		l++;
	        if(l<length && globalVariables.dependencies1.get(j).size()>1)
	        {
	            if( substring(compareWith, globalVariables.fdstring.get(j))==1)
	            {
	                
	                for(k=0;k<((Vector)globalVariables.dependencies1.get(j)).size();k++)
	                {
	                	
	                	int convert = (int)((Vector)globalVariables.dependencies1.get(j)).get(k);
	                	decompose[k]=(char)(convert+65);
	                }
	                decompose[k]='\0';
	                String decomposeStr = new String(decompose);
	                decomposedRelations.add(decomposeStr);
	                
	            }
	        }
	    }
	    decompose=new char[20];
	    //System.out.println("RELATION 0 "+ decomposedRelations);
	    for(i=0;i< globalVariables.n;i++)
	        leftOut[i]=0;
	    z=decomposedRelations.size();
	    for(i=0;i<z;i++)
	    {
	    	l=0;
	    	while(decomposedRelations.get(i).charAt(l)>='A' && decomposedRelations.get(i).length()<= 'Z')
	    		l++;
	        for(j=0;j<l;j++)
	        leftOut[(decomposedRelations.get(i)).charAt(j)-'A']++;
	    }
	    length=0;
	    while((globalVariables.fdstring.get(index)).charAt(length)>='A' && (globalVariables.fdstring.get(index)).charAt(length)<= 'Z')
	        		length++;
	    for(i=0;i<length;i++)
	    {
	        decompose[i]=(globalVariables.fdstring.get(index)).charAt(i);
	        //System.out.println("include = " + decompose[i]+ " " + length);
	        leftOut[decompose[i]-'A']++;
	    }
	    for(j=0;j< globalVariables.n;j++)
	    {
	        if(leftOut[j]==0)
	        {
	            decompose[i]=(char)(j+'A');
	            i++;
	        }
	    }
	    decompose[i]='\0';
	    String decomposeStr = new String(decompose);
	        decomposedRelations.add(decomposeStr);
	    System.out.println("After this decomposition, the relation satisfies 2NF");
	    for(i=0;i<decomposedRelations.size();i++)
	        System.out.println("Relation " + (i+1)+": "+ decomposedRelations.get(i));
	}
	static void decompose3NF(int maxNFindex)
	{
	    int i,z,j,index,k,flag,l;
	    index=maxNFindex;
	    z=globalVariables.inputDependency0.size();
	    Vector<String> decomposedRelations = new Vector<String>();
	    char[] decompose = new char[20];
	    int[] leftOut = new int[globalVariables.n];
	        
	    for(i=0;i< globalVariables.n;i++)
	        leftOut[i]=0;
	    z=globalVariables.inputDependency1.get(index).size();
	    for(i=0;i<z;i++)
	    {
	    	int convert = (int)((Vector)globalVariables.inputDependency1.get(index)).get(i);
	                	decompose[i]=(char)(convert+65);
	        leftOut[convert]++;
	        //System.out.println("i = "+i+" z= "+z+" convert= "+convert);
	    }
	    ///System.out.println("LEFTOUT 3 "+ leftOut[3] + decompose);
	    decompose[i]='\0';
	    String decomposeStr = new String(decompose);
	        decomposedRelations.add(decomposeStr);	        
	    //System.out.println("RELATION 0 "+ decomposedRelations);
	    z=globalVariables.inputDependency1.size();
	    for(i=0;i<z;i++)
	    {
	        flag=0;
	        decompose=new char[20];
	        for(j=0;j<globalVariables.fdstring.get(i).length();j++)
	        {
	        	if((globalVariables.fdstring.get(i)).charAt(j)>='A' && (globalVariables.fdstring.get(i)).charAt(j)<= 'Z')
		        {
		            decompose[j]=(globalVariables.fdstring.get(i)).charAt(j);
		            if(leftOut[decompose[j]-'A']==0)
		            {
		                flag++;
		                break;
		            }
		        }
		        else break;
	        }
	        //System.out.println("leftOut " + leftOut);
	        if(flag==0)
	        {            
	            for(k=0;k<globalVariables.inputDependency1.get(i).size();k++)
	            {
	                if(leftOut[(globalVariables.inputDependency1.get(i)).get(k)]==0)
	                {
	                	int convert = (int)((Vector)globalVariables.inputDependency1.get(i)).get(k);
	                	decompose[j]=(char)(convert+65);

	                    j++;
	                    leftOut[convert]++;
	                    //System.out.println("convert " + convert + "   k " + k + " decompose[j-1] "+ decompose[j-1]);
	                }
	            }
	            decompose[j]='\0';
	            l=0;
	            while(globalVariables.fdstring.get(i).charAt(l)>='A' && globalVariables.fdstring.get(i).charAt(l)<='Z')
	            	l++;
	            if(j>l)
	            {
	                decomposeStr = new String(decompose);
	       			decomposedRelations.add(decomposeStr);
	            }
	        }
	    }
	    //System.out.println("RELATION 0 "+ decomposedRelations);
	    System.out.println("After this decomposition, the relation satisfies 3NF");
	    for(i=0;i<decomposedRelations.size();i++)
	        System.out.println("Relation " + (i+1)+": "+ decomposedRelations.get(i));
	
	}
	static void decomposeBCNF(int maxNFindex)
	{
	    int i,j,z,k;
	    z=globalVariables.inputDependency0.size();
	    Vector<String> decomposedRelations = new Vector<String>();
	    char[] decompose = new char[20];
	    int[] leftOut = new int[globalVariables.n];
	    for(i=0;i< globalVariables.n;i++)
	        leftOut[i]=0;
	    for(i=0;i<z;i++)
	    {
	        if(globalVariables.inputDependency1.get(i).size()>1 && i!=globalVariables.maxNFindex)
	        {
	            for(j=0;j<globalVariables.inputDependency1.get(i).size();j++)
	            {
	            	int convert = (int)((Vector)globalVariables.inputDependency1.get(i)).get(j);
	                decompose[j]=(char)(convert+65);	        
	                if(j>0)
	                    leftOut[(int)(decompose[j]-'A')]++;
	            }
	            decompose[j]='\0';
	            String decomposeStr = new String(decompose);
	       		decomposedRelations.add(decomposeStr);
	            
	        }
	    }
	    i=globalVariables.maxNFindex;
	    k=0;
	    for(j=0;j<globalVariables.inputDependency1.get(i).size();j++)
	    {
	        if(leftOut[globalVariables.inputDependency1.get(i).get(j)]==0)
	        {
	        	int convert = (int)((Vector)globalVariables.inputDependency1.get(i)).get(j);
	            decompose[k]=(char)(convert+65);
	            k++;
	        }	        
	    }
	    decompose[k]='\0';
	    String decomposeStr = new String(decompose);
	       	decomposedRelations.add(decomposeStr);

	    System.out.println("After this decomposition, the relation satisfies BCNF");
	    for(i=0;i<decomposedRelations.size();i++)
	        System.out.println("Relation " + (i+1)+": "+ decomposedRelations.get(i));
	}

	static int checkBCNF(int candidateIndex)
	{
	    int i,z;
	    z=globalVariables.dependencies0.size();
	    for(i=0;i<z;i++)
	    {
	        if(globalVariables.dependencies1.get(i).size()>1 && i!=candidateIndex)
	            return 0;
	    }
	    return 1;

	}

	static int check3NF(int candidateIndex)
	{
	    int z1,z2;
	    z1=globalVariables.inputDependency1.get(candidateIndex).size();
	    z2=globalVariables.dependencies1.get(candidateIndex).size();
	    if(z1==z2)
	        return 1;
	    else return 0;    
	}


	static void check2NF()
	{
	    int i,j,z,flag,length,l;
	    z=globalVariables.candidateKeys.size();
	    String compareWith;
	    //System.out.println("size of candidateKeys " + z ); 
	    for(i=0;i<z;i++)
	    {
	        flag=0;
	        compareWith=globalVariables.fdstring.get(globalVariables.candidateKeys.get(i));
	        j=0;
	        while(compareWith.charAt(j)>='A' && compareWith.charAt(j)<= 'Z')
	        {
	        	j++;
	        }
	        length= j;
	        for(j=0;j<globalVariables.fdstring.size();j++)
	        {
	        	l=0;
	        	while((globalVariables.fdstring.get(j)).charAt(l)>='A' && (globalVariables.fdstring.get(j)).charAt(l)<= 'Z')
	        		l++;
	        	//System.out.println("l = " + l + "length = " + length + " "+ compareWith + " " + globalVariables.fdstring.get(j));
	            if(l<length && globalVariables.dependencies1.get(j).size()>1)
	            {

	                if( substring(compareWith, globalVariables.fdstring.get(j))==1);
	                {
	                    flag++;
	                    break;
	                }
	            }
	        }
	        if (flag==0)
	        {
	            globalVariables.nf=2;
	            //System.out.println("NF at " + i +" " + globalVariables.nf );
	            globalVariables.nf+=check3NF( globalVariables.candidateKeys.get(i));
	            //System.out.println("NF after checking 3NF at " + i +" " + globalVariables.nf );
	            if( globalVariables.nf==3)
	            {
	                globalVariables .nf+=checkBCNF( globalVariables.candidateKeys.get(i));
	            }
	            if( globalVariables .nf>globalVariables.maxNF)
	                globalVariables.maxNFindex= globalVariables.candidateKeys.get(i);
	            globalVariables.maxNF = Math.max( globalVariables.nf,globalVariables.maxNF); 

	        }
	        if(globalVariables.maxNF==4)
	            break;
	    }
	    
	}


	// Visit function
	static void visit(int i, int j, int[][] visited)
	{
	    int z=0,value,index=0,l,present,k;
	    value = (globalVariables.dependencies1.get(i)).get(j);
	    for(k=0;k<globalVariables.fd;k++)
	    {
	        if((int)globalVariables.dependencies0.get(k) == value)
	        {
	            index=k;
	            z=globalVariables.dependencies1.get(k).size();
	            break;
	        }

	    }
	    for(k=0;k<z;k++)
	    {
	        present=0;
	        for(l=0;l<globalVariables.dependencies1.get(i).size();l++)
	        {
	            if(globalVariables.dependencies1.get(i).get(l)==(globalVariables.dependencies1.get(index)).get(k))
	            {
	                present++;
	                break;
	            }
	        }
	        if(present==0)
	        globalVariables.dependencies1.get(i).add((globalVariables.dependencies1.get(index)).get(k));
	    }
	}
}

class HighInputExcetion extends Exception
{
   int maxInputValue = 26, minInputValue = 0;
   HighInputExcetion( int mx, int mn) 
   {
	maxInputValue = mx;
	minInputValue = mn;
   }
   public String toString(){ 
	return ("Input value out of expected bounds. Value expected in range "+ minInputValue+" to "+maxInputValue);
   }
}
class WrongInputFormatException extends Exception
{
	String str= "Input Format Doesnt Match X>Y format.";
	public String toString(){ 
	return (str);
   }
}

public class Module1
{
	public static void main(String[] args) 
	{
		globalVariables global = new globalVariables();
		functions func = new functions();
		System.out.println("Enter the No. of attributes"); 
		Scanner scan = new Scanner(System.in); 
		try
		{
			globalVariables.n=scan.nextInt();  
			if(globalVariables.n>26 || globalVariables.n<1)
				throw new HighInputExcetion(26,1);
		}
		catch(Exception e)
		{
			System.out.println("Exception found... \n Exception type: <"+ e +">\n Exiting Process... Run Again with correct input");
			System.exit(0);
		}

	    System.out.println("Your attributes names are as follows:");
	    globalVariables.input[1]='\0';
	    int flag,k,i,j;
	    char ch;
	    for(i=0;i< globalVariables.n;i++)
	    {
	        ch=(char)(65+i);
	        System.out.print("  "+ch);
	        globalVariables.input[0]=ch;
	        String inputStr = new String(globalVariables.input);
	        globalVariables.fdstring.add(inputStr);
	        //System.out.println(inputStr +" " + globalVariables.fdstring.get(i));
	        //globalVariables.v.add(i);
	        globalVariables.dependencies0.add(i);
	        globalVariables.inputDependency0.add(i);

	        // v.add([i]);
	        // vi.add([i]);
	        globalVariables.dependencies1.add(i,new Vector<Integer>());
	        globalVariables.dependencies1.get(i).add(i);
	        //System.out.println(globalVariables.dependencies1.get(i));
	        globalVariables.inputDependency1.add(i,new Vector<Integer>());
	        globalVariables.inputDependency1.get(i).add(i);
	        //globalVariables.v.remove(0);

	    }
	    
	    System.out.println("\nEnter the no. of functional globalVariables.dependencies");
	    try
		{
			globalVariables.fd=scan.nextInt();  
			if(globalVariables.fd<0)
				throw new HighInputExcetion(1000,0);
		}
		catch(Exception e)
		{
			System.out.println("Exception found... \n Exception type: <"+ e +">\n Exiting Process... Run Again with correct input");
			System.exit(0);
		}
	    
	    //System.out.println("FD " +globalVariables.fd);
	    System.out.println("Now enter the functional globalVariables.dependencies in format X>Y");
	    globalVariables.fdep = scan.nextLine();
	    for(i=0;i<globalVariables.fd;i++)
	    {
	        
	        
	        try
			{
				globalVariables.fdep = scan.nextLine();
				if(globalVariables.fdep.length()<3)
					throw new WrongInputFormatException();

				for(j=0;j<globalVariables.fdep.length();j++)
				{
					if(j!=globalVariables.fdep.length()-2)
					{
						if(globalVariables.fdep.charAt(j)<'A' || globalVariables.fdep.charAt(j)>=(char)(globalVariables.n+65))
							throw new WrongInputFormatException();
					}
					else if(globalVariables.fdep.charAt(j)!='>')
						throw new WrongInputFormatException();
				}

				
			}
			catch(Exception e)
			{
				System.out.println("Exception found... \n Exception type: <"+ e +">\n Exiting Process... Run Again with correct input");
				System.exit(0);
			}
	        globalVariables.length=globalVariables.fdep.length();
	        for(j=0;j<globalVariables.length-2;j++)
	        {
	            globalVariables.input[j]=globalVariables.fdep.charAt(j);
	        }
	        globalVariables.input[j]='\0';
	        //System.out.println("length " + globalVariables.length + " " + globalVariables.fdep);
	        globalVariables.location=(int)(globalVariables.fdep.charAt(globalVariables.length-1)-'A');
	        flag=0;
	        String inputStr = new String(globalVariables.input);
	        
	        for(j=0;j<globalVariables.fdstring.size();j++)
	        {
	        	try{        	
	            
		            if(globalVariables.fdstring.get(j).equals(inputStr))
		            {
		            	//System.out.println(inputStr +" " + globalVariables.fdstring.get(j)+ " " +globalVariables.location);
		            	if(j==globalVariables.location)
		            		throw new WrongInputFormatException();
		                globalVariables.dependencies1.get(j).add(globalVariables.location);
		                globalVariables.inputDependency1.get(j).add(globalVariables.location);
		                //System.out.println(j+" "+ globalVariables.dependencies1.get(j));
		                flag++;
		                break;
		            }
	            }	
		        catch(Exception e)
				{
					System.out.println("Exception found... \n Exception type: <"+ e +">\n Exiting Process... Run Again with correct input");
					System.exit(0);
				}   
	           
	        }
	            
	        if(flag==0)
	        {
	        	//System.out.println("location" + globalVariables.location);
	        	inputStr = new String(globalVariables.input);
	            globalVariables.fdstring.add(inputStr);
	            globalVariables.dependencies0.add(j);
	            globalVariables.dependencies1.add(j,new Vector<Integer>());	            
	            globalVariables.dependencies1.get(j).add(globalVariables.location);
	            globalVariables.inputDependency0.add(j);
	            globalVariables.inputDependency1.add(j,new Vector<Integer>());
	            globalVariables.inputDependency1.get(j).add(globalVariables.location);
	            for(k=0;k<globalVariables.length-2;k++)
	            {
	            	//System.out.println("LENGTH = " + globalVariables.length);
	                globalVariables.dependencies1.get(j).add(globalVariables.fdep.charAt(k)-'A');
	                globalVariables.inputDependency1.get(j).add(globalVariables.fdep.charAt(k)-'A');

	            }           
	        }
	    }
	    
	    globalVariables.fd=globalVariables.dependencies0.size();
	    //System.out.println("FD " +globalVariables.fd);
	    
	    for(i=0;i<globalVariables.fd;i++)
	    {	    	
	        for(j=0;j< globalVariables.n+1;j++)
	            globalVariables.visited[i][j]=0;
	        for(j=0;j<globalVariables.dependencies1.get(i).size();j++)
	        {
	            if(globalVariables.visited[i][(globalVariables.dependencies1.get(i)).get(j)]==0)
	            {
	                globalVariables.visited[i][(globalVariables.dependencies1.get(i)).get(j)]=1;
	                func.visit(i,j,globalVariables.visited);
	            }
	        }
	    }

	    System.out.println("This is the list of attributes which are dependent on the following functions:");
	    for(i=0;i<globalVariables.fd;i++)
	    
	    {
	    	//System.out.println(i+" inputDependency1 "+ globalVariables.inputDependency1.get(i));
	        System.out.print("\n"+ globalVariables.fdstring.get(i) + "-> ");
	        int z=globalVariables.dependencies1.get(i).size();
	        if(z==globalVariables.n)
	            globalVariables.candidateKeys.add(i);
	        
	        for(j=0;j<z;j++)
	        {
	            System.out.print(  globalVariables.fdstring.get((globalVariables.dependencies1.get(i)).get(j)) + " ");
	        }	        
	    }
	    
	    if(globalVariables.candidateKeys.size()==0)
	    {
	    	//System.out.println(" candidate key not in input");
	        func.createCandidateKey();
	    }
	    System.out.println("\nThe Candidate Keys are: ");
	    for(i=0;i<globalVariables.candidateKeys.size();i++)
	    {
	        System.out.println("      "+globalVariables.fdstring.get(globalVariables.candidateKeys.get(i)) );
	    }
	    func.check2NF();
	    System.out.println("The highest normal form of this relation is :"+globalVariables.maxNF+" at index "+globalVariables.maxNFindex);
	    switch(globalVariables.maxNF)
	    {
	        case 1: func.decompose2NF();
	                break;
	        case 2: func.decompose3NF(globalVariables.maxNFindex);
	                break;
	        case 3: func.decomposeBCNF(globalVariables.maxNFindex);
	                break;
	        default:System.out.println("The relation is already in BCNF form."); 
	                break;

	    }
	}    
}
