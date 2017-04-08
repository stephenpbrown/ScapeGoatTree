 * Steve Brown
 * stephen.p.brown@wsu.edu
 * CS 450
 * Programming Project: Scapegoat Tree
 * Due 11/3/16
 * Team for brainstorming ideas: Chris Hight, Tyler Bounds, Steve Brown.

Description:
This program allows the user to be able to read in a text file with different commands on it.
The commands include:
 * BuildTree alpha, key 
	Assume that the input file contains one call to BuildTree on the first line. 
	This call creates a new tree with alpha weight and a first node containing key as a value. 
	All operations for that input file are on this tree. 
 * Insert key
	Given an integer key, create a new node with key value; and insert it into the tree. 
 * Search key
	Find a specified key in the tree if it exists, otherwise give an error message. 
 * Delete key
	Delete the specified key from the tree. 
 * Print
	Prints the tree structure, rotated 90 degrees to the left.
 * Done 
	Exit the program.

This project implements "flatten" by using the Day-Stout-Warren algorithm. We tried to implement
the algorithm in the assignment, but after hours of work, it was to no avail. The Day-Stout-Warren
algorithm also takes care of flattening and rebuilding the tree in place. We talked to Dr. Mocas
about it, and she said that since it implements flatten, that she accepts it for full credit.

After each insert this tree checks if the newly inserted node violates the alpha-weight-balance,
if it does, it goes up the tree, checking each ancestor (parent), to see if it violates
the alpha-weight-balance. If it does, it saves that node and keeps looking. After it
can't look any further, it returns the highest ancestor closest to the root to be
the "scapegoat". It then uses this scapegoat to balance a "subtree", by building a "linked list"
by connecting all the elements of the subtree to a node's right pointers,
and then rebuilds the tree in place using this linked list, to avoid having to use an array and extra space.

The tree prints out like a normal tree, except rotated 90 degrees to the left.

The delete and search functions are normal for a BST. The delete function was built off
of pseudo code on Wikipedia.

Building the project using Netbeans:
The project is in Java, and was made using Eclipse. Following are the instructions for building
the project in Netbeans which is available on the lab computers using Windows:
 * Take the included .zip file ScapeGoatTree and place it somewhere accessible.
 * Unzip the folder into your chosen directory.
 * Open Netbeans, go to file > Eclipse Project...
 * Click on "Import Project ignoring Project Dependencies".
 * Browse "Project to Import", go into the unzipped ScapeGoatTree folder and click on the ScapeGoat folder and press "open".
 * Then Browse for a destination folder to store the imported files (You can choose the same folder if you like).
 * After the project is imported, go into Scapegoat > src > scapeGoat > Main.java.
 * Change the variable "fileName" in the Main.java class to the directory where your text file is.
 * After that, click on the green arrow and "run" the project.
 * The output is then displayed in the console.

Building the project using Eclipse:
In case you want to use Eclipse, and for future reference:

 * Download program, an archive file (.zip)
 * Run Eclipse and create a new Java project named whatever you want.
 * Right-click in the Package Explorer and select Import.
 * Expand General and select Archive File.
 * Select Browse and navigate to where you downloaded the project.
 * Select Finish
 * In the Package Explorer, expand the folder the project imported to.
 * If the files are not under the src folder, select them all, drag and drop them on src.
 * Expand the src folder and the (default package) to reveal the files.
 * Double click Main.java
 * Edit Main.java and change the filename to the input text file path.
 	Example: fileName = "C:/Users/user/Desktop/test.txt"
 * Click the green circle with the white arrow at the top of Eclipse
 * The output is then displayed in the console.


Included files:
Included is a .zip file named ScapeGoatTree, the file contains the following:
 * README.txt - Contains instructions for the program
 * TestList.txt - Contains a test list of instructions I was using to test my program
 * .project - Eclipse project file
 * .classpath - Eclipse class path file
 * src: - Source folder which houses the .java files
  * scapeGoat
   * Main.java - Main function that handles the input and output
   * Node.java - Houses the nodes and their children, parent, and key
   * ScapeGoatTree.java - Main tree class that handles all the inserts, deletes, rebuilding, etc...
 * bin - Contains the java CLASS files
 * .settings - Eclipse settings folder

