package controller;

import model.Graph;

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
	
	public static int[] OrdenacaoTopologica(Graph grafo) {
		DepthFirstSearch dfs = new DepthFirstSearch(grafo);
		int[] termino = dfs.getTermino();
		int[] result = new int[grafo.getNlc()];
		
		for (int i = 0; i < grafo.getNlc(); i++)
			System.out.println("Vertice " + i + ":\t" + termino[i]);
		
		return result;
	}
	
	//TODO arruma a ordenao pra ordenacao topologica
	public static void bubbleSort(int[] a) {
		for (int i = 0; i < a.length-1; i++) {
			for (int j = 0; j < a.length-1; j++) {
				if (a[j] > a[j+1]) {
					swap(a, j, j+1);
				}
			}
		}
	}

	private static void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	
}
