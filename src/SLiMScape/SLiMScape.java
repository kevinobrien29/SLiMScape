/**
 * 
 */
package SLiMScape;

import vis.data.Constants;
import vis.launch.LaunchSlimDomain;
import cytoscape.Cytoscape;
import cytoscape.plugin.CytoscapePlugin;
import cytoscape.util.CytoscapeAction;

public class SLiMScape extends CytoscapePlugin {
	
	public SLiMScape(){
		init();
	}
	
	protected void init(){
	
		// Create the Action, add the action to Cytoscape menu the action object 
		// takes care of menu location
		LaunchSlimDomain customButton = new LaunchSlimDomain("Launch " + Constants.pluginName);
		Cytoscape.getDesktop().getCyMenus().addCytoscapeAction((CytoscapeAction) customButton);
	}

}