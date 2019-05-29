import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static javafx.scene.shape.StrokeType.INSIDE;

public class TicTacToeApp extends Application {

    private boolean playable = true;
    private boolean turnX = true;
    private Tile[][] board = new Tile[4][4];
    private List<Combo> combos = new ArrayList<>();

    private Pane root = new Pane();

    private Parent createContent() {
        root.setPrefSize(400, 400);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Tile tile = new Tile();
                tile.setTranslateX(j * 100);
                tile.setTranslateY(i * 100);

                root.getChildren().add(tile);

                board[j][i] = tile;
            }
        }

        // horizontal
        for (int y = 0; y < 4; y++) {
            combos.add(new Combo(board[0][y], board[1][y], board[2][y]));
            combos.add(new Combo(board[1][y], board[2][y], board[3][y]));
        }

        // vertical
        for (int x = 0; x < 4; x++) {
            combos.add(new Combo(board[x][0], board[x][1], board[x][2]));
            combos.add(new Combo(board[x][1], board[x][2], board[x][3]));
        }

        // diagonals
        combos.add(new Combo(board[0][0], board[1][1], board[2][2]));
        combos.add(new Combo(board[2][0], board[1][1], board[0][2]));
        combos.add(new Combo(board[1][0], board[2][1], board[3][2]));
        combos.add(new Combo(board[3][0], board[2][1], board[1][2]));
        combos.add(new Combo(board[0][1], board[1][2], board[2][3]));
        combos.add(new Combo(board[2][1], board[1][2], board[0][3]));
        combos.add(new Combo(board[1][1], board[2][2], board[3][3]));
        combos.add(new Combo(board[3][1], board[2][2], board[1][3]));

        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    private void checkState() {
        for (Combo combo : combos) {
            if (combo.isComplete()) {
                playable = false;
                playWinAnimation(combo);
                break;
            }
        }
    }

    private void playWinAnimation(Combo combo) {
        Line line = new Line();
        line.setStartX(combo.tiles[0].getCenterX());
        line.setStartY(combo.tiles[0].getCenterY());
        line.setEndX(combo.tiles[2].getCenterX());
        line.setEndY(combo.tiles[2].getCenterY());
        line.setStrokeWidth(7);
        line.setStroke(Color.GREY);

        root.getChildren().add(line);
    }

    private class Combo {
        private Tile[] tiles;
        public Combo(Tile... tiles) {
            this.tiles = tiles;
        }

        public boolean isComplete() {
            if (tiles[0].getValue().isEmpty())
                return false;

            return tiles[0].getValue().equals(tiles[1].getValue())
                    && tiles[0].getValue().equals(tiles[2].getValue());
        }
    }

    private class Tile extends StackPane {
        private Text text = new Text();

        public Tile() {
            Rectangle border = new Rectangle(100, 100);
            border.setFill(Color.DODGERBLUE);
            border.setStroke(Color.BLACK);
            border.setStrokeType(INSIDE);

            text.setFont(Font.font(72));

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);

            setOnMouseClicked(event -> {
                if (!playable)
                    return;

                    if (turnX){
                        if(!text.getText().isEmpty())
                            return;

                        drawX();
                        turnX = false;
                    }
                    else
                    {
                        if(!text.getText().isEmpty())
                            return;

                        drawO();
                        turnX = true;
                    }
                checkState();
            });
        }

        public double getCenterX() {
            return getTranslateX() + 50;
        }

        public double getCenterY() {
            return getTranslateY() + 50;
        }

        public String getValue() {
            return text.getText();
        }

        private void drawX() {
            text.setText("X");
        }

        private void drawO() {
            text.setText("O");
        }
    }

//    public int minmax(String gracz)
//    {
//        int m, mmx;
//
//        if(wygrana(t,gracz)) return (gracz == 'X') ? 1 : -1;
//
//        if(remis(t,true)) return 0;
//
//        gracz = (gracz == 'X') ? 'O' : 'X';
//
//        mmx = (gracz == 'O') ? 10 : -10;
//
//        for(int i = 1; i <= 9; i++)
//            if(t[i] == ' ')
//            {
//                t[i] = gracz;
//                m = minimax(t,gracz);
//                t[i] = ' ';
//                if(((gracz == 'O') && (m < mmx)) || ((gracz == 'X') && (m > mmx))) mmx = m;
//            }
//        return mmx;
//
//        return 0;
//    }

    public static void main(String[] args) {
        //TicTacToeApp ticTacToeApp = new TicTacToeApp();
        launch(args);
    }
}