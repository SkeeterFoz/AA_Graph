package controller;

import java.util.ArrayList;

import model.Graph;
import utils.DepthFirstSearch;

public class Operacoes {

	public static boolean isOrientado(Graph grafo) {
		byte orientado = 0;
		
		for (int i = 0; i < grafo.getNlc(); i++) {
			for (int j = 0; j < grafo.getNlc(); j++) {
				if (grafo.getElement(i, j) != grafo.getElement(j, i)) {
					orientado = 1;
				}
			}
		}
		if (orientado == 1) {
			return true;
		} else return false;
	}
	
	public static ArrayList<Integer> OrdenacaoTopologica(Graph grafo) {
		DepthFirstSearch dfs = new DepthFirstSearch(grafo);
		dfs.doListaTermino(true);
		
		dfs.run();
		ArrayList<Integer> result = dfs.getListaTermino();

		return result;
	}
	
	public static Graph FechoTransitivo(Graph grafo) {
		Graph resultado = new Graph(grafo.getNlc());
		resultado.setMatriz(grafo.getMatriz());
		
		for (int i = 0; i < resultado.getNlc(); i++)
			for (int j = 0; j < resultado.getNlc(); j++) {
				for (int k = 0; k < resultado.getNlc(); k++) {
					if ((resultado.getElement(j, i) > 0) && (resultado.getElement(i, k) > 0)) {
						resultado.setElement(j, k, 2);
					}
				}
			}
		return resultado;		
	}
	
	public static Graph ComponentesFortementeConexas(Graph grafo) {
		DepthFirstSearch dfs = new DepthFirstSearch(grafo);
	
		dfs.doListaTermino(true);
		dfs.run();
		ArrayList<Integer> fechamentoGrafo = dfs.getListaTermino();
		
		DepthFirstSearch dfsT = new DepthFirstSearch(grafo.getGraphTransposto());
		
		int[] fechamento = new int[fechamentoGrafo.size()];
		for(int i = 0; i < fechamentoGrafo.size(); i++)
			fechamento[i] = fechamentoGrafo.get(i);
		dfsT.setDoComponentes(true);
		dfsT.run(fechamento);

		ArrayList<ArrayList<Integer>> componentes = dfsT.getComponentes();
		
		for (ArrayList<Integer> list:componentes) {
			for (Integer i: list) {
				for (int j = 0; j < grafo.getNlc(); j++) {
					if (!list.contains(j))
						grafo.setElement(i, j, 0);
				}
			}
		}
		return grafo;
	}
}
