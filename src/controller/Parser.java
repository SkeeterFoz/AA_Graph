package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import model.Graph;

/**
 * @author igor
 * @brief Classe responsável pelos parses entre arquivo texto, estrutura de grafo e xml do prefuse
 */
public class Parser {

	private Graph grafo;
	
	/**
	 * @brief Retorna o grafo interno gerado pelo parser
	 * @return Grafo
	 */
	public Graph getGrafo() {
		return grafo;
	}

	/**
	 * @brief Retorna o grafo do parser
	 * @param grafo Grafo
	 */
	public void setGrafo(Graph grafo) {
		this.grafo = grafo;
	}

	/**
	 * @brief Construtor que realiza o parser do vetor de strings passado para um objeto Graph
	 * @param v Vetor com as strings
	 * @param nl Numero de linhas do vetor
	 */
	public Parser(String[] v, int nl) {
		
		// instancia um objeto grafo já com seu tamanho (da matriz de adjacência nl x nl)
		this.grafo = new Graph(nl);
	
		int i=0;

		// varre o vetor de strings, preenchendo o objeto da estrutura grafo
		while (i < nl) {
			int j=0;
			int k=0;
			while (j < nl) {
				// se for vírgula, apenas passe ao próximo char
				if (v[i].charAt(k) == ',') {
					// avançar contador de chars na coluna da string
					k++;
					continue;
				}
					
				// se for 1, preenche a matriz dado os indices
				if (v[i].charAt(k) == '1') {
					this.grafo.setElement(i, j, 1);
				}
				
				// avançar contador de chars na coluna da string
				k++;
				// avançar contador de elementos na coluna da matriz
				j++;
			}
			// avançar próxima linha da matriz
			i++;
		}
		
		// seta se é grafo ou dígrafo
		grafo.setOrientado(Operacoes.isOrientado(grafo));
	}
	
	/**
	 * @brief Cria o arquivo XML para entrada do prefuse
	 * @param g Grafo de entrada
	 * @param _path Path do arquivo destino do xml gerado
	 */
	public void GraphToXml(Graph g, String _path) throws IOException {
		
		// inicio padrão do schema xml do prefuse
		StringBuffer xml = new StringBuffer(
		"<?xml version=\"1.0\" encoding=\"ISO8859-1\"?>\n<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\">\n" + 
		"\t<graph edgedefault=\""+ ((g.isOrientado()) ? "directed" : "undirected")+ "\">\n" +
    	"\t<key id=\"name\" for=\"node\" attr.name=\"name\" attr.type=\"string\"/>\n" +
    	"\t<key id=\"type\" for=\"node\" attr.name=\"type\" attr.type=\"string\"/>\n" +
		"\t<key id=\"waycolor\" for=\"edge\" attr.name=\"waycolor\" attr.type=\"string\"/>\n"
    	);
		
		// buffer da matriz do grafo
		byte mtx[][] = g.getMatriz();
		
		// para cada nó do grafo representado pela matriz, inserir um node na representação xml do prefuse
		for (int i=0; i < g.getNlc(); i++) {
			xml.append(
			"\n\t\t<node id=\"" + i + "\">" +
			"\n\t\t\t<data key=\"name\">" + (i+1) + "</data>" +
			"\n\t\t\t<data key=\"type\">" + (((i % 2) == 0) ? "normal" : "outro")+"</data>" +
			"\n\t\t</node>");
		}
		
		// varre matriz do grafo, uma vez achado aresta, adiciona um edge na representão xml do prefuse
		for (int i=0; i < g.getNlc(); i++) {
			for (int j=0; j < g.getNlc(); j++) {
				// se tiver 1 ou 2 na matriz, representa uma aresta
				// 1 - aresta normal
				// 2 - aresta para pintar, ou seja, dado algoritmo que necessitam que suas arestas sejam de cor diferente para visualização do resultado.
				if ((mtx[i][j] == 1) || (mtx[i][j] == 2)) {
					xml.append("\n\n\t\t<edge source=\"" + i + "\" target=\"" + j + "\"> " +
							"\n\t\t\t<data key=\"waycolor\">color"+((mtx[i][j] == 1) ? 1 : 2)+"</data>\n\t\t</edge>");
				}
				
			}
		}
		
		// fim do xml, encerrando tags
		xml.append("\n\n\t</graph>\n</graphml>");
		
		// grava em um arquivo de saida xml tudo o texto já inserido no StringBuffer.
		BufferedWriter out = new BufferedWriter(new FileWriter(_path));
        out.write(xml.toString());
        out.flush();
        out.close();
	
	}
}

