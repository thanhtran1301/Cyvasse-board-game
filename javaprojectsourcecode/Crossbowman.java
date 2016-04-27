import java.util.*;
import java.awt.Point;

public class Crossbowman extends Piece{
 ArrayList<Point> validMoves = new ArrayList<Point>();
  
 public Crossbowman(int player){
  super(3, player);
 } 
 
 //This method assumes that the board is read from top left, row by row
 //ending at bottom right.
 @Override
 public List<Point> getValidMoves(Point p, Square[][] squareArray){
  
  return validMoves;
 }
}