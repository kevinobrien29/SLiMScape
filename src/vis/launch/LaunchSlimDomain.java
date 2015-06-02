/**
 * 
 */
package vis.launch;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import vis.data.Constants;
import vis.root.SetupWizardJFrame;
import cytoscape.CyNetwork;
import cytoscape.Cytoscape;
import cytoscape.util.CytoscapeAction;

@SuppressWarnings("serial")
public class LaunchSlimDomain extends CytoscapeAction {

	private boolean opened = false;
	private SetupWizardJFrame frame = null;

	/**
	 * @param name
	 */
	public LaunchSlimDomain(String name) {
		super(name);
		setPreferredMenu("Plugins");
	}

	public void setRunning(boolean value) {
		this.opened = value;
	}

	// public static final String vsName = "Example Visual Style";

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// CytoPanelImp ctrlPanel = (CytoPanelImp) Cytoscape.getDesktop()
		// .getCytoPanel(SwingConstants.WEST);

		if (!this.opened) {
			launch();
		} else {

			int reply = JOptionPane.showConfirmDialog(Cytoscape.getDesktop(),
					"This will delete your current " + Constants.pluginName
							+ " session. Are you sure you wish to continue?",
					"Continue?", JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {
				frame.reset();
				frame.dispose();
				launch();
			}
		}
	}

	public void launch() {
		ArrayList<String> networkNames = new ArrayList<String>();
		for (CyNetwork network : Cytoscape.getNetworkSet()) {
			networkNames.add(network.getTitle());
		}
		if (networkNames.size() > 0) {
			try {
				frame = new SetupWizardJFrame(this);
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.opened = true;
		} else {
			JOptionPane.showMessageDialog(Cytoscape.getDesktop(),
					"Please import a network before launching the plugin!");
		}
	}
}
