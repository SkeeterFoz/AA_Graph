package controller;

import model.Graph;

public class DepthFirstSearch {
	private Graph grafo;
	
	/**
	 * Matriz que representa a estrutura da Busca em Profundidade com:
	 * - a primeira coluna indicando o timestamp de descobert (d[u])
	 * - segunda coluna indicando timestamp de termino (f[u])
	 * - terceira coluna indicando predecessor de u na árvore DFS (pi)
	 * - quarta coluna indicando as cores dos vértices:
	 * 		0 = branco
	 * 		1 = cinza
	 * 		2 = preto 
	 * 
	 * Ex:
	 * 	--------------------
	 *  | d | f | pi | cor |
	 *  --------------------
	 */
	private int[][] dfs;

	public DepthFirstSearch(Graph grafo) {
		this.grafo = grafo;
		this.dfs = new int[grafo.getNlc()][grafo.getNlc()];
	}
}
