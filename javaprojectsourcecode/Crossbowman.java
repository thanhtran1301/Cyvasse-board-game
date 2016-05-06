import java.util.*;
import java.awt.Point;

public class Crossbowman extends Piece{
 ArrayList<Point> validMoves = new ArrayList<Point>();
 List<Integer> captureList = new ArrayList<Integer>() {{
            add(Piece.RABBLE);
            add(Piece.SPEARMAN);
            add(Piece.CROSSBOWMAN);
            add(Piece.DRAGON);
            add(Piece.KING);
           }};
  
 public Crossbowman(int player){
  super(3, player);
 } 
 
 //This method assumes that the board is read from top left, row by row
 //ending at bottom right.
 @Override
 public List<Point> getValidMoves(Point p, Square[][] squareArray){
  validMoves = new ArrayList<Point>();
  if (inwater && loseturn < 2){
    return validMoves;
  }else{
    inwater = false;
    loseturn = 0;  
    int xcor = (int)p.getX();
    int ycor = (int)p.getY();
    int i, j;
    for(i = ycor - 1 ; i <= ycor + 1; i++)
    {
     for (j = xcor -1 ; j <= xcor +1 ;j++)
     {
      if(( i!= ycor || j!= xcor) && (0 <= i && i <= 7) && (0 <= j && j <= 7) )
      {
        if(squareArray[j][i].getModifier() == Square.MOUNTAIN && squareArray[j][i].getPiece() !=null &&
          squareArray[j][i].getPiece().getPieceNo() == Piece.DRAGON){
          validMoves.add(new Point(j,i));
      }
      else if (squareArray[j][i].getModifier() != Square.MOUNTAIN){
        if(squareArray[j][i].getPiece() == null || captureList.contains(squareArray[j][i].getPiece().getPieceNo())){
          validMoves.add(new Point(j,i));
        }

      }
    }
  }
}
}

return validMoves;
}
public boolean canTake(Piece p){
  return captureList.contains(p.getPieceNo());
}
}