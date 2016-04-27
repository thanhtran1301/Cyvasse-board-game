import java.util.*;
import java.awt.Point;

public class BoardTester{
	public static void main(String[] args){
		Board board = new Board();
		ArrayList<Point> moves = new ArrayList<Point>(board.getValidMoves(new Point(0,0)));
		
		for(int i = 0; i < moves.size(); i++){
			System.out.println(moves.get(i));
		}
	}
}