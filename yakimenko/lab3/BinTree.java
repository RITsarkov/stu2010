package yakimenko.collections;


public class BinTree{
	
	public static void main(String[] args) {
		
		int arr[] = {3, 2, 4, 9, 8, 10, 1, 0, -1, 1, 2, 1};
		NodeTree<Integer> bTree = new NodeTree<Integer>(6);
		
		for (int i=0; i < arr.length; i++)
			bTree.add (arr[i]);
		
		bTree = bTree.remove(6);
		
		if (bTree.contains(15)) 
			System.out.println("YES");
		else System.out.println("NO");
		
		bTree.traverse();
		
		return;
	}
}
