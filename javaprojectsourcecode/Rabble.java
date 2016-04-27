import java.util.*;
import java.awt.Point;

public class Rabble extends Piece{
 ArrayList<Point> validMoves = new ArrayList<Point>();
  
 public Rabble(int player){
  super(1, player);
 } 

 //This method assumes that the board is read from top left, row by row
 //ending at bottom right.
 @Override
 public List<Point> getValidMoves(Point p, Square[][] squareArray){

  int xcor = (int)p.getX();
  int ycor = (int)p.getY();
  int i, j;
  for(i = ycor - 1 ; i <= ycor + 1; i++)
  {
   for (j = xcor -1 ; j <= xcor +1 ;j++)
   {
    if( i!= ycor && j!= xcor && (0 <= i && i <= 7) && (0 <= j && j <= 7) )
    {
     if (squareArray[j][i].getModifier() != Square.MOUNTAIN) {
      validMoves.add(new Point(j,i));
     }

    }
   }
  }
    return validMoves;
 }

 public boolean canTake(Piece p){
  if(validMoves.contains(p)) return true;
  else return false;
 }
}