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

	/**
	 * Realiza o parser do vetor de strings passado
	 * @param v Vetor com as strings
	 * @param nl Numero de linhas do vetor
	 * @param g Grafo de saida gerado pelo parser
	 */
	public Parser(String[] v, int nl, Graph g) {
	
		byte matriz[][]; 
		matriz = new byte[nl][nl];
		
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
					matriz[i][j] = 1;
				}
				k++;
				j++;
			}
			i++;
		}
	
		g.setMatriz(matriz);
		g.setNlc(nl);
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
	
}

