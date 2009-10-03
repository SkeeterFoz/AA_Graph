package controller;

import model.Graph;

public class Parser {

	private byte[][] matriz;
	
	/**
	 * Realiza o parser do vetor de strings passado
	 * @param v Vetor com as strings
	 * @param nl Numero de linhas do vetor
	 */
	public Parser(String[] v, int nl) {
		this.matriz = new byte[nl][nl];
		
		int i = 0;
		while (i < nl) {
			int c = 0;
			int tmp = 0;
			while (c < nl) {
				if (v[i].charAt(tmp) == '0') {
					this.matriz[i][c] = 0;
					c++;
				} else if (v[i].charAt(tmp) == '1') {
					this.matriz[i][c] = 1;
					c++;
				}
				tmp++;
			}
			i++;
		}
	}
	
	public void printGraph(int nl) {
		for (int i = 0; i < nl; i++) {
			for (int j = 0; j < nl; j++) {
				System.out.print(matriz[i][j]);
				System.out.print(' ');
			}
			System.out.println(" ");
		}
	}
	
	public void TextToGraph(String _path, Graph g) throws Exception {
	}
	
}

