package controller;

import java.util.ArrayList;

import model.Graph;
import utils.DepthFirstSearch;

/**
 * Operações a serem realizadas no grafo
 * @author newton
 *
 */
public class Operacoes {

	/**
	 * Verifica se um grafo é orientado
	 * @param grafo O Grafo que irá ser verificado
	 * @return True caso o grafo seja orientado
	 */
	public static boolean isOrientado(Graph grafo) {
		byte orientado = 0;
		
		for (int i = 0; i < grafo.getNlc(); i++) {
			for (int j = 0; j < grafo.getNlc(); j++) {
				if (grafo.getElement(i, j) != grafo.getElement(j, i)) { //Verifica se elementos i,j são iguais a j,i
					orientado = 1;
				}
			}
		}
		if (orientado == 1) {
			return true;
		} else return false;
	}
	
	/**
	 * Realiza a Ordenação Topológica
	 * @param grafo Grafo em que se buscará a ordenação
	 * @return Uma lista com a sequência de elementos ordenados topologicamente
	 */
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
	
	public static  ArrayList<ArrayList<Integer>> ComponentesConexas(Graph grafo) {
		DepthFirstSearch dfs = new DepthFirstSearch(grafo);
	
		dfs.setDoComponentes(true);
		dfs.run();

		return dfs.getComponentes();
	}
	
	public static Graph VerticesDeCorte(Graph grafo) {
		DepthFirstSearch dfs = new DepthFirstSearch(grafo);
		
		dfs.setDoComponentes(true);
		dfs.run();
		
		grafo.printGraph();
		
		int numComponentes = dfs.getComponentes().size();
		int[] vetorH = new int[grafo.getNlc()];

		for (int i = 0; i < grafo.getNlc(); i++) {
			for (int j = 0; j < grafo.getNlc(); j++) {
				vetorH[j] = grafo.getElement(i, j);
				grafo.setElement(i, j, 0);
			}
			DepthFirstSearch dfsTmp = new DepthFirstSearch(grafo);
			
			dfsTmp.setDoComponentes(true);
			dfsTmp.run();

			for (int j = 0; j < grafo.getNlc(); j++) {
				grafo.setElement(i, j, vetorH[j]);
				vetorH[j] = 0;
			}
			
			if (dfsTmp.getComponentes().size() > numComponentes) {
				for (int j = 0; j < grafo.getNlc(); j++) {
					if (grafo.getElement(i, j) > 0) {
						grafo.setElement(i, j, 2);
						grafo.setElement(j, i, 2);
					}
				}
			}
		}
		System.out.println(" ");
		grafo.printGraph();
		return grafo;
	}
	
	public static Graph ArestasDeCorte(Graph grafo) {
		DepthFirstSearch dfs = new DepthFirstSearch(grafo);
		
		dfs.setDoComponentes(true);
		dfs.run();
		
		int numComponentes = dfs.getComponentes().size();
		int tmp;

		for (int i = 0; i < grafo.getNlc(); i++) {
			for (int j = 0; j < grafo.getNlc(); j++) {
				tmp = grafo.getElement(i, j);
				grafo.setElement(i, j, 0);

				DepthFirstSearch dfsTmp = new DepthFirstSearch(grafo);

				dfsTmp.setDoComponentes(true);
				dfsTmp.run();

				if (dfsTmp.getComponentes().size() > numComponentes) {
					grafo.setElement(i, j, 2);
					grafo.setElement(j, i, 2);
				} else {
					grafo.setElement(i, j, tmp);
				}
				tmp = 0;
			}
		}
		return grafo;
	}
	
	public static ArrayList<ArrayList<Integer>> Biparticao(Graph grafo) {
		int[] Cor = new int[grafo.getNlc()];

		for (int i = 0; i < grafo.getNlc(); i++) {
			if (Cor[i] == 0) {
				Cor[i] = 1;
				if (!Biparticao_Visit(grafo, i, Cor, 2)) {
					return null;
				}
			}
		}
		
		ArrayList<ArrayList<Integer>> resultado = new ArrayList<ArrayList<Integer>>();
		resultado.add(new ArrayList<Integer>());
		resultado.add(new ArrayList<Integer>());
		for (int i = 0; i < Cor.length; i++) {
			if (Cor[i] == 1) {
				resultado.get(0).add(i);
			} else {
				resultado.get(1).add(i);
			}
		}
		
		return resultado;
	}
	
	private static boolean Biparticao_Visit(Graph grafo, int u, int[] cor, int corPai) {
		if (corPai == 1) {
			cor[u] = 2;
		} else {
			cor[u] = 1;
		}
		for (int i = 0; i < grafo.getNlc(); i++) {
			if (grafo.getElement(u, i) > 0) {
				if (cor[i] == cor[u])
					return false;
				Biparticao_Visit(grafo, i, cor, cor[u]);
			}
		}
		return true;
	}
}
