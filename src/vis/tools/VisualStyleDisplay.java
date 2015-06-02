package vis.tools;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import cytoscape.CyNetwork;
import cytoscape.Cytoscape;
import cytoscape.view.CyNetworkView;
import cytoscape.visual.CalculatorCatalog;
import cytoscape.visual.VisualMappingManager;
import cytoscape.visual.VisualStyle;

public class VisualStyleDisplay extends JPanel {

	/**
	 * Create the panel.
	 */
	public VisualStyleDisplay(final CyNetwork network, final CyNetworkView networkView) {
		setLayout(null);

		JButton btnApplyVisualStyle = new JButton("Apply Visual Style");
		btnApplyVisualStyle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				// get the VisualMappingManager and CalculatorCatalog
				VisualMappingManager manager = Cytoscape
						.getVisualMappingManager();
				CalculatorCatalog catalog = manager.getCalculatorCatalog();

				// check to see if a visual style with this name already exists
				VisualStyle vs = catalog.getVisualStyle("example");
				if (vs == null) {
					// if not, create it and add it to the catalog
					vs = VisualStyleBuilder.createPrimaryVisualStyle(network);
					catalog.addVisualStyle(vs);
				}

				networkView.setVisualStyle(vs.getName()); // not strictly
															// necessary

				// actually apply the visual style
				manager.setVisualStyle(vs);
				networkView.redrawGraph(true, true);
			}
		});
		btnApplyVisualStyle.setBounds(12, 12, 160, 25);
		add(btnApplyVisualStyle);

	}
}
