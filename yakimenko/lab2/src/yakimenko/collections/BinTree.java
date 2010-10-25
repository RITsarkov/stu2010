package yakimenko.collections;


public class BinTree extends NodeTree{
	
	public BinTree(int key) {
		super(key);
	}

	public static void main(String[] args) {
		
		int arr[] = {3, 2, 4, 9, 8, 10, 1, 0, -1, 1, 2, 1};
		NodeTree bTree = new NodeTree(6);
		
		for (int i=0; i < arr.length; i++)
			bTree.add (new NodeTree(arr[i]));
		
		bTree = bTree.remove(bTree.getNode(6));
		
		if (bTree.contains(bTree.getNode(3))) 
			System.out.println("YES");
		else System.out.println("NO");
		
		bTree.traverse();
		
		return;
	}
}
