import java.util.*;
import java.awt.Point;

public class King extends Piece{
 ArrayList<Point> validMoves = new ArrayList<Point>();
  
 public King(int player){
  super(10, player);
 } 

 //This method assumes that the board is read from top left, row by row
 //ending at bottom right.
 @Override
 public List<Point> getValidMoves(Point p, Square[][] squareArray){

   return validMoves;
 }
}