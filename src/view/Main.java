package view;

import java.io.IOException;

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

						
				
			groupComposite = new Composite(shell, SWT.NONE);
			//groupComposite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
			groupComposite.setLayout(new FillLayout());
			
			Composite fakeComposite = new Composite(groupComposite, SWT.BEGINNING);
			//groupComposite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
			fakeComposite.setLayout(new RowLayout(SWT.BEGINNING));
			
			Group groupGrafo = new Group(fakeComposite, SWT.BOTTOM);
			groupGrafo.setText("        Algoritmos Grafo       ");
			groupGrafo.setLayout(new RowLayout(SWT.VERTICAL));
				btnComponente = new Button(groupGrafo, SWT.NONE);
				btnComponente.setText("Componentes Conexas");
				btnComponente.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						
					}
				});
				
				btnCiclo = new Button(groupGrafo, SWT.NONE);
				btnCiclo.setText("Ciclo");
				btnCiclo.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						byte[][] mtx;
						mtx = graph.getMatriz();
						mtx[4][3] += 1;
						mtx[3][5] += 1;
						mtx[5][4] += 1;
						
						replot(mtx);
						
					}
				});
				
				btnVcorte = new Button(groupGrafo, SWT.NONE);
				btnVcorte.setText("Vértice de corte");
				btnVcorte.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {

					}
				});
				
				btnAcorte = new Button(groupGrafo, SWT.NONE);
				btnAcorte.setText("Aresta de corte");
				btnAcorte.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						
					}
				});
				
				btnBipartido = new Button(groupGrafo, SWT.NONE);
				btnBipartido.setText("Bipartição");
				btnBipartido.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						
					}
				});
				
			Group groupDigrafo = new Group(fakeComposite, SWT.SHADOW_IN);
			groupDigrafo.setText("        Algoritmos Dígrafo       ");
			groupDigrafo.setLayout(new RowLayout(SWT.VERTICAL));
				
				btnFecho = new Button(groupDigrafo, SWT.NONE);
				btnFecho.setText("Fecho Transitivo");
				btnFecho.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						replot(Operacoes.FechoTransitivo(graph).getMatriz());
					}
				});
				
				btnTopologica = new Button(groupDigrafo, SWT.NONE);
				btnTopologica.setText("Ordenação Topológica");
				btnTopologica.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {

					}
				});
				
				btnComponenteF = new Button(groupDigrafo, SWT.NONE);
				btnComponenteF.setText("Componentes Fortemente Conexas");
				btnComponenteF.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {

					}
				});

				
			this.btnAcorte.setEnabled(false);
			this.btnVcorte.setEnabled(false);
			this.btnBipartido.setEnabled(false);
			this.btnCiclo.setEnabled(false);
			this.btnComponente.setEnabled(false);
			this.btnFecho.setEnabled(false);
			this.btnTopologica.setEnabled(false);
			this.btnComponenteF.setEnabled(false);
				
			graphComposite = new Composite(shell, SWT.EMBEDDED);
			//graphComposite.setLayout(new GridLayout());
			graphComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
			//graphComposite.setSize(500, 350);

			graphFrame = SWT_AWT.new_Frame(graphComposite);
			
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
	
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
	
	private void openFile()
	{

		try {
			FileDialog dlg = new FileDialog(shell, SWT.OPEN);
			dlg.setFilterNames(new String[] {"txt", "Todos os arquivos (*.*)" });
			dlg.setFilterExtensions(new String[] {"*.txt", "*.*" });
			dlg.setText("Abrir Arquivo...");
			fileName = dlg.open();
			if (fileName != null) {

				String infile = "config.xml";
				String label = "name";
				
				io = new IOFile(fileName);
				io.Read();
				parser = new Parser(io.getV(), io.getN());
				
				graph = parser.getGrafo();
				if (graph.isOrientado())
					desativaGrafo();
				else
					desativaDigrafo();
				
				parser.GraphToXml(graph, infile);
				

				if (panel != null){
					graphFrame.remove(panel);
					panel = null;
				}
				
				panel = new java.awt.Panel(new java.awt.BorderLayout());
			    //panel.add(GraphViewEdgeDecoratorV2.demo(infile, "name", graphComposite.getSize().x, graphComposite.getSize().y));
			    panel.add(GraphView.demo(infile, label));
			    panel.setVisible(true);
			    graphFrame.add(panel);
			    graphFrame.setVisible(true);
 			    
			} else
				System.out.println("Cancelar");
		} catch (Exception e){
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR);
			messageBox.setMessage("Erro ao abrir o arquivo" );
		}
	}
	
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

		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setText("Trabalho de Análise de Algoritmos - Grafos");
		shell.setMaximized(true);
		Main inst = new Main(shell, SWT.NULL);
		
		GridLayout layout = new GridLayout(2,false);
		
		shell.setLayout(layout);
		shell.layout();
		shell.open();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

}
