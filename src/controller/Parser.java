package controller;

import model.Graph;

public class Parser {

	private byte[][] matriz;
	
	/**
	 * Realiza o parser do vetor de strings passado
	 * @param v Vetor com as strings
	 * @param nl Numero de linhas do vetor
	 */
	public Parser(String[] v, int nl) {
		this.matriz = new byte[nl][nl];
		
		int i = 0;
		while (i < nl) {
			int c = 0;
			while (c < nl) {
				if (v[i].charAt(c) == '1') {
					this.matriz[i][c] = 1;
				}
			}
		}
	}
	
	public void TextToGraph(String _path, Graph g) throws Exception {
	}
	
//	public void GraphToXml(Graph g, ArrayList<Edge> _pathlist, int _metrica, String _path) throws IOException {
//		
//		StringBuffer xml = new StringBuffer(
//		"<?xml version=\"1.0\" encoding=\"ISO8859-1\"?>\n<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\">\n" + 
//		"\t<graph edgedefault=\"undirected\">\n" +
//    	"\t<key id=\"name\" for=\"node\" attr.name=\"name\" attr.type=\"string\"/>\n" +
//    	"\t<key id=\"state\" for=\"node\" attr.name=\"state\" attr.type=\"string\"/>\n" +
//    	"\t<key id=\"sa\" for=\"node\" attr.name=\"sa\" attr.type=\"string\"/>\n" +
//    	"\t<key id=\"weight\" for=\"edge\" attr.name=\"weight\" attr.type=\"string\"/>\n" +
//    	"\t<key id=\"waycolor\" for=\"edge\" attr.name=\"waycolor\" attr.type=\"string\"/>\n"
//    	);
//		
//		for (int i=0; i < g.getNodelist().size(); i++) {
//			
//			xml.append(
//			"\n\t\t<node id=\"" + i + "\">" +
//			"\n\t\t\t<data key=\"name\">" + g.getNodelist().get(i).getName() + "</data>" + 
//            "\n\t\t\t<data key=\"state\">" + ((g.getNodelist().get(i).isState()) ? "true" : "false") + "</data>" + 
//            "\n\t\t\t<data key=\"sa\">" + g.getNodelist().get(i).getSa() + "</data>" + 
//            "\n\t\t</node>");
//		}
//		
//		for (int i=0; i < g.getEdgelist().size(); i++) {
//			xml.append(
//			"\n\n\t\t<edge source=\"" + g.getEdgelist().get(i).getSrc().getId() + "\" target=\"" + g.getEdgelist().get(i).getDst().getId() + "\">" +
//			"\n\t\t\t<data key=\"weight\">");
//			System.out.println("metrica: " + _metrica);
//			switch (_metrica) {
//			case 0: xml.append( ((g.getEdgelist().get(i).isEqualsa()) ? g.getEdgelist().get(i).getDist() : g.getEdgelist().get(i).getUserdist())); break;
//			case 1: xml.append( ((g.getEdgelist().get(i).isEqualsa()) ? g.getEdgelist().get(i).getRettime() : g.getEdgelist().get(i).getUserrettime())); break;
//			case 2: xml.append( ((g.getEdgelist().get(i).isEqualsa()) ? g.getEdgelist().get(i).getHopcount() : g.getEdgelist().get(i).getUserhopcount())); break;
//			case 3: xml.append( ((g.getEdgelist().get(i).isEqualsa()) ? g.getEdgelist().get(i).getBandwidth() : g.getEdgelist().get(i).getUserbandwidth())); break;
//			}
//			
//			xml.append("</data>");
//			xml.append("\n\t\t\t<data key=\"waycolor\">");
//			if (_pathlist != null) {
//				if (ConstainsPath(_pathlist, g.getEdgelist().get(i).getSrc().getId(), g.getEdgelist().get(i).getDst().getId())) {
//					xml.append("color3</data>\n\n\t</edge>");
//					continue;
//				}
//			}
//			
//			xml.append((((g.getEdgelist().get(i).getSrc().isState()) && (g.getEdgelist().get(i).getDst().isState())) ? "color1" : "color2") + "</data>");
//
//			xml.append("\n\t\t</edge>");
//		}
//		
//		xml.append("\n\n\t</graph>\n</graphml>");
//		
//		BufferedWriter out = new BufferedWriter(new FileWriter(_path));
//        out.write(xml.toString());
//        out.flush();
//        out.close();
//	
//	}
//
//	private boolean ConstainsPath(ArrayList<Edge> path, long src, long dst)
//	{
//		for (int i=0; i < path.size(); i++)
//			if ((path.get(i).getSrc().getId() == src) && (path.get(i).getDst().getId() == dst))
//				return true;
//		return false;
//	}
		
}

