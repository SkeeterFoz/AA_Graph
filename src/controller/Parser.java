package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import model.Graph;

/**
 * @author igor
 *
 */
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
	
		int i=0;

		while (i < nl) {
			int j=0;
			int k=0;
			while (j < nl) {
				if (v[i].charAt(k) == ',') {
					k++;
					continue;
				}
					
				if (v[i].charAt(k) == '1') {
					this.grafo.setElement(i, j, 1);
				}
				k++;
				j++;
			}
			i++;
		}
	}
	
	public void TextToGraph(String _path, Graph g) throws Exception {
	}
	
	/**
	 * Cria o arquivo XML para entrada do prefuse
	 * @param g Grafo de entrada
	 * @param _path Path do arquivo destino do xml gerado
	 */
	public void GraphToXml(Graph g, String _path) throws IOException {
		
		StringBuffer xml = new StringBuffer(
		"<?xml version=\"1.0\" encoding=\"ISO8859-1\"?>\n<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\">\n" + 
		"\t<graph edgedefault=\"directed\">\n" +
    	"\t<key id=\"name\" for=\"node\" attr.name=\"name\" attr.type=\"string\"/>\n" +
    	"\t<key id=\"type\" for=\"node\" attr.name=\"type\" attr.type=\"string\"/>\n" +
		"\t<key id=\"waycolor\" for=\"edge\" attr.name=\"waycolor\" attr.type=\"string\"/>\n"
    	);
		byte mtx[][] = g.getMatriz();
		
		for (int i=0; i < g.getNlc(); i++) {
			xml.append(
			"\n\t\t<node id=\"" + i + "\">" +
			"\n\t\t\t<data key=\"name\">" + (i+1) + "</data>" +
			"\n\t\t\t<data key=\"type\">normal</data>" +
			"\n\t\t</node>");
		}
		
		
		for (int i=0; i < g.getNlc(); i++) {
			for (int j=0; j < g.getNlc(); j++) {
				if ((mtx[i][j] == 1) || (mtx[i][j] == 2)) {
					xml.append("\n\n\t\t<edge source=\"" + i + "\" target=\"" + j + "\"> " +
							"\n\t\t\t<data key=\"waycolor\">color"+((mtx[i][j] == 1) ? 1 : 2)+"</data>\n\t\t</edge>");
				}
				
			}
		}
				
		xml.append("\n\n\t</graph>\n</graphml>");
		
		BufferedWriter out = new BufferedWriter(new FileWriter(_path));
        out.write(xml.toString());
        out.flush();
        out.close();
	
	}
	
//=======
//			int c = 0;
//			int tmp = 0;
//			while (c < nl) {
//				if (v[i].charAt(tmp) == '0') {
//					this.grafo.setElement(i, c, 0);
//					c++;
//				} else if (v[i].charAt(tmp) == '1') {
//					this.grafo.setElement(i, c, 1);
//					c++;
//				}
//				tmp++;
//			}
//			i++;
//		}
//	}	
//>>>>>>> 1c6b36658cd9da8366be5cf5c11ce0897c77ef1e
}

