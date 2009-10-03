package model;

public class Graph {
	
	/**
	 * Matriz adjacência que representa o grafo
	 */
	private byte[][] matriz;
	
	/**
	 * Numero de linhas e colunas
	 */
	private int nlc;
	
	public Graph(int nl) {
		this.matriz = new byte[nl][nl];
		this.nlc = nl;
	}
	
	
	/**
	 * @return the nlc
	 */
	public int getNlc() {
		return nlc;
	}

	/**
	 * @param nlc the nlc to set
	 */
	public void setNlc(int nlc) {
		this.nlc = nlc;
	}
	
	public byte getElement(int i, int j) {
		return this.matriz[i][j];
	}
	
	public void setElement(int i, int j, int valor) {
		this.matriz[i][j] = (byte) valor;
	}

	/**
	 * @return the matriz
	 */
	public byte[][] getMatriz() {
		return matriz;
	}

	/**
	 * @param matriz the matriz to set
	 */
	public void setMatriz(byte[][] matriz) {
		this.matriz = matriz;
	}
	
	public void printGraph() {
		for (int i = 0; i < this.nlc; i++) {
			for (int j = 0; j < this.nlc; j++) {
				System.out.print(matriz[i][j]);
				System.out.print(' ');
			}
			System.out.println(" ");
		}
	}
}
