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
}
