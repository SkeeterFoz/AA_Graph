package model;

public class Graph {
	private byte[][] matriz;
	
	/**
	 * Numero de linhas e colunas
	 */
	private int nlc;

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
	
	
}
