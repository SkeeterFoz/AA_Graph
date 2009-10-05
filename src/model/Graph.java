package model;

import controller.Operacoes;

public class Graph {
	
	/**
	 * Matriz adjacência que representa o grafo
	 */
	private byte[][] matriz;
	
	/**
	 * Numero de linhas e colunas
	 */
	private int nlc;
	
	private boolean orientado;
	
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
	
	

	public boolean isOrientado() {
		return orientado;
	}


	public void setOrientado(boolean orientado) {
		this.orientado = orientado;
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
	
	public Graph getGraphTransposto() {
		Graph gtmp = new Graph(nlc);
		
		for (int i=0; i < nlc; i++)
			for (int j=0; j < nlc; j++)
				gtmp.setElement(j, i, matriz[i][j]);
		
		gtmp.setOrientado(Operacoes.isOrientado(gtmp));
		return gtmp;
	}
	
//	public byte[] getAdj(int i) {
//		byte[] adj = new byte[this.nlc];
//		
//		return adj;
//	}
}
