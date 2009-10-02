package view;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;

import controller.IOFile;

// TEste de commit
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
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		controle.setArquivo(fc.getSelectedFile().getAbsolutePath());
//		IO fluxo = new IO(controle.getArquivo());
	}

}
