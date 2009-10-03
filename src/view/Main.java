package view;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;

import controller.IOFile;
import controller.Parser;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFileChooser fc = new JFileChooser(".");
		int returnVal = fc.showOpenDialog(null);
		
		if(returnVal != JFileChooser.APPROVE_OPTION) return; 
		
		try {
			IOFile io = new IOFile(fc.getSelectedFile().getAbsolutePath());
			io.Read();
			
			Parser parser = new Parser(io.getV(), io.getN());
			parser.printGraph(io.getN());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
