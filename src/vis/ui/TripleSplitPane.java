package vis.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import vis.data.attribute.Attribute;
import vis.data.run.Run;
import vis.slimfinder.ui.SLiMFinderAttributeTable;

public class TripleSplitPane extends JPanel  implements ItemListener{
	JSplitPane jSplitPane1 = new JSplitPane();
	JSplitPane jSplitPane2 = new JSplitPane();
	JPanel jPanel1 = null;
	RunsTable runsTable = null;
	JPanel resultCards = null;
	
	private String runDir;
	JComboBox comboBox = null;
	int numberOfCards = 0;

	public TripleSplitPane() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	}
	
	public void setPanes(JPanel jPanel1, RunsTable runsTable, JPanel resultCards) {
		jPanel1.setPreferredSize(new Dimension(new Dimension(100, 100)));
		//runsTable.setPreferredSize(new Dimension(new Dimension(100, 500)));
		resultCards.setPreferredSize(new Dimension(new Dimension(100, 100)));
		
		
		this.jPanel1 = jPanel1;
		this.runsTable = runsTable;
		this.resultCards = resultCards;
		
		
		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
		int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
		
		
		JScrollPane resultScrollBar = new JScrollPane(v, h);
		resultScrollBar.setViewportView(resultCards);
		
		JScrollPane runsScrollBar = new JScrollPane(v, h);
		runsScrollBar.setPreferredSize(new Dimension(100,runsTable.getRowHeight()*10));
		runsScrollBar.setMinimumSize(new Dimension(100,runsTable.getRowHeight()*10));
		runsScrollBar.setViewportView(new BorderLayoutWrapper(runsTable));
		
		jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jSplitPane1.getTopComponent().setMaximumSize(jSplitPane1.getSize());
		jSplitPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		add(jSplitPane1, BorderLayout.CENTER);
		jSplitPane1.add(jPanel1, JSplitPane.TOP);
		jSplitPane1.add(jSplitPane2, JSplitPane.BOTTOM);
		jSplitPane2.add(runsScrollBar, JSplitPane.TOP);
		jSplitPane2.add(resultCards, JSplitPane.BOTTOM);
	}

	void this_windowOpened(WindowEvent e) {
		this.jSplitPane1.setDividerLocation(0.3);
		this.jSplitPane2.setDividerLocation(0.7);
	}
	
	public void initialiseRun(Run run)
	{
		this.runsTable.addRow(run);
	}

	public void startRun(String id, HashSet<String> highlightedNodes) {
		HashMap<String, String> mappings = new HashMap<String, String>();
		mappings.put("Status_", "Running");
		mappings.put("Percentage", "0");
		this.runsTable.startRun(id);
		//resultCards.add(log, id);
		//CardLayout cl = (CardLayout) (resultCards.getLayout());
		//if (numberOfCards < 1) {
		//	cl.show(resultCards, id);
		//}
		//numberOfCards++;
	}

	public void markFailedResult(String id, String logPath, String stdout, String stderr, String message) {
		resultCards.add(new NoResultsPanel(logPath, stdout, stderr, message) , id);
		this.runsTable.markFailedResult(id, message);
		CardLayout cl = (CardLayout) (resultCards.getLayout());
		//cl.show(resultCards, id);
		
	}

	public void addResults(String id, AttributeTable slimPanel, JTable resultsDisplay, int numberOfInputNodes, int numberOfSLiMS, Double minimumSig, ArrayList<Attribute> childAttributes) {
		
		JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportView(resultsDisplay);
		resultCards.add(scrollPane, id);
		this.runsTable.addResults(id, slimPanel,numberOfInputNodes, numberOfSLiMS, minimumSig, childAttributes);
		CardLayout cl = (CardLayout) (resultCards.getLayout());
		//cl.show(resultCards, id);
		
	}

	public void addResults(String id, TableDisplay resultsDisplay) {
		resultCards.add(resultsDisplay, id);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub

	}
}
