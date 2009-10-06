package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class IOFile {
	
	private String path;
	
	private String line;
	
	private int nl;
	
	private FileReader f;
	
	private BufferedReader in;
	
	private String[] vlines = new String[500];
	
	/**
	 * @brief Construtor da classe IOFile
	 * @brief Tenta abrir a instancia de um arquivo, caso não exista gera uma exception
	 * @param _path
	 * @throws FileNotFoundException
	 */
	public IOFile(String _path) throws FileNotFoundException {
		this.path = _path;
		try {
			f = new FileReader(this.path);			
		} catch(FileNotFoundException e) {
			throw new FileNotFoundException("Arquivo não encontrado");
		}
	}
	
	/**
	 * @brief Lê arquivo texto linha a linha e armazena num buffer interno
	 * @throws IOException
	 */
	public void Read() throws IOException {
		this.nl = 0;
		this.in = new BufferedReader(f);
		
		
		try {
			// lê linha do arquivo
			line = in.readLine();
			
			// enquanto houver linha no vetor
			while(line != null) {
				
				// armazena linha do arquivo em buffer interno da classe
				vlines[nl] = line;
				
				// lê próxima linha
				line = in.readLine();
				
				// aumenta contador de linhas
				nl++;
			}
				
		} catch(IOException e) {
			throw(new IOException("Falha de I/O no arquivo"));
		}
	
	}
	
	/**
	 * @brief Retorna a quantidade de linhas
	 * @return Quantidade de linhas lidas do arquivo de entrada
	 */
	public int getN() {
		return this.nl;
	}
	
	/**
	 * @brief Retorna o vetor de linhas
	 * @return Vetor de linhas lido do arquivo de entrada
	 */
	public String[] getV() {
		return this.vlines;
	}

}
