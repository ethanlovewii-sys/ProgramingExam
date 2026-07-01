package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class moveCalculator {

    public static Collection<ChessMove> calculator(ChessBoard board, ChessPosition position) {
        ChessPiece piece = board.getPiece(position);
        Collection<ChessMove> moves = new ArrayList<>();
        return switch (piece.getPieceType()) {
            case BISHOP -> bishopMoves(board, position, moves);
            case KING -> kingMoves(board, position, moves);
            case KNIGHT -> knightMoves(board, position, moves);
            case PAWN -> pawnMoves(board, position, moves);
            case QUEEN -> queenMoves(board, position, moves);
            case ROOK -> rookMoves(board, position, moves);
        };
    }

    private static Collection<ChessMove> straightLine(ChessPosition position, ChessBoard board, int[] direction) {
        Collection<ChessMove> straightMoves = new ArrayList<>();
        ChessPosition newPosition = new ChessPosition(position.getRow() + direction[0], position.getColumn() + direction[1]);
        while (isValidPosition(newPosition, board, position)){
            straightMoves.add(new ChessMove(position, newPosition, null));
            if (board.getPiece(newPosition) != null) break;
            newPosition = new ChessPosition(newPosition.getRow() + direction[0], newPosition.getColumn() + direction[1]);
        }
        return straightMoves;
    }

    private static boolean isValidPosition(ChessPosition newPosition, ChessBoard board, ChessPosition originalPosition) {
        int row = newPosition.getRow();
        int col = newPosition.getColumn();
        if (row < 1 || row > 8 || col < 1 || col > 8){
            return false;
        }
        if (board.getPiece(newPosition) != null){
            ChessGame.TeamColor moversColor = board.getPiece(originalPosition).getTeamColor();
            ChessGame.TeamColor otherColor = board.getPiece(newPosition).getTeamColor();
            return moversColor != otherColor;
        }
        return true;
    }

    private static Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition position, Collection<ChessMove> moves) {
        int[][] directions = new int[][]{{1, 1}, {-1, 1}, {-1, -1}, {1, -1}};
        for (int[] direction : directions){
            moves.addAll(straightLine(position, board, direction));
        }
        return moves;
    }

    private static Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition position, Collection<ChessMove> moves) {
        int[][] possibleMoves = new int[][]{{1, 1}, {-1, 1}, {-1, -1}, {1, -1}, {1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        for (int[] move : possibleMoves){
            ChessPosition newPosition = new ChessPosition(position.getRow() + move[0], position.getColumn() + move[1]);
            if (isValidPosition(newPosition, board, position)){
                moves.add(new ChessMove(position, newPosition, null));
            }
        }
        return moves;
    }

    private static Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition position, Collection<ChessMove> moves) {
        int[][] possibleMoves = new int[][]{{2, 1}, {2, -1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {1, -2}, {-1, -2}};
        for (int[] move : possibleMoves){
            ChessPosition newPosition = new ChessPosition(position.getRow() + move[0], position.getColumn() + move[1]);
            if (isValidPosition(newPosition, board, position)){
                moves.add(new ChessMove(position, newPosition, null));
            }
        }
        return moves;
    }

    private static Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition position, Collection<ChessMove> moves) {
        ChessGame.TeamColor moversColor = board.getPiece(position).getTeamColor();
        ChessPosition frontSpace;
        ChessPosition doubleSpace;
        int row = position.getRow();
        int col = position.getColumn();

        if (moversColor == ChessGame.TeamColor.WHITE){
            frontSpace = new ChessPosition(row + 1, col);
            if (board.getPiece(frontSpace) == null){
                moves.add(new ChessMove(position, frontSpace, null));
                doubleSpace = new ChessPosition(row + 2, col);
                if (row == 2 && board.getPiece(doubleSpace) == null){
                    moves.add(new ChessMove(position, doubleSpace, null));
                }
            }
        }

        else{
            frontSpace = new ChessPosition(row - 1, col);
            if (board.getPiece(frontSpace) == null){
                moves.add(new ChessMove(position, frontSpace, null));
                doubleSpace = new ChessPosition(row - 2, col);
                if (row == 7 && board.getPiece(doubleSpace) == null){
                    moves.add(new ChessMove(position, doubleSpace, null));
                }
            }
        }

        if (col > 1){
            ChessPosition leftSpace = new ChessPosition(frontSpace.getRow(), frontSpace.getColumn() - 1);
            if (board.getPiece(leftSpace) != null){
                if (isValidPosition(leftSpace, board, position)){
                    moves.add(new ChessMove(position, leftSpace, null));
                }
            }
        }

        if (col < 8){
            ChessPosition rightSpace = new ChessPosition(frontSpace.getRow(), frontSpace.getColumn() + 1);
            if (board.getPiece(rightSpace) != null){
                if (isValidPosition(rightSpace, board, position)){
                    moves.add(new ChessMove(position, rightSpace, null));
                }
            }
        }

        if (frontSpace.getRow() == 8 || frontSpace.getRow() == 1){
            Collection<ChessMove> promotionMoves = new ArrayList<>();
            for (ChessMove move : moves){
                ChessPosition startPosition = move.getStartPosition();
                ChessPosition endPosition = move.getEndPosition();
                promotionMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));
                promotionMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
                promotionMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
                promotionMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
            }
            return promotionMoves;
        }

        return moves;
    }

    private static Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition position, Collection<ChessMove> moves) {
        int[][] directions = new int[][]{{1, 1}, {-1, 1}, {-1, -1}, {1, -1}, {1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        for (int[] direction : directions){
            moves.addAll(straightLine(position, board, direction));
        }
        return moves;
    }

    private static Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition position, Collection<ChessMove> moves) {
        int[][] directions = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        for (int[] direction : directions){
            moves.addAll(straightLine(position, board, direction));
        }
        return moves;
    }
}
