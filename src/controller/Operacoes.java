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
		dfs.doListaTermino(true); //Faz com que o DFS gera uma lista encadeada dos vertices ordenados decrescentemente por tempo de termino
		
		dfs.run();
		ArrayList<Integer> result = dfs.getListaTermino(); //Adquiri a lista de termino para ser retornada;

		return result;
	}
	
	/**
	 * Calcula o fecho transitivo de um grafo
	 * @param grafo Grafo que será realizada o calculo do fecho transitivo
	 * @return Grafo com arestas do fecho transitivo adicionadas
	 */
	public static Graph FechoTransitivo(Graph grafo) {
		Graph resultado = new Graph(grafo.getNlc());
		resultado.setMatriz(grafo.getMatriz());
		
		for (int i = 0; i < resultado.getNlc(); i++)
			for (int j = 0; j < resultado.getNlc(); j++) {
				for (int k = 0; k < resultado.getNlc(); k++) {
					//Se existe caminho de j para i e de i para k, logo existe caminho de j para k
					if ((resultado.getElement(j, i) > 0) && (resultado.getElement(i, k) > 0)) {
						resultado.setElement(j, k, 2);
					}
				}
			}
		return resultado;		
	}
	
	/**
	 * Calcula os componentes fortemente conexos de um grafo
	 * @param grafo Estrutura que representa o grafo
	 * @return Um grafo com os componentes fortemente conexos
	 */
	public static Graph ComponentesFortementeConexas(Graph grafo) {
		DepthFirstSearch dfs = new DepthFirstSearch(grafo);
	
		/**
		 * Calcula a lista de termino
		 */
		dfs.doListaTermino(true);
		dfs.run();
		ArrayList<Integer> fechamentoGrafo = dfs.getListaTermino();
		
		//Calcula o DFS para o transposto do Grafo original
		DepthFirstSearch dfsT = new DepthFirstSearch(grafo.getGraphTransposto());
		
		int[] fechamento = new int[fechamentoGrafo.size()];
		for(int i = 0; i < fechamentoGrafo.size(); i++)
			fechamento[i] = fechamentoGrafo.get(i);
		dfsT.setDoComponentes(true); //Habilita a geração de Componentes
		dfsT.run(fechamento);

		ArrayList<ArrayList<Integer>> componentes = dfsT.getComponentes(); //Adquiri as componentes geradas
		
		//Remove as arestas que não pertencem as componentes fortemente conexas
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
	
	/**
	 * Verifica se um grafo possui ciclo e caso ele exista marca os vértices que o compõe
	 * @param grafo Estrutura de dados do gráfico
	 * @return Grafo com o ciclo marcado ou null caso não houver ciclo
	 */
	public static Graph Ciclo(Graph grafo) {
		DepthFirstSearch dfs = new DepthFirstSearch(grafo);
		
		dfs.doListaCiclo(true); //Habilita a geração de lista de ciclo no DFS
		dfs.run();
		
		if (dfs.getCiclo() != null) {
			for (Integer i: dfs.getCiclo()) {
				for (int j = 0; j < grafo.getNlc(); j++) {
					if (dfs.getCiclo().contains(j)) { //Marca vértices que pertencem ao ciclo
						grafo.setElement(i, j, 2);
						grafo.setElement(j, i, 2);
					}
				}
			}
			return grafo;
		}
		return null;
	}
	
	/**
	 * Descobre as componentes conexas existentes no grafo
	 * @param grafo Estrutura de dados do grafo a ser explorado
	 * @return Lista de compenetes conexas presentes no grafo
	 */
	public static  ArrayList<ArrayList<Integer>> ComponentesConexas(Graph grafo) {
		DepthFirstSearch dfs = new DepthFirstSearch(grafo);
	
		dfs.setDoComponentes(true); //Habilita a geração de componentes
		dfs.run();

		return dfs.getComponentes();
	}
	
	/**
	 * Verifica quais os vértices de corte e marca as arestas que chegam até este vértice
	 * @param grafo Estrutura de dados que representa o grafo a ser explorado
	 * @return Grafo com as arestas dos vértices de corte marcadas
	 */
	public static Graph VerticesDeCorte(Graph grafo) {
		DepthFirstSearch dfs = new DepthFirstSearch(grafo);
		
		dfs.setDoComponentes(true); //Habilita a geração de componentes
		dfs.run();
		
		grafo.printGraph();
		
		//Componentes conexas existentes no grafo original
		int numComponentes = dfs.getComponentes().size();
		int[] vetorH = new int[grafo.getNlc()];

		for (int i = 0; i < grafo.getNlc(); i++) {
			//Retira um vértice
			for (int j = 0; j < grafo.getNlc(); j++) {
				vetorH[j] = grafo.getElement(i, j);
				grafo.setElement(i, j, 0);
			}
			DepthFirstSearch dfsTmp = new DepthFirstSearch(grafo);
			
			dfsTmp.setDoComponentes(true); //Habilita a geração de lista de componentes conexas
			dfsTmp.run();

			for (int j = 0; j < grafo.getNlc(); j++) {
				grafo.setElement(i, j, vetorH[j]);
				vetorH[j] = 0;
			}
			
			//Verifica se o número de componentes aumentou
			if (dfsTmp.getComponentes().size() > numComponentes) {
				for (int j = 0; j < grafo.getNlc(); j++) {
					if (grafo.getElement(i, j) > 0) { // Marca arestas do vértice de corte
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
	
	/**
	 * Verifica se uma aresta é de corte e caso seja marca a mesma
	 * @param grafo Estrutura de dados que representa o grafo
	 * @return Grafo com as arestas de corte marcadas
	 */
	public static Graph ArestasDeCorte(Graph grafo) {
		DepthFirstSearch dfs = new DepthFirstSearch(grafo);
		
		dfs.setDoComponentes(true);
		dfs.run();
		
		int numComponentes = dfs.getComponentes().size();
		int tmp;

		for (int i = 0; i < grafo.getNlc(); i++) {
			for (int j = 0; j < grafo.getNlc(); j++) {
				tmp = grafo.getElement(i, j);
				//Remove uma aresta
				grafo.setElement(i, j, 0);

				DepthFirstSearch dfsTmp = new DepthFirstSearch(grafo);

				dfsTmp.setDoComponentes(true);
				dfsTmp.run();
				
				//Verifica se o numero de componentes aumento com a remoção da aresta
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
	
	/**
	 * Verifica se um grafo é bipartido
	 * @param grafo Estrutura de dados utilizada para representar o grafo
	 * @return Lista contendo os dois grupos de elementos caso o grafo se bipartido ou null caso contrário
	 */
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
	
	/**
	 * Visita os elementos do grafo buscando separá-los em 2 grupos
	 * @param grafo Estrutura de dados que representa o grafo
	 * @param u Elemento a ser explorado
	 * @param cor Lista de vertices com suas respectivas cores
	 * @param corPai Cor do vértice pai
	 * @return Retorna true caso foi possível realizar a bipartição ou false caso o grafo não seja bipartido
	 */
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
				if (cor[i] == 0)
					Biparticao_Visit(grafo, i, cor, cor[u]);
			}
		}
		return true;
	}
}
