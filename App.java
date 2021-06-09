import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
 
public class App extends Application implements EventHandler<KeyEvent>{
    
    public static void main(String[] args) {
        launch(args);
    }
    //global variables
    Boolean game = true;
    Rectangle myGamePiece = new Rectangle(50,50);
    Rectangle Obsticle = new Rectangle(100,50);
    Rectangle Safesticle = new Rectangle(100,50);
    Rectangle Finish = new Rectangle(50,50);
    TranslateTransition translateTransition = new TranslateTransition(); 
    TranslateTransition translateTransitiontest = new TranslateTransition(); 
    Button restart_btn = new Button();
    //test code
    int n=250,m=685;
    int round=1;
    Text test = new Text("");
    Text gametest = new Text("Round : "+round);
    double speed1=0.05,speed2=0.05;

    
    @Override
    public void start(Stage primaryStage) throws Exception{
        //-----
        test.setX(238);test.setY(15);
        gametest.setX(400);gametest.setY(35);
        gametest.setFill(Color.YELLOW);
        //-----
        myGamePiece.setX(250);
        myGamePiece.setY(685);
        myGamePiece.setFill(Color.RED);
        //button
        restart_btn.setLayoutX(242);
        restart_btn.setLayoutY(350);
        restart_btn.setScaleX(6);
        restart_btn.setScaleY(5);
        restart_btn.setVisible(false);
        restart_btn.setText("restart");
        //obsticles
        Obsticle.setX(-100);
        Obsticle.setY(575);
        Obsticle.setFill(Color.BLUE);

        Safesticle.setX(0);
        Safesticle.setY(135);
        Safesticle.setFill(Color.rgb(106, 27, 10 ));

        Finish.setX(250);
        Finish.setY(25);
        Finish.setFill(Color.GREEN);
        //obsticales movement
        translateTransitiontest.setNode(Safesticle); 
        translateTransition.setNode(Obsticle); 
        //
        translateTransition.setByX(650); 
        translateTransition.setCycleCount(1000); 
        translateTransition.setAutoReverse(false);  
        translateTransition.play(); 
        //
        translateTransitiontest.setByX(550); 
        translateTransitiontest.setCycleCount(1000); 
        translateTransitiontest.setAutoReverse(false);  
        translateTransitiontest.play(); 
        
        //
        //update
        Timer timer = new Timer(); 
        TimerTask task = new MyTask(); 
        timer.schedule(task, 100, 10); 
        Pane pane = new Pane();
        pane.setId("pane");
        pane.getChildren().addAll(Safesticle,myGamePiece,Obsticle,test,Finish,restart_btn,gametest);
        Scene scene = new Scene(pane, 550, 750);
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm()); 
        scene.setOnKeyPressed(this);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //updating cords
    class MyTask extends TimerTask 
    {  
        public void run() {  
          //collision obsticle detector
        if(myGamePiece.getX()<(Obsticle.getTranslateX()+0) && (myGamePiece.getX()+50)>(Obsticle.getTranslateX()-100) && myGamePiece.getY()==Obsticle.getY()){
            game=false;
        }else{
            game=true;
        }  
          //collision safesticle detector
        if(myGamePiece.getX()>=Safesticle.getTranslateX()-49 && (myGamePiece.getX()+50)<=Safesticle.getTranslateX()+149 && myGamePiece.getY()==Safesticle.getY()){
            game=true;
        }else if(myGamePiece.getX()<Safesticle.getTranslateX()-49 && myGamePiece.getY()==Safesticle.getY()){
            game=false;
        }else if((myGamePiece.getX()+50)>Safesticle.getTranslateX()+149 && myGamePiece.getY()==Safesticle.getY()){
            game=false;
        }
        translateTransition.setRate(speed1);
        translateTransitiontest.setRate(speed2);
        gametest.setText("Round : "+round);
        if(game==false){
            test.setText("GAMEOVER");
            translateTransition.pause();
            translateTransitiontest.pause();
            restart_btn.setVisible(true);
            
            EventHandler<ActionEvent> eveniment = new EventHandler<ActionEvent>() { 
                public void handle(ActionEvent e) 
                {   myGamePiece.setX(250);
                    myGamePiece.setY(685);
                    n=250;m=685;
                    speed1=0.05;
                    speed2=0.05;
                    round=1;
                    test.setText("");
                    restart_btn.setVisible(false);
                    translateTransition.play();
                    translateTransitiontest.play();
                    game=true;
                } 
            }; 
            
            restart_btn.setOnAction(eveniment);
        }
     } 
    }
    @Override
    public void handle (KeyEvent event){
        //control commands for player
        if(game==true){
        if(event.getCode().toString().equals("D")){
            myGamePiece.setX(n+50);
            n+=50;
        }else if(event.getCode().toString().equals("A")){
            myGamePiece.setX(n-50);
            n-=50;
        }if(event.getCode().toString().equals("S")){
            myGamePiece.setY(m+55);
            m+=55;
        }else if(event.getCode().toString().equals("W")){
            myGamePiece.setY(m-55);
            m-=55;
        }
    }
        //after finishing a level makes it harder
        if(myGamePiece.getX()==Finish.getX() && myGamePiece.getY()==Finish.getY()){
            myGamePiece.setX(250);
            myGamePiece.setY(685);
            n=250;m=685;
            speed1+=0.05;
            speed2+=0.02;
            round++;
        }
        
    }
    
    
    
}