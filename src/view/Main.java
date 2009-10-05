package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import model.Graph;

import utils.DepthFirstSearch;
import controller.IOFile;
import controller.Operacoes;
import controller.Parser;


public class Main {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Graph grafo;
		
		JFileChooser fc = new JFileChooser(".");
		int returnVal = fc.showOpenDialog(null);
		
		if(returnVal != JFileChooser.APPROVE_OPTION) return; 
		
		try {
			IOFile io = new IOFile(fc.getSelectedFile().getAbsolutePath());
			io.Read();
			
			Parser parser = new Parser(io.getV(), io.getN());
			grafo = parser.getGrafo();
			grafo.printGraph();
			System.out.println(" ");
			
			if (Operacoes.isOrientado(grafo)) {
				JOptionPane.showMessageDialog(null, "O grafo é Orientado", "Tipo de Grafo", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "O grafo não é Orientado", "Tipo de Grafo", JOptionPane.INFORMATION_MESSAGE);
			}
			
			DepthFirstSearch dfs = new DepthFirstSearch(grafo);
			dfs.doListaTermino(true);
			dfs.doListaCiclo(true);
			dfs.run();
			int[] termino = dfs.getTermino();
			ArrayList<Integer> lista = dfs.getListaTermino();
			
			for (int i = 0; i < grafo.getNlc(); i++)
				System.out.println("Vertice " + i + ":\t" + termino[i]);
			
			System.out.println(" ");
			
			for (Integer i: lista) {
				System.out.println("Ordenação Topológica\t" + i);
			}
			
			if (dfs.temCiclo()) {
				System.out.println("\nTem ciclo");
				for (Integer i: dfs.getCiclo()) {
					System.out.println("Vertice: \t" + i);
				}
			}
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
