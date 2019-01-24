package cst8284.assignment1;



import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import cst8284.assignment1.FileUtils;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TaskManager extends Application {

	private ArrayList<ToDo> toDoArray;
	private static int currentToDoElement;
	private Stage primaryStage;
	TextField txfTitle;
	TextArea txaSubject;
	TextField  txfDate;
	@Override
	public void start(Stage primaryStage) {
		setPrimaryStage(primaryStage);
		primaryStage.setTitle("To Do List");
		primaryStage.setScene(getSplashScene(new Text("JAVA fx CREATED BY TEJVEER SINGH")));
		primaryStage.show();
	}

	public Scene getSplashScene(Text defaultText) {
		Rectangle rect = new Rectangle(5000,1000,5000,5600);
		rect.setArcHeight(20);
		rect.setArcWidth(30);
		rect.setFill(Color.YELLOW);


		defaultText.setFont(Font.font("verdana",60));

		StackPane startPane = new StackPane();
		startPane.setPrefHeight(100);
		startPane.setPrefWidth(200);



		FadeTransition ft = new FadeTransition(Duration.seconds(3),defaultText);
		ft.setFromValue(1.0f);
		ft.setToValue(0.3f);
		ft.setAutoReverse(true);
		ft.setCycleCount(4);

		RotateTransition rt = new RotateTransition(Duration.seconds(3),defaultText);
		rt.setByAngle(180f);
		rt.setAutoReverse(true);
		rt.setCycleCount(4);


		ParallelTransition pt = new ParallelTransition(rect, ft, rt);
		pt.play();
		

		startPane.setOnMouseClicked(e -> {


			filechoose();
			//getPrimaryStage().setScene(getToDoScene(null));
		});
		
		startPane.getChildren().addAll(rect,defaultText);


		Scene scene = new Scene(startPane, 1024, 768);
		return scene;
	}

	public Scene getToDoScene(ToDo td) {
		if (td == null) {  // use null to signal initial setup, i.e. ToDo[0]
			FileUtils fUtils = new FileUtils();
			//ArrayList<ToDo> tdRawArray = fUtils.getToDoArray(filechoose());
			//ArrayList<ToDo> tdCompactArray = getToDoArrayWithoutEmpties(tdRawArray);

			setToDoElement(0);
			td = getToDoArray().get(getToDoElement());
		}
		return (new Scene(getToDoPane(td)));
	}

	public BorderPane getToDoPane(ToDo td) {
		VBox vbLeft = new VBox();
		vbLeft.setMinWidth(120);

		VBox vbRight = new VBox();
		vbRight.setMinWidth(120);

		HBox hb =new HBox();
		hb.setMinHeight(50);
        hb.setMaxWidth(150);
        
        
        
        
		Menu fileMenu = new Menu("File");
		MenuItem newMenuItem = new MenuItem("Open");
		newMenuItem.setOnAction(e -> {
			filechoose();
		});
		MenuItem saveMenuItem = new MenuItem("Save");
		saveMenuItem.setOnAction(e -> {
			
			td.setTitle(txfTitle.getText());
			getToDoArray().add(getToDoElement(), td);
		    getPrimaryStage().setScene(getToDoScene(getToDoArray().get(getToDoElement())));	
		});
		MenuItem AddToDoMenuItem = new MenuItem("Add To Do");
		AddToDoMenuItem.setOnAction(e -> {
			ToDo tdNew = new ToDo(txaSubject.getText(),"Demo Test #" + (int)(Math.random() *1000), (int)(Math.random()*3+1),new Date(), false, false, false);
			getToDoArray().add(getToDoElement(), tdNew);
		    getPrimaryStage().setScene(getToDoScene(getToDoArray().get(getToDoElement())));	
		});
		
				MenuItem RemoveToDoMenuItem = new MenuItem("Remove To Do");

		RemoveToDoMenuItem.setOnAction(e -> {
			getToDoArray().remove(getToDoElement());
			setToDoElement(getToDoElement()==0?0:getToDoElement()-1);
			getPrimaryStage().setScene(getToDoScene(getToDoArray().get(getToDoElement())));
		});
		
		MenuItem exitMenuItem = new MenuItem("Exit");
		MenuBar menubar = new MenuBar();
		menubar.getMenus().add(fileMenu);
		exitMenuItem.setOnAction(actionEvent -> Platform.exit());

		fileMenu.getItems().addAll(newMenuItem, saveMenuItem,AddToDoMenuItem,RemoveToDoMenuItem,exitMenuItem);

		

		BorderPane rootNode = new BorderPane();
		rootNode.setLeft(vbLeft);
		rootNode.setRight(vbRight);
		rootNode.setTop(menubar);
		rootNode.setBottom(getBottomPane(td, rootNode));
		rootNode.setCenter(getCenterPane(td));
		return rootNode;
	}



	private Object currenttodoElement() {
		// TODO Auto-generated method stub
		return null;
	}


	public GridPane getCenterPane(ToDo td) {

		GridPane gp = new GridPane();
		gp.setPadding(new Insets(50));
		gp.setPrefWidth(1200);

		gp.setVgap(10);
		gp.setHgap(40);
		gp.setStyle("-fx-font: 20px Tahoma; -fx-stroke: black; -fx-stroke-width: 1;");

		Label lblTask = new Label("Task");
		gp.add(lblTask, 0, 0);
		 txfTitle = new TextField(td.getTitle());
		gp.add(txfTitle, 1, 0);

		Label lblSubject = new Label("Subject");
		gp.add(lblSubject, 0, 1);
		 txaSubject = new TextArea(td.getSubject());
		gp.add(txaSubject, 1, 1);

		Label lblDate = new Label("Due Date");
		gp.add(lblDate, 0, 2);
		 txfDate = new TextField(td.getDueDate().toString());
		gp.add(txfDate, 1, 2);

		Label lblPriority = new Label("Priority");
		gp.add(lblPriority, 0, 3);
		ToggleGroup tglGroup = new ToggleGroup();
		RadioButton rb1 = (new RadioButton("1   "));
		rb1.setToggleGroup(tglGroup);
		RadioButton rb2 = (new RadioButton("2   "));
		rb2.setToggleGroup(tglGroup);
		RadioButton rb3 = (new RadioButton("3   "));
		rb3.setToggleGroup(tglGroup);
		int pr = td.getPriority();
		RadioButton rbSet = (pr == 1) ? rb1 : (pr == 2) ? rb2 : (pr == 3) ? rb3 : null;
		rbSet.setSelected(rbSet != null);
		HBox hRadioButtons = new HBox();
		hRadioButtons.getChildren().addAll(rb1, rb2, rb3);
		gp.add(hRadioButtons, 1, 3);
		return gp;
	}

	public HBox getBottomPane(ToDo td, BorderPane root) {

		HBox paneCtlBtns = new HBox(10);
		paneCtlBtns.setStyle("-fx-font: 50px Tahoma; -fx-stroke: black; -fx-stroke-width: 1;");
		paneCtlBtns.setAlignment(Pos.CENTER);
		paneCtlBtns.setPadding(new Insets(50));

		Button btnFirst = new Button("\u23ee"); // btnFirst.setMinSize(80, 80);
		Button btnBack = new Button("\u23ea"); // btnBack.setMinSize(80, 80);
		Button btnNext = new Button("\u23e9"); // btnNext.setMinSize(80, 80);
		Button btnLast = new Button("\u23ed"); // btnLast.setMinSize(80, 80);

		btnFirst.setOnAction(e -> {
			setToDoElement(0);
			btnBack.fire();
		});

		btnBack.setOnAction(e -> {
			int toDoElement = getToDoElement();
			setToDoElement(toDoElement <=0 ? 0 : --toDoElement);
			btnFirst.setDisable(getToDoElement()==0); btnBack.setDisable(getToDoElement()==0);
			btnNext.setDisable(getToDoElement()==getToDoArray().size()-1);
			btnLast.setDisable(getToDoElement()==getToDoArray().size()-1);
			root.setCenter(getCenterPane(getToDoArray().get(getToDoElement())));
		});

		btnLast.setOnAction(e -> {
			setToDoElement(getToDoArray().size()-1);
			btnNext.fire();
		});

		btnNext.setOnAction(e -> {
			int toDoElement = getToDoElement();
			if (toDoElement < getToDoArray().size() -1) setToDoElement(++toDoElement);
			btnFirst.setDisable(toDoElement==0); btnBack.setDisable(toDoElement==0);
			btnNext.setDisable(toDoElement==getToDoArray().size()-1);
			btnLast.setDisable(toDoElement==getToDoArray().size()-1);
			root.setCenter(getCenterPane(getToDoArray().get(getToDoElement())));
		});

		if (getToDoElement()==0) btnFirst.fire();  // set default button conditions
		paneCtlBtns.getChildren().addAll(btnFirst, btnBack, btnNext, btnLast);
		return paneCtlBtns;
	}

	/*private ToDo[] getToDoArrayWithoutEmpties(ToDo[] tdRawAr) {
		int toElement = 0;
		for (int fromElement = 0; fromElement < tdRawAr.length; fromElement++)
			if (!tdRawAr[fromElement].isEmptySet())
				tdRawAr[toElement++] = tdRawAr[fromElement];
		ToDo[] fullArray = new ToDo[toElement];
		System.arraycopy(tdRawAr, 0, fullArray, 0, toElement);
		return fullArray;*/

	private ArrayList<ToDo> getToDoArrayWithoutEmpties(ArrayList<ToDo> tdRawArray) {

		ArrayList<ToDo> fullArray = (ArrayList<ToDo>)tdRawArray.clone();



		return fullArray;}



	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage stage) {
		this.primaryStage = stage;
	}

	public void setToDoArray(ArrayList<ToDo> td) {
		toDoArray = td;
	}

	public ArrayList<ToDo> getToDoArray() {
		return this.toDoArray;
	}

	public static void setToDoElement(int currentElementNumber){
		currentToDoElement = currentElementNumber;
	}

	public static int getToDoElement(){
		return currentToDoElement;
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	public void filechoose(){
		FileChooser fc = new FileChooser( );
		fc.setTitle("Open ToDO File");
		fc.getExtensionFilters().addAll( new ExtensionFilter("Quiz Files", "*.todo"),
				new ExtensionFilter("All Files", "*.*"));	 

		File todoFile1 = fc.showOpenDialog(getPrimaryStage());
		FileUtils.getAbsPath(todoFile1);
		if(FileUtils.fileExists(todoFile1)){
			
			FileUtils f = new FileUtils();
			f.getToDoArray(f.getAbsPath(todoFile1));
			setToDoArray(f.getToDoArray(f.getAbsPath(todoFile1)));
			 getPrimaryStage().setScene(getToDoScene(toDoArray.get(getToDoElement())));

			//FileUtils.getToDoArray(FileUtils.);
			 
		}
		else{
			Alert alert = new Alert(AlertType.CONFIRMATION, "No file ENTERED? Do You wish to exit(OK) or  continue (Cancel) ");
			alert.showAndWait();
			
		}
		


	}
}
