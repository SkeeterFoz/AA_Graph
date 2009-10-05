package controller;

import model.Graph;

public class DepthFirstSearch {
	private Graph grafo;
	
	/**
	 * Matriz que representa a estrutura da Busca em Profundidade com cada 
	 * linha representando os dados de um vertice e as colunas conforme segue:
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
	 *  
	 *  A contagem de vertices começa em 1, sendo assim a linha 0 representa os dados
	 *  do vértice 1
	 */
	private int[][] dfs;

	private final static byte BRANCO = 0;
	private final static byte CINZA = 1;
	private final static byte PRETO = 2;
	
	private final static byte DESCOBERTA = 0; // D
	private final static byte TERMINO = 1; // F
	private final static byte PREDECESSOR = 2; //PI
	private final static byte COR = 3;
	
	private int timestamp;
	
	public DepthFirstSearch(Graph grafo) {
		this.grafo = grafo;
		this.dfs = new int[grafo.getNlc()][4];
		
		timestamp = 0;
		
		for (int i = 0; i < grafo.getNlc(); i++) {
			if (this.dfs[i][COR] == BRANCO) 	//Se a cor do vértice for branco 
				DFS_Visit(i);
		}
	}
	
	private void DFS_Visit(int u) {
		this.dfs[u][COR] = CINZA;
		this.dfs[u][DESCOBERTA] = ++timestamp;
		
		for (int i = 0; i < grafo.getNlc(); i++) {
			if (grafo.getElement(u, i) == 1) {
				if (this.dfs[i][COR] == BRANCO) {
					this.dfs[i][PREDECESSOR] = u;
					DFS_Visit(i);
				}
			}
		}
		this.dfs[u][COR] = PRETO;
		this.dfs[u][TERMINO] = ++timestamp;
	}
	
	public int[] getTermino() {
		int[] result = new int[grafo.getNlc()];
		for (int i = 0; i < grafo.getNlc(); i++)
			result[i] = this.dfs[i][TERMINO];
		
		return result;
	}
}
