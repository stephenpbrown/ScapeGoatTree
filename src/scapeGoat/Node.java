/*
 * Steve Brown
 * stephen.p.brown@wsu.edu
 * CS 450
 * Programming Project: Scapegoat Tree
 * Due 11/3/16
 */

package scapeGoat;

/*
 * This is the Node class, which houses each node, their children, parent, and key
 */

public class Node 
{
	Node parent, left, right;
	int key;
	
	public Node(int key)
	{
		this.key = key;
		this.parent = null;
		this.left = null;
		this.right = null;
	}
}
