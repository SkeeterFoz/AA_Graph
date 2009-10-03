package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

public class IOFile {
	
	private String path;
	
	private String line;
	
	private int nl;
	
	private FileReader f;
	
	private BufferedReader in;
	
	private String[] vlines = new String[500];
	
	//Método que verifica a existência e se não existir mostra uma msg na tela
	public IOFile(String _path) throws FileNotFoundException {
		this.path = _path;
		try {
			f = new FileReader(this.path);			
		} catch(FileNotFoundException e) {
			throw new FileNotFoundException("Arquivo não encontrado");
		}
	}
	
	//Lê o arquivo e guarda num vetor de linhas
	public void Read() throws IOException {
		this.nl = 0;
		this.in = new BufferedReader(f);
		
		try {
			line = in.readLine();
			while(line != null) {
				vlines[nl] = line;
//				System.out.println(line);
				line = in.readLine();
				nl++;
			}
			JOptionPane.showMessageDialog(null, "Numero de linhas: " + nl, "AA_Grafos", JOptionPane.INFORMATION_MESSAGE);
		} catch(IOException e) {
			throw(new IOException("Falha de I/O no arquivo"));
		}
	
	}
	
	public int getN() {
		return this.nl;
	}
	
	public String[] getV() {
		return this.vlines;
	}

}
