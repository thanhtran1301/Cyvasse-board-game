import java.util.*;
import java.awt.Point;
public abstract class Piece{
	//Define names for pieceNo
	public static final int RABBLE = 1;
	public static final int SPEARMAN = 2;
	public static final int CROSSBOWMAN = 3;
	public static final int LIGHTCAVALRY = 4;
	public static final int HEAVYCAVALRY = 5;
	public static final int ELEPHANT = 6;
	public static final int CATAPULT = 7;
	public static final int TREBUCHET = 8;
	public static final int DRAGON = 9;
	public static final int KING = 10;	
	//Define maximum move distances
	public static final int[] maxMoves = {0, 1, 1, 1, 3, 2, 1, 1, 1, -1, 1};
	//Boolean array for if a piece is ranged
	public static final boolean[] isRanged = {false, false, false, true, false, false, false, true, true, false, false};
	//Array of names of pieces
	public static final String[] pieceName = {"", "R", "S", "C", "L", "H", "E", "P", "T", "D", "K"};
	
	
	//Piece number
	private int pieceNo;
	//Owner of piece
	private int owner;
	// this is my modification
	public boolean inwater = false;
	public int loseturn = 0;

	protected Point trebBegin = null;
	protected Point trebEnd = null;
	/**
	** Constructor for Piece class
	** @param n Piece number
	** @param player Player number
	**/
	public void trebuMove(Point beginPoint, Point endPoint){
		trebBegin = beginPoint;
		trebEnd = endPoint;
	}
	public Piece(int n, int player){
		pieceNo = n;
		owner = player;
	}
	/**
	** Getter method for Piece Number
	** @return Piece number
	**/
	public int getPieceNo(){
		return pieceNo;
	}
	/**
	** Getter method for Owner of Piece
	** @return Player number
	**/
	public int getOwner(){
		return owner;
	}
	/**
	** Method to determine if current piece can take given piece
	** @param enemyPieceNo Piece number of Piece being taken
	** @return If given piece can be taken
	**/
	//Please override this method in each class that extends Piece
	public boolean canTake(int enemyPieceNo){			
		return false;
	}
	/**
	** Return the valid moves of current Piece
	** @param p Location of Piece
	** @param squareArray Board state
	** @return List of valid moves
	**/
	//Please override this method in each class that extends Piece
	public List<Point> getValidMoves(Point p, Square[][] squareArray){
		ArrayList<Point> validMoves = new ArrayList<Point>();
		return validMoves;
	}
	/**
	** Method to check if an end point is a valid move position given a start positions
	** @param start Starting square unit is moving from
	** @param end Ending Square unit is trying to move to
	** @return returns If move is valid
	**/
	public boolean ValidBoardSquare(Square start, Square end){
		//Check if square is mountain
		if(end.getModifier() == Square.MOUNTAIN){
			return false;
		}
		
		//Check for piece on square
		if(end.getPiece().getPieceNo() > 0){
			//Check if piece can take it
			if(!start.getPiece().canTake(end.getPiece().getPieceNo())){
				return false;
			}			
		}
		
		return true;
	}
}