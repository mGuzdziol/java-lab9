import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import static javafx.scene.shape.StrokeType.INSIDE;

public class TicTacToeApp extends Application {

    private static boolean ifFirst = true;
    private boolean playable = true;
    private boolean turnX = true;
    private Tile[][] board = new Tile[4][4];
    private List<Combo> combos = new ArrayList<>();
    ChoiceBox choiceBox = new ChoiceBox();
    HBox hbox = new HBox(choiceBox);
    ChoiceBox choiceBox2 = new ChoiceBox();
    HBox hbox2 = new HBox(choiceBox);
    ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

    private Pane root = new Pane();

    private Parent createContent() {
        root.setPrefSize(400, 500);

        choiceBox.setTranslateX(200);
        choiceBox.setTranslateY(450);
        choiceBox2.setTranslateX(50);
        choiceBox2.setTranslateY(450);
        root.getChildren().addAll(choiceBox,choiceBox2);

        choiceBox2.getItems().add("JavaScript");
        choiceBox2.getItems().add("C++");

        choiceBox2.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                System.out.println(choiceBox2.getItems().get((Integer) number2));

                String path;
                if(choiceBox2.getItems().get((Integer) number2)=="JavaScript")
                    path="D:\\Studia\\Sem6\\java\\cw9\\java-lab9\\js\\";
                else
                    path="D:\\Studia\\Sem6\\java\\cw9\\java-lab9\\js\\";

                File directory = new File(path);
                File[] children = directory.listFiles();
                for (File child : children) {
                    if (child.isFile() && child.getName().contains(".js")) {
                        choiceBox.getItems().add(child.getName());
                    }
                }
            }
        });
//        try {
//            engine.eval(new FileReader("D:\\Studia\\Sem6\\java\\cw9\\java-lab9\\js\\advancedAlg.js"));
//            Invocable invocable = (Invocable) engine;
//            Object result;
//            result = invocable.invokeFunction("display", "helloWorld");
//            System.out.println(result);
//        } catch (FileNotFoundException | NoSuchMethodException | ScriptException e) {
//            e.printStackTrace();
//        }

//        choiceBox.getItems().add("Choice 1");
//        choiceBox.getItems().add("Choice 2");

       // root.getChildren()


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

    private boolean checkState() {
        for (Combo combo : combos) {
            if (combo.isComplete()) {
                playable = false;
                playWinAnimation(combo);
                return true;
            }
        }
        return false;
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

    static {
        System.loadLibrary("native");
    }

    private native void sayHello();

    private class Tile extends StackPane {
        private Text text = new Text();
        long cnt=0;

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

                // if(ifFirst==false){
                //if (turnX){
                if(!text.getText().isEmpty())
                    return;
                cnt=0;
                drawX();
                turnX = false;
                //checkState();

                if(!checkState()){
                int[] tab =enemyTurn();

                board[tab[0]][tab[1]].text.setText("O");
                turnX = true;
                checkState();}

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


        public int[] enemyTurn()
        {
//            String[][] miniMaxBoard = new String[4][4];
//            for (int i = 0; i < 4; i++) {
//                for (int j = 0; j < 4; j++) {
//                    miniMaxBoard[j][i]= board[j][i].text.getText();
//                }
//            }

            String miniMaxBoard = new String();

            int z=0;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    miniMaxBoard += board[j][i].text.getText()+" ";
                    z++;
                }
            }

            Object result=null;

            int[] intArray=null;
            if(choiceBox2.getSelectionModel().getSelectedItem().toString()=="JavaScript") {
                try {
                    engine.eval(new FileReader("D:\\Studia\\Sem6\\java\\cw9\\java-lab9\\js\\"+choiceBox.getSelectionModel().getSelectedItem().toString()));
                    Invocable invocable = (Invocable) engine;
                    result = invocable.invokeFunction("algorithm", miniMaxBoard);
                } catch (FileNotFoundException | NoSuchMethodException | ScriptException e) {
                    e.printStackTrace();
                }

                String str = (String) result;
                String strArray[] = str.split(" ");

                intArray = new int[strArray.length];
                for (int i = 0; i < strArray.length; i++) {
                    intArray[i] = Integer.parseInt(strArray[i]);
                }
            }

//            for (int i = 0; i < 4; i++) {
//                for (int j = 0; j < 4; j++) {
//                    if(miniMaxBoard[j][i].equals("")) {
//                        miniMaxBoard[j][i] ="O";
//                        if (wygrana(miniMaxBoard, "O"))
//                            return new int[]{j, i};
//                        miniMaxBoard[j][i] = "";
//                    }
//                }
//            }
//
//            for (int i = 0; i < 4; i++) {
//                for (int j = 0; j < 4; j++) {
//                    if(miniMaxBoard[j][i].equals(""))
//                    {
//                        miniMaxBoard[j][i] ="X";
//                        if(wygrana(miniMaxBoard,"X"))
//                            return new int[]{j,i};
//                        miniMaxBoard[j][i]="";
//                    }
//                }
//            }
//
//            Random r = new Random();
//            int x = r.nextInt((3 - 0) + 1) + 0;
//            int y = r.nextInt((3 - 0) + 1) + 0;
//
//            while(miniMaxBoard[x][y]=="X" || miniMaxBoard[x][y]=="O"){
//                x = r.nextInt((3 - 0) + 1) + 0;
//                y = r.nextInt((3 - 0) + 1) + 0;
//            }
//
            return intArray;
        }

        public boolean wygrana(String t[][], String g)
        {
            boolean test;

            test = false;

            for (int y = 0; y < 4; y++) {
                test |= ((t[0][y] == g) && (t[1][y] == g) && (t[2][y] == g));
                test |= ((t[1][y] == g) && (t[2][y] == g) && (t[3][y] == g));
            }

            for (int x = 0; x < 4; x++) {
                test |= ((t[x][0] == g) && (t[x][1] == g) && (t[x][2] == g));
                test |= ((t[x][1] == g) && (t[x][2] == g) && (t[x][3] == g));
            }

            test |= ((t[0][0] == g) && (t[1][1] == g) && (t[2][2] == g));
            test |= ((t[2][0] == g) && (t[1][1] == g) && (t[0][2] == g));
            test |= ((t[1][0] == g) && (t[2][1] == g) && (t[3][2] == g));
            test |= ((t[3][0] == g) && (t[2][1] == g) && (t[1][2] == g));
            test |= ((t[0][1] == g) && (t[1][2] == g) && (t[2][3] == g));
            test |= ((t[2][1] == g) && (t[1][2] == g) && (t[0][3] == g));
            test |= ((t[1][1] == g) && (t[2][2] == g) && (t[3][3] == g));
            test |= ((t[3][1] == g) && (t[2][2] == g) && (t[1][3] == g));

            if(test)
            {
                return true;
            }
            return false;
        }

    }


    public static void main(String[] args) {
        //TicTacToeApp ticTacToeApp = new TicTacToeApp();
        launch(args);
    }
}