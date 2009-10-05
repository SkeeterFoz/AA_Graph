package view;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.DragControl;
import prefuse.controls.FocusControl;
import prefuse.controls.NeighborHighlightControl;
import prefuse.controls.PanControl;
import prefuse.controls.WheelZoomControl;
import prefuse.controls.ZoomControl;
import prefuse.controls.ZoomToFitControl;
import prefuse.data.Graph;
import prefuse.data.Tuple;
import prefuse.data.event.TupleSetListener;
import prefuse.data.expression.parser.ExpressionParser;
import prefuse.data.io.GraphMLReader;
import prefuse.data.tuple.TupleSet;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.LabelRenderer;
import prefuse.util.ColorLib;
import prefuse.visual.VisualGraph;
import prefuse.visual.VisualItem;

public class GraphView extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final String graph = "graph";
    private static final String nodes = "graph.nodes";
    private static final String edges = "graph.edges";

    private Visualization m_vis;
    
    public GraphView(Graph g, String label) {
    	super(new BorderLayout());
    	
        // visualização do grafo
        m_vis = new Visualization();
        
        // Renderizadores
        LabelRenderer tr = new LabelRenderer();
        tr.setRoundedCorner(8, 8);
        m_vis.setRendererFactory(new DefaultRendererFactory(tr));

        // setar o grafo dentro da estrutura do framework
        setGraph(g, label);
        
        // efeito de nós selecionados
        TupleSet focusGroup = m_vis.getGroup(Visualization.FOCUS_ITEMS); 
        focusGroup.addTupleSetListener(new TupleSetListener() {
            public void tupleSetChanged(TupleSet ts, Tuple[] add, Tuple[] rem)
            {
                for ( int i=0; i<rem.length; ++i )
                    ((VisualItem)rem[i]).setFixed(false);
                for ( int i=0; i<add.length; ++i ) {
                    ((VisualItem)add[i]).setFixed(false);
                    ((VisualItem)add[i]).setFixed(true);
                }
                if ( ts.getTupleCount() == 0 ) {
                    ts.addTuple(rem[0]);
                    ((VisualItem)rem[0]).setFixed(false);
                }
                m_vis.run("draw");
            }
        });
        

        // Coloração dos vértices e arestas
        
        ColorAction fill = new ColorAction(nodes, 
                VisualItem.FILLCOLOR, ColorLib.rgb(200,200,255));
        fill.add(VisualItem.FIXED, ColorLib.rgb(255,100,100));
        fill.add(VisualItem.HIGHLIGHT, ColorLib.rgb(255,200,125));
        
        ActionList draw = new ActionList();
        draw.add(fill);
        draw.add(new ColorAction(nodes, VisualItem.STROKECOLOR, 0));
        draw.add(new ColorAction(nodes, VisualItem.TEXTCOLOR, ColorLib.rgb(0,0,0)));
        draw.add(new ColorAction(edges, VisualItem.FILLCOLOR, ColorLib.gray(200)));
        
    	ColorAction edgeStrokeColor = new ColorAction(edges, 
    		VisualItem.STROKECOLOR, ColorLib.gray(200));
        
    	edgeStrokeColor.add(ExpressionParser.predicate("waycolor == 'color1'"), ColorLib.rgba(0,0,255,100));
    	edgeStrokeColor.add(ExpressionParser.predicate("waycolor == 'color2'"), ColorLib.rgba(255,0,0,100));
    	edgeStrokeColor.add(ExpressionParser.predicate("waycolor == 'color3'"), ColorLib.rgba(0,255,0,100));
        
    	draw.add(edgeStrokeColor);
    	
        ActionList animate = new ActionList(Activity.INFINITY);
        animate.add(new ForceDirectedLayout(graph));
        animate.add(fill);
        animate.add(new RepaintAction());

        m_vis.putAction("draw", draw);
        m_vis.putAction("layout", animate);

        m_vis.runAfter("draw", "layout");
        
        
        // display principal
        Display display = new Display(m_vis);
        display.setSize(700,700);
        display.pan(350, 350);
        display.setForeground(Color.GRAY);
        display.setBackground(Color.WHITE);
        
        // main display controles
        display.addControlListener(new FocusControl(1));
        display.addControlListener(new DragControl());
        display.addControlListener(new PanControl());
        display.addControlListener(new ZoomControl());
        display.addControlListener(new WheelZoomControl());
        display.addControlListener(new ZoomToFitControl());
        display.addControlListener(new NeighborHighlightControl());

    
        display.setForeground(Color.GRAY);
        display.setBackground(Color.WHITE);
        
        m_vis.run("draw");        
        add(display);
    }
    
    public void setGraph(Graph g, String label) {
        // update labeling
        DefaultRendererFactory drf = (DefaultRendererFactory)
                                                m_vis.getRendererFactory();
        ((LabelRenderer)drf.getDefaultRenderer()).setTextField(label);
        
        // update graph
        m_vis.removeGroup(graph);
        VisualGraph vg = m_vis.addGraph(graph, g);
        m_vis.setValue(edges, null, VisualItem.INTERACTIVE, Boolean.FALSE);
        VisualItem f = (VisualItem)vg.getNode(0);
        m_vis.getGroup(Visualization.FOCUS_ITEMS).setTuple(f);
        f.setFixed(false);
    }
    
    public static JPanel demo() {
        return demo((String)null, "label");
    }
    
    public static JPanel demo(String datafile, String label) {
        Graph g = null;
        if ( datafile == null ) {
            datafile = "config.xml";
            label = "name";
        } else {
            try {
                g = new GraphMLReader().readGraph(datafile);
            } catch ( Exception e ) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return demo(g, label);
    }
    
	public static JPanel demo(Graph g, String label) {
		final GraphView view = new GraphView(g, label);

		JPanel panel = new JPanel();
		view.m_vis.run("layout");
		panel = view;

		return panel;
	}
    
}