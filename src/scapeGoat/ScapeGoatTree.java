/*
 * Steve Brown
 * stephen.p.brown@wsu.edu
 * CS 450
 * Programming Project: Scapegoat Tree
 * Due 11/3/16
 */

package scapeGoat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScapeGoatTree 
{
	private Node root;
	private Node sentinel;
	private Node scapeGoat;
	private int maxNodeCount;
	private int nodeCount;
	private double alpha;
	
	// Set initial alpha value and root value
	public ScapeGoatTree(double alpha, int rootVal)
	{
		insert(rootVal);
		this.sentinel = this.root;
		this.alpha = alpha; 
	}
	
	// Public function to insert a value into the tree
	public void insert(int key)
	{
		root = this.insert(this.root, key);
		nodeCount++;
		
		if(nodeCount > maxNodeCount)
			maxNodeCount = nodeCount;
		
//		System.out.println("Inserting " + key);
//		System.out.println("isDeep = " + isDeep(search(root,key)));
		
		Node checkDepth = search(root, key);
		
		if(isDeep(checkDepth))
		{
//			System.out.println("Checking for scapegoat. key = " + checkDepth.key);
			if((scapeGoat = findScapeGoat(checkDepth)) == null)
			{
//				System.out.println("The tree is alpha-weight-balanced");
			}
			else
			{
//				System.out.println("The tree is NOT alpha-weight-balanced: key " + scapeGoat.key + " size of " + size(scapeGoat));
				rebuildTree(scapeGoat);
			}
		}
			
	}
	
	// Private function to insert a value into the tree
	private Node insert(Node root, int key)
	{
		if(root == null)
			return new Node(key);
		else if(key < root.key)
		{
			root.left = insert(root.left, key);
			root.left.parent = root;
		}
		else if(key > root.key)
		{
			root.right = insert(root.right, key);
			root.right.parent = root;
		}
		return root;
	}
	
	// Function for doing a left rotate
	private Node leftRotate(Node root)
	{
		if(root.right != null)
		{
			Node rightChild = root.right;
			root.right = rightChild.right;
			rightChild.right = rightChild.left;
			rightChild.left = root.left;
			root.left = rightChild;
			
			int temp = root.key;
			root.key = rightChild.key;
			rightChild.key = temp;
		}
		return root;
	}
	
	// Function for doing a right rotate
	private Node rightRotate(Node root)
	{
		if(root.left != null)
		{
			Node leftChild = root.left;
			root.left = leftChild.left;
			leftChild.left = leftChild.right;
			leftChild.right = root.right;
			root.right = leftChild;
			
			int temp = root.key;
			root.key = leftChild.key;
			leftChild.key = temp;
		}
		return root;
	}
	
	/*
	 * The functions used to flatten the list and rebuild the tree in place are
	 * adapted from the Day-Stout-Warren (DSW) algorithm.
	 */
	
	// Function to create a "linked list" by connecting values with right pointers
	private Node flatten(Node node)
	{
		while(node.left != null)
		{
			node = rightRotate(node);
		}
		if (node.right != null)
		{
			node.right = flatten(node.right);
		}
		return node;
	}
	
	// Function to flatten and call build tree
	private void rebuildTree(Node scapeGoat)
	{
		Node flattenedList = flatten(scapeGoat);		
		buildTree(flattenedList, size(scapeGoat));
		
		resetParents(root);
	}
	
	// Function to build a tree based off the flattened "linked list"
	private Node buildTree(Node root, int n)
	{
		int times = (int) ((int)Math.log(n)/Math.log(2));
		Node newRoot = root;
		for(int i = 0; i < times; i++)
		{
			newRoot = leftRotate(newRoot);
			root = newRoot.right;
			for(int j = 0; j < n/2 - 1; j++)
			{
				root = leftRotate(root);
				root = root.right;
			}
			n >>= 1;
		}
		return newRoot;
	}
	
	// Finds the highest (closest to the root) scapegoat ancestor that violates the alpha height rule
	private Node findScapeGoat(Node newNode)
	{
		if(newNode.parent == null) // Root node
			return null;
		
		Node scapeGoat = null;
		Node p = newNode.parent;
		
		while(p != null)
		{
//			System.out.println(p.key + ", p = " + alpha*size(p) + ", l = " + size(p.left) + ", r = " + size(p.right));
			if(size(p.left) <= alpha*size(p)
				&& size(p.right) <= alpha*size(p))
			{
				scapeGoat = p;
			}
			p = p.parent;
		}
	
		return scapeGoat;
	}
	
	// Public function to search for a particular key in the tree
	public boolean search(int key)
	{
		if(this.search(this.root, key) != null)
			return true;
		else
			return false;
	}
	
	// Private function to search for a particular key in the tree
	private Node search(Node root, int key)
	{
		Node result = null;
		if(root == null)
			return null;
		
		if(root.key == key)
			return root;
		
		if(root.left != null)
			result = search(root.left, key);
		
		if(result == null)
			result = search(root.right, key);
		
		return result;
	}
	
	// Public function to delete node in the tree
	public void delete(int key)
	{
		if(search(this.root, key) != null)
		{
			if(root.left == null && root.right == null) // Delete the root
				root = null;
			else
			{
				this.delete(this.root, key);
				nodeCount--;
				
				// if nodeCount <= alpha*maxNodeCount
				// Rebuild the tree and set maxNodeCount = nodeCount;
				if(nodeCount <= alpha*maxNodeCount)
				{
					rebuildTree(root);
				}
				resetParents(this.root);
			}
		}
	}
	
	// Private function to delete node in the tree
	private void delete(Node root, int key)
	{
		if(key < root.key)
			delete(root.left, key);
		else if(key > root.key)
			delete(root.right, key);
		else
		{
			if(root.left != null && root.right != null) // Both children
			{
				Node successor = findMin(root.right);
				root.key = successor.key;
				delete(successor, successor.key);
			}
			else if(root.left != null) // Only has left child
			{
				replaceNodeInParent(root, root.left);
			}
			else if(root.right != null) // Only has right child
			{
				replaceNodeInParent(root, root.right); 
			}
			else // Node has no children
			{
				replaceNodeInParent(root, null);
			}
		}
	}
	
	// Replaces the parent node
	private void replaceNodeInParent(Node root, Node newValue)
	{
		if(root.parent != null)
		{
			if(root == root.parent.left)
				root.parent.left = newValue;
			else
				root.parent.right = newValue;
		}
		if(newValue != null)
			newValue.parent = root.parent;
	}
	
	// Function to find the minimum key in the tree
	private Node findMin(Node root)
	{
		Node minNode = root;
		while(minNode.left != null)
			minNode = minNode.left;
		return minNode;
	}
	
	// Function to recursively loop through tree and set the correct parent nodes
	private void resetParents(Node root)
	{
		if(root != null)
		{
			resetParents(root.left);
			if(root.left != null)
				root.left.parent = root;
			if(root.right != null)
				root.right.parent = root;
			resetParents(root.right);
		}
	}
	
	// Print the tree in order
	public void inOrder()
	{
		this.inOrder(this.root);
	}
	
	private void inOrder(Node root)
	{
		if(root != null)
		{
			inOrder(root.left);
			if(root.parent == null)
				System.out.println(root.key + ", parent = " + root.parent);
			else
				System.out.println(root.key + ", parent = " + root.parent.key);
			inOrder(root.right);
		}
	}
	
	// Pretty print the tree, rotated left on its side
	public void prettyPrint()
	{
		this.prettyPrint(this.root, 0);
	}
	
	private void prettyPrint(Node root, int level)
	{
		if(root != null)
		{
			prettyPrint(root.right, level+1);
			for(int i = 0; i < level*3; i++)
				System.out.print(" ");
			System.out.println(root.key);
			prettyPrint(root.left, level+1);
		}
	}
	
	// Setter for the alpha
	public void setAlpha(double alpha)
	{
		this.alpha = alpha;
	}
	
	// Getting for the alpha
	public double getAlpha()
	{
		return this.alpha;
	}
	
	// Function to find the number of nodes in the tree
	private int size(Node root)
	{
		if(root == null)
			return 0;
		else
			return size(root.left) + size(root.right) + 1;
	}
	
	// Function to find the height of the tree
	public int height()
	{
		return height(this.root);
	}
	
	private int height(Node root)
	{
		if(root == null)
			return -1;
		
		int leftHeight = height(root.left);
		int rightHeight = height(root.right);
		
		if(leftHeight > rightHeight)
			return leftHeight + 1;
		else
			return rightHeight + 1;
	}
	
	// Check if node is "too deep"
	private boolean isDeep(Node node)
	{
		int depth = 0;
		Node p = node.parent;
		
		if(node.parent == null)
			depth = 0;
		else
		{
			while(p != null)
			{
				depth++;
				p = p.parent;
			}
		}
		
		if(depth > alphaHeight())
			return true;
		else 
			return false;
	}
	
	// Returns the "alpha height" of the tree
	private int alphaHeight()
	{
		// Using identity logb(n) = log(n) / log(b)
		double b = 1/alpha;
		int n = size(root);
		double log = Math.log(n) / Math.log(b);
				
		return (int)(Math.floor(log));
	}
	
}
