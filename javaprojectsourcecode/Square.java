import java.awt.Point;
import java.util.*;
public class Square{
	//static variables for modifiers
	public final static int EMPTY = 0;
	public final static int MOUNTAIN = 1;
	public final static int WATER = 2;
	public final static int FOREST = 3;
	public final static int FORTRESS = 4;
	
	//Modifier of current square
	private int squareMod;
	//PIece at current square
	private Piece myPiece;
	//Owner at current Square
	private int owner;
	/**
	** Default Constructor, creates empty square
	**/
	public Square(){
		squareMod = EMPTY;
		owner = 0;
	}
	/**
	** Constructor, with given modifier and player variables, with no piece
	** @param modifier Intended modifier for square
	** @param player Owner of square
	**/
	public Square(int modifier, int player){
		squareMod = modifier;
		owner = player;
		myPiece = null;
	}
	/**
	** Constructor, with given modifier and player variables, with Piece given
	** @param modifier Intended modifier for square
	** @param piece Given piece at a square
	** @param player Owner of square
	**/
	public Square(int modifier, Piece piece, int player){
		squareMod = modifier;
		myPiece = piece;
		owner = player;
	}
	/**
	** Getter method for modifier
	** @return Returns modifier values
	**/
	public int getModifier(){
		return squareMod;
	}
	/**
	** Setter method for modifier
	** @param modifier Intended modifier for current square
	** @param player Owner of the square
	**/
	public void setModifier(int modifier, int player){
		squareMod = modifier;
		owner= player;
	}
	/**
	** Getter method for owner
	** @return Returns owner of current Square
	**/
	public int getOwner(){
		return owner;
	}
	/**
	** Getter method for piece
	** @return Returns piece in current Square
	**/
	public Piece getPiece(){
		return myPiece;
	}
	/**
	** Setter method for modifier
	** @param p Piece to be placed on current Square
	**/
	public void setPiece(Piece p){
		myPiece = p;
	}
	/**
	** Method that places a piece on current Square given Piece number
	** @param pieceNo Piece number being place on current square
	** @param player Owner of piece
	**/
	public void setPiece(int pieceNo, int player){
		//System.out.println("Hello, pieceNO is: " + pieceNo);
		//Calls different constructors depending on the pieceNo
		if(pieceNo == Piece.RABBLE){
			myPiece = new Rabble(player);
		}
		else if (pieceNo == Piece.SPEARMAN){
			myPiece = new Spearman(player);
		}
		else if(pieceNo == Piece.CROSSBOWMAN){
			myPiece = new Crossbowman(player);
		}
		else if(pieceNo == Piece.LIGHTCAVALRY){
			myPiece = new LightCavalry(player);
		}
		else if(pieceNo == Piece.HEAVYCAVALRY){
			myPiece = new HeavyCavalry(player);
		}
		else if(pieceNo == Piece.TREBUCHET){
			myPiece = new Trebuchet(player);
		}
		else if(pieceNo == Piece.CATAPULT){
			myPiece = new Catapult(player);
		}
		else if(pieceNo == Piece.ELEPHANT){
			myPiece = new Elephant(player);
		}
		else if(pieceNo == Piece.DRAGON){
			myPiece = new Dragon(player);
		}
		else if(pieceNo == Piece.KING){
			myPiece = new King(player);
		}
		else if(pieceNo == 0){
			myPiece = null;
		}	
		else{
			System.out.println("Error invalid pieceNo: " + pieceNo);
		}
		
	}
	/**
	** Method that removes piece from current square
	**/
	public void removePiece(){
		myPiece = null;
	}
}