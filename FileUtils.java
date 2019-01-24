package cst8284.assignment1;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class FileUtils {

	
	private static String relPath ;//= "ToDoList.todo";
	
	public ArrayList<ToDo> getToDoArray(String fileName) {
		ArrayList<ToDo> toDos = new ArrayList<>();
		try {
			FileInputStream fis = getFIStreamFromAbsPath(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			 ToDo e; 
			try {
				while(true){
					 e = (ToDo)(ois.readObject());
					 toDos.add(e);
				}
			} catch (IOException x){
				x.getMessage();
				ois.close();
			}
      
      
			
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
 		return toDos ;
	}
	
	public static FileInputStream getFIStreamFromAbsPath(String absPath){
		FileInputStream fis = null;
		try {
     		fis = new FileInputStream(absPath);
		} catch (IOException e){
			e.printStackTrace();
		}
		return fis;
	}
	
	public static String getAbsPath() {
		return relPath;
	}

	public static String getAbsPath(File f) {
		return f.getAbsolutePath();
	}

	public static void setAbsPath(File f) { 
		relPath = (fileExists(f))? f.getAbsolutePath():""; 
	}
	
	public static Boolean fileExists(File f) {
		return (f != null && f.exists() && f.isFile() && f.canRead());
	}

}
