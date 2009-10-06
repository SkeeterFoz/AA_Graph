package view;

import java.io.IOException;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.Graph;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import controller.IOFile;
import controller.Operacoes;
import controller.Parser;

/**
 * @author igor
 *
 */
public class Main {

	private Shell shell;
	private Menu mainMenu;
	private MenuItem fileMenuItem;
	private Menu fileMenu;
	private MenuItem openFileMenuItem;
	private MenuItem exitMenuItem;
	private MenuItem helpMenuItem;
	private Menu helpMenu;
	private MenuItem contentsMenuItem;
	private MenuItem aboutMenuItem;
	private Composite groupComposite;
	private Composite graphComposite;
	
	private String fileName;
	private Parser parser;
	private IOFile io;
	private Graph graph;
	
	private java.awt.Frame graphFrame;
	private java.awt.Panel panel;
	
	private Button btnCiclo;
	private Button btnComponente;
	private Button btnVcorte;
	private Button btnAcorte;
	private Button btnBipartido;
	private Button btnFecho;
	private Button btnTopologica;
	private Button btnComponenteF;
	
	public Main(Composite parent, int style) {
		this.shell = parent.getShell();
		initGUI();
	}
	
	
	private void initGUI() {
		try {
		
			// Menu bar e itens
			mainMenu = new Menu(shell, SWT.BAR);
			shell.setMenuBar(mainMenu);
				fileMenuItem = new MenuItem(mainMenu, SWT.CASCADE);
				fileMenuItem.setText("Arquivo");
				fileMenu = new Menu(fileMenuItem);
					openFileMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
					openFileMenuItem.setText("Abrir");
					openFileMenuItem.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							openFile();
						}
					});

					exitMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
					exitMenuItem.setText("Sair");
					exitMenuItem.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							shell.dispose();
						}
					});

				fileMenuItem.setMenu(fileMenu);	
				
				helpMenuItem = new MenuItem(mainMenu, SWT.CASCADE);
				helpMenuItem.setText("Ajuda");
					helpMenu = new Menu(helpMenuItem);
						contentsMenuItem = new MenuItem(helpMenu, SWT.CASCADE);
						contentsMenuItem.setText("Conteúdo");
						contentsMenuItem.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								final Shell dialog = new Shell(shell);
							    dialog.setText("Conteúdo");
							    dialog.setSize(600, 150);
							    dialog.setLayout(new RowLayout(SWT.VERTICAL));
							    
							        new StyledText(dialog, SWT.PUSH).setText("Este trabalho foi desenvolvido p/ a disciplina de AA e abrange o escopo da teoria sobre grafos, \n" + 
							    		"contendo assim a implementação dos algoritmos de verificação de componentes conexas, \n" +
							    		"verificação da existência de ciclos, verificação de vértices e arestas de corte e \n" +
							    		"análise de bipartição para os grafos simples. Para dígrafos, os algoritmos implementados \n" +
							    		"verificação de componentes fortemente conexas, fecho transitivo e ordenação topológica.\n");
							    Button btnOk = new Button(dialog, SWT.PUSH);
								btnOk.setText("Ok");
								btnOk.addListener(SWT.Selection, new Listener() {
									public void handleEvent(Event event) {
										dialog.dispose();
									}
								});
								dialog.open();
							}
						});
						
						aboutMenuItem = new MenuItem(helpMenu, SWT.CASCADE);
						aboutMenuItem.setText("Sobre");
						aboutMenuItem.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								final Shell dialog = new Shell(shell);
							    dialog.setText("Sobre");
							    dialog.setSize(350, 150);
							    dialog.setLayout(new RowLayout(SWT.VERTICAL));
							    new StyledText(dialog, SWT.PUSH).setText("Implementação da Teoria de Grafos 1.0 \n\n" + 
							    		"Alunos: \n\t Igor Henrique da Cruz (spym4n@gmail.com)\n\t Newtom Muchael (skeeterfoz@gmail.com)\n");
							    Button btnOk = new Button(dialog, SWT.PUSH);
								btnOk.setText("Ok");
								btnOk.addListener(SWT.Selection, new Listener() {
									public void handleEvent(Event event) {
										dialog.dispose();
									}
								});
							    dialog.open();
							}
						});
						
				helpMenuItem.setMenu(helpMenu);

						
			// Widget Composite principal da shell da swt	
			groupComposite = new Composite(shell, SWT.NONE);
			// Setando o layout desse composite
			groupComposite.setLayout(new FillLayout());
			
			// Widget Composite lado esquerdo, botões dos algoritmos
			Composite fakeComposite = new Composite(groupComposite, SWT.BEGINNING);
			// Setando o layout desse composite
			fakeComposite.setLayout(new RowLayout(SWT.BEGINNING));
			
			// Widget Group dos algoritmos para grafos
			Group groupGrafo = new Group(fakeComposite, SWT.BOTTOM);
			groupGrafo.setText("        Algoritmos Grafo       ");
			// Setando o layout desse composite
			groupGrafo.setLayout(new RowLayout(SWT.VERTICAL));
				
				btnComponente = new Button(groupGrafo, SWT.NONE);
				btnComponente.setText("Componentes Conexas");
				btnComponente.addListener(SWT.Selection, new Listener() {
					
					// evento do botão Componentes Conexas
					public void handleEvent(Event event) {
						ArrayList<ArrayList<Integer>> compenentes = Operacoes.ComponentesConexas(graph);
						for (ArrayList<Integer> i : compenentes)
							System.out.println(i.toString());
					}
				});
				
				btnCiclo = new Button(groupGrafo, SWT.NONE);
				btnCiclo.setText("Ciclo");
				btnCiclo.addListener(SWT.Selection, new Listener() {
					
					// evento do botão Ciclo
					public void handleEvent(Event event) {
						
					}
				});
				
				btnVcorte = new Button(groupGrafo, SWT.NONE);
				btnVcorte.setText("Vértice de corte");
				btnVcorte.addListener(SWT.Selection, new Listener() {
					
					// evento do botão Vértice de Corte
					public void handleEvent(Event event) {
						replot(Operacoes.VerticesDeCorte(graph).getMatriz());
					}
				});
				
				btnAcorte = new Button(groupGrafo, SWT.NONE);
				btnAcorte.setText("Aresta de corte");
				btnAcorte.addListener(SWT.Selection, new Listener() {
					
					// evento do botão Aresta de Corte
					public void handleEvent(Event event) {
						replot(Operacoes.ArestasDeCorte(graph).getMatriz());	
					}
				});
				
				btnBipartido = new Button(groupGrafo, SWT.NONE);
				btnBipartido.setText("Bipartição");
				btnBipartido.addListener(SWT.Selection, new Listener() {
					
					// evento do botão Bipartição
					public void handleEvent(Event event) {
						ArrayList<ArrayList<Integer>> compenentes = Operacoes.Biparticao(graph);
						if (compenentes != null)
							for (ArrayList<Integer> i : compenentes)
								System.out.println(i.toString());
					}
				});
			
			// Widget Group para os algoritmos de dígrafo
			Group groupDigrafo = new Group(fakeComposite, SWT.SHADOW_IN);
			groupDigrafo.setText("        Algoritmos Dígrafo       ");
			
			// setando o layout do widget
			groupDigrafo.setLayout(new RowLayout(SWT.VERTICAL));
				
				btnFecho = new Button(groupDigrafo, SWT.NONE);
				btnFecho.setText("Fecho Transitivo");
				btnFecho.addListener(SWT.Selection, new Listener() {
					
					// evento do botão Fecho Transitivo
					public void handleEvent(Event event) {
						replot(Operacoes.FechoTransitivo(graph).getMatriz());
					}
				});
				
				btnTopologica = new Button(groupDigrafo, SWT.NONE);
				btnTopologica.setText("Ordenação Topológica");
				btnTopologica.addListener(SWT.Selection, new Listener() {
					
					// evento do botão Ordenação Topológica
					public void handleEvent(Event event) {
						StringBuffer sb = new StringBuffer("Ordenação topológica é dada pelos seguintes vértices: \n");
						for (int i=0; i < Operacoes.OrdenacaoTopologica(graph).size(); i++)
							sb.append(Operacoes.OrdenacaoTopologica(graph).get(i)+1 + " - ");
						JOptionPane.showMessageDialog(null, sb);
					}
				});
				
				btnComponenteF = new Button(groupDigrafo, SWT.NONE);
				btnComponenteF.setText("Componentes Fortemente Conexas");
				btnComponenteF.addListener(SWT.Selection, new Listener() {
					
					// evento do botão Componentes Fortemente Conexas
					public void handleEvent(Event event) {
						replot(Operacoes.ComponentesFortementeConexas(graph).getMatriz());
					}
				});

			// Setando todos os widgets de botões para falso para não poder 
			// usa-los quando não carregado nenhum grafo	
			this.btnAcorte.setEnabled(false);
			this.btnVcorte.setEnabled(false);
			this.btnBipartido.setEnabled(false);
			this.btnCiclo.setEnabled(false);
			this.btnComponente.setEnabled(false);
			this.btnFecho.setEnabled(false);
			this.btnTopologica.setEnabled(false);
			this.btnComponenteF.setEnabled(false);
			
			// Widget Composite para a visualização do grafo gerado pelo prefuse
			graphComposite = new Composite(shell, SWT.EMBEDDED);
			graphComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
			graphFrame = SWT_AWT.new_Frame(graphComposite);
			
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * @brief Replota um grafo na tela dada uma nova matriz do grafo
	 * @param mtx Matriz do novo grafo a ser plotado
	 */
	private void replot(byte[][] mtx)
	{
		Graph graphtmp = new Graph(mtx.length);
		graphtmp.setMatriz(mtx);
		graphtmp.setNlc(graph.getNlc());
		graphtmp.setOrientado(Operacoes.isOrientado(graphtmp));
				
		try {
			parser.GraphToXml(graphtmp, "resultado.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		graphFrame.remove(panel);
		
		panel = new java.awt.Panel(new java.awt.BorderLayout());						
	    panel.add(GraphView.demo("resultado.xml", "name"));
	    
	   	    
	    graphFrame.add(panel);
	    graphFrame.repaint();
	    graphFrame.setVisible(true);
		
	}
	
	/**
	 * @brief Método do evento abrir arquivo que chama os controladores para plotar o grafo do arquivo de entrada
	 */
	private void openFile()
	{

		try {
			// Dialog para escolha do arquivo de entrada
			FileDialog dlg = new FileDialog(shell, SWT.OPEN);
			dlg.setFilterNames(new String[] {"txt", "Todos os arquivos (*.*)" });
			dlg.setFilterExtensions(new String[] {"*.txt", "*.*" });
			dlg.setText("Abrir Arquivo...");
			fileName = dlg.open();
			if (fileName != null) {

				String infile = "config.xml";
				String label = "name";
				
				// Ler o arquivo
				io = new IOFile(fileName);
				io.Read();
				
				// Realizar o parser para a estrutura de grafo
				parser = new Parser(io.getV(), io.getN());				
				graph = parser.getGrafo();
				
				// Ativar apenas botões necessários para o tipo de grafo
				if (graph.isOrientado())
					desativaGrafo();
				else
					desativaDigrafo();
				
				// Criar um arquivo XML para a visualização do prefuse
				parser.GraphToXml(graph, infile);
				
				// Caso já tenha um grafo plotado, deletar
				if (panel != null){
					graphFrame.remove(panel);
					panel = null;
				}
				
				panel = new java.awt.Panel(new java.awt.BorderLayout());
			    
				// chamar classe de montagem da visualização do grafo
				panel.add(GraphView.demo(infile, label));
			    panel.setVisible(true);
			    
			    // adicionar e mostrar no painel o panel gerado pela GraphView
			    graphFrame.add(panel);
			    graphFrame.setVisible(true);
 			    
			} else
				System.out.println("Cancelar");
		} catch (Exception e){
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR);
			messageBox.setMessage("Erro ao abrir o arquivo" );
		}
	}
	
	/**
	 * @brief Desativa elementos gráficos (botões) que não devem estar ativos caso seja um dígrafo
	 */
	private void desativaGrafo()
	{
		this.btnAcorte.setEnabled(false);
		this.btnVcorte.setEnabled(false);
		this.btnBipartido.setEnabled(false);
		this.btnCiclo.setEnabled(false);
		this.btnComponente.setEnabled(false);
		this.btnFecho.setEnabled(true);
		this.btnTopologica.setEnabled(true);
		this.btnComponenteF.setEnabled(true);
	}
	
	/**
	 * @brief Desativa elementos gráficos (botões) que não devem estar ativos caso seja um grafo
	 */
	private void desativaDigrafo()
	{
		this.btnAcorte.setEnabled(true);
		this.btnVcorte.setEnabled(true);
		this.btnBipartido.setEnabled(true);
		this.btnCiclo.setEnabled(true);
		this.btnComponente.setEnabled(true);
		this.btnFecho.setEnabled(false);
		this.btnTopologica.setEnabled(false);
		this.btnComponenteF.setEnabled(false);
	}

	/**
	 * @brief Método Principal que chama a tela de ínicio.
	 * @param args
	 */
	public static void main(String[] args) {

		// Criação de elementos da SWT
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setText("Trabalho de Análise de Algoritmos - Grafos");
		shell.setMaximized(true);
		Main inst = new Main(shell, SWT.NULL);
		
		GridLayout layout = new GridLayout(2,false);
		
		shell.setLayout(layout);
		shell.layout();
		
		// Abre a shell SWT
		shell.open();
		
		// Verifica a disponibilidade da janela, até quando fecha-la
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

}
