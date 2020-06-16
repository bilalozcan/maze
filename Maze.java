import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Maze {
	public static int satir = 5;
	public static int sutun = 5;
	private Map<Integer,Cell> map = new HashMap<>();
	class Cell{
		int data;
		Cell parent;
		int leftWall;
		int rightWall;
		int topWall;
		int bottomWall;
		int rank;
		int x;
		int y;
	}
	public void makeSet(int data,int x,int y) {
		Cell node = new Cell();
		node.data = data;
		node.parent = node;
		node.rank = 0;
		node.leftWall = 1;
		node.rightWall = 1;
		node.topWall = 1;
		node.bottomWall = 1;
		node.x = x;
		node.y = y;
		map.put(data,node);
	}
	public int findSet(int data) {
		return findSet(map.get(data)).data;
	}
	private Cell findSet(Cell node) {
		Cell parent = node.parent;
		if(parent== node) {
			return parent;
		}
		node.parent = findSet(node.parent);
		return node.parent;
	}
	public void union(int data1,int data2) {
		Cell node1 = map.get(data1);
		Cell node2 = map.get(data2);
		Cell parent1 = findSet(node1);
		Cell parent2 = findSet(node2);
		
		if(parent1.data == parent2.data) {
			return;
		}
		if (parent1.rank == parent2.rank) {
			parent1.rank = parent1.rank + 1;
			parent2.parent = parent1;
			
		} else if (parent1.rank > parent2.rank) {
			parent2.parent = parent1;
		} else {
			parent1.parent = parent2;
		}
		
		if(node1.x < node2.x) {
			node1.rightWall = 0;
			node2.leftWall = 0;
		} else if(node1.x > node2.x) {
			node2.rightWall = 0;
			node1.leftWall = 0;
		} else if(node1.y > node2.y) {
			node2.bottomWall = 0;
			node1.topWall = 0;
		} else if(node1.y < node2.y) {
			node2.topWall = 0;
			node1.bottomWall = 0;
		}
	}
	public void makeWay(int x,int y,int dizi[][]) {
		Random rand = new Random();
		int yon = rand.nextInt(4);; // 0 ise sol | 1 ise sağ | 2 ise yukari | 3 ise asagi 
		if(x==satir-1&&y==sutun-1)
			return;
		else
		wayControl(x,y, yon,dizi);
	}
	private void wayControl(int x , int y, int yon,int dizi[][]) {
		if(yon == 0) {
			if(x - 1 >= 0 ) {
				union(dizi[y][x], dizi[y][x-1]);
				x=x-1;
				makeWay(x,y,dizi);
			}
			else
				makeWay(x,y,dizi);
		}
		else if(yon == 1) {
			if(x + 1 < satir) {
				union(dizi[y][x], dizi[y][x+1]);
				x=x+1;
				makeWay(x,y,dizi);
			}
			else
				makeWay(x,y,dizi);
		}
		else if(yon == 2) {
			if(y - 1 >= 0 ) {
				union(dizi[y][x], dizi[y-1][x]);
				y=y-1;
				makeWay(x,y,dizi);
			}
			else
				makeWay(x,y,dizi);
		}
		else if(yon == 3 ) {
			if(y +1  < sutun ) {
				union(dizi[y][x], dizi[y+1][x]);
				y=y+1;
				makeWay(x,y,dizi);
			}
			else
				makeWay(x,y,dizi);
		}
		
	}
	public void printMaze(int n, int m) {
		for(int i=0;i<n;i++) {						
			for(int j=0;j<m;j++) {	
				if(map.get(i*n+j).leftWall == 1)
					System.out.print("|");
				else
					System.out.print(" ");
				if(map.get(i*n+j).bottomWall == 1)
					System.out.print("_");
				else
					System.out.print(" ");
				/*if(map.get(i*n+j).topWall == 1)
					System.out.print("_");
				else
					System.out.print(" ");*/
				if(map.get(i*n+j).rightWall == 1) {
					System.out.print("|");
				}
				else
					System.out.print(" ");
				
			}
			System.out.print("\n");
		}
	}
	public static void main(String[] args) {
	 	
		Maze ds = new Maze();
		int[][] dizi = new int[sutun][satir];
		for(int i = 0; i < sutun; i++) {						
			for(int j = 0; j < satir;j++) {	
				dizi[i][j]=i*satir+j;
				ds.makeSet(i*satir+j, j, i);
			}
		}
		ds.makeWay(0, 0, dizi);
		ds.printMaze(satir, sutun);
	}
}
