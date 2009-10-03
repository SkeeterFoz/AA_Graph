package controller;

import model.Graph;

public class Parser {

	private Graph grafo;
	
	/**
	 * @return the grafo
	 */
	public Graph getGrafo() {
		return grafo;
	}

	/**
	 * @param grafo the grafo to set
	 */
	public void setGrafo(Graph grafo) {
		this.grafo = grafo;
	}

	/**
	 * Realiza o parser do vetor de strings passado
	 * @param v Vetor com as strings
	 * @param nl Numero de linhas do vetor
	 */
	public Parser(String[] v, int nl) {
		this.grafo = new Graph(nl);
		
		int i = 0;
		while (i < nl) {
			int c = 0;
			int tmp = 0;
			while (c < nl) {
				if (v[i].charAt(tmp) == '0') {
					this.grafo.setElement(i, c, 0);
					c++;
				} else if (v[i].charAt(tmp) == '1') {
					this.grafo.setElement(i, c, 1);
					c++;
				}
				tmp++;
			}
			i++;
		}
	}	
}

