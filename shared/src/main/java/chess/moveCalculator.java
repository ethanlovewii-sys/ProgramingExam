package chess;

import java.util.ArrayList;
import java.util.Collection;

public class moveCalculator {

    public static Collection<ChessMove> calculator(ChessBoard board, ChessPosition position) {
        ChessPiece piece = board.getPiece(position);
        Collection<ChessMove> moves = new ArrayList<>();
        return switch (piece.getPieceType()) {
            case BISHOP -> bishopMoves(board, position, moves);
            case KING -> kingMoves(board, position);
            case KNIGHT -> knightMoves(board, position);
            case PAWN -> pawnMoves(board, position);
            case QUEEN -> queenMoves(board, position);
            case ROOK -> rookMoves(board, position);
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

    private static Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition position) {
        return null;
    }

    private static Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition position) {
        return null;
    }

    private static Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition position) {
        return null;
    }

    private static Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition position) {
        return null;
    }

    private static Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition position) {
        return null;
    }
}
