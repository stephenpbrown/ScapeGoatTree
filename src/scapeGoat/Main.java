/*
 * Steve Brown
 * CS 450
 * stephen.p.brown@wsu.edu
 * Programming Project: Scapegoat Tree
 * Due 11/3/16
 * 
 * Description:
 * This program allows the user to be able to read in a text file with different commands on it.
 * The commands include:
 * BuildTree alpha, key - Assume that the input file contains one call to BuildTree on the first line. This call creates a new tree with alpha weight and a first node containing key as a value. All operations for that input file are on this tree. 
 * Insert key – given an integer key, create a new node with key value; and insert it into the tree. 
 * Search key – find a specified key in the tree if it exists, otherwise give an error message. 
 * Delete key – delete the specified key from the tree. 
 * Print – prints the tree structure. Your output does not need to be fancy but should be fairly easy for the grader to interrupt as a tree structure. 
 * Done – exit the program.
 * 
 * After each insert this tree checks if the newly inserted node violates the alpha-weight-balance,
 * if it does, it goes up the tree, checking each ancestor (parent), to see if it violates
 * the alpha-weight-balance. If it does, it saves that node and keeps looking. After it
 * can't look any further, it returns the highest ancestor closest to the root to be
 * the "scapegoat". It then uses this scapegoat to balance a "subtree", by building a "linked list"
 * by connecting all the elements of the subtree to a node's right pointers,
 * and then rebuilds the tree in place using this linked list, to avoid having to use an array and extra space.
 * 
 * The tree prints out like a normal tree, except rotated 90 degrees to the left.
 * 
 */

package scapeGoat;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * This main function reads in from a file, and depending on the input, uses a switch statement
 * to execute the commands on the tree.
 */

public class Main {
	
	public static void main(String[] args) 
	{	
		Scanner scanIn = null;
		String str = "";
		String fileName = "D:\\TestList.txt";//"D:\\School*Fall*2016\\CS450*Design*and*Analysis*of*Algorithms\\ScapeGoatTree\\TestList.txt";
		int key = 0;
		double alpha = 0;
		ScapeGoatTree sgt = null;
		
		try {
			scanIn = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(scanIn.hasNextLine())
		{
			str = scanIn.next();
			
			switch(str.toLowerCase())
			{
				case "buildtree":
				{
					alpha = Double.parseDouble(scanIn.next());
					key = Integer.parseInt(scanIn.next());
					sgt = new ScapeGoatTree(alpha,key);

					break;
				}
				case "insert": 
				{
					key = Integer.parseInt(scanIn.next());
					sgt.insert(key);

					break;
				}
				case "delete":
				{
					key = Integer.parseInt(scanIn.next());
					
					System.out.println("Deleting " + key);
					sgt.delete(key);

					break;
				}
				case "search":
				{
					key = Integer.parseInt(scanIn.next());
					boolean result = sgt.search(key);
					
					if(result)
						System.out.println("Found " + key + " in the tree");
					else
						System.out.println("Didn't find " + key + " in the tree");
					
					break;
				}
				case "print":
				{
					System.out.println("---------Printing--------");
//					sgt.inOrder();
					sgt.prettyPrint();
					System.out.println("-------------------------");
					
					break;
				}
				case "done":
				{
					System.out.println("Done: Exiting the program");
					return;
				}
				default :
				{
					System.out.println("Invalid input or command: " + str);
					return;
				}
			}
		}
	}

}
