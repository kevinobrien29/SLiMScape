package vis.slimfinder.ui;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vis.data.run.Run;
import vis.data.run.SLiMFinderRun;
import vis.exec.WorkQueue;
import vis.root.Variables;
import vis.ui.RunModel;
import vis.ui.RunsTable;
import cytoscape.CyNetwork;
import cytoscape.view.CyNetworkView;

public class SLimFinderRunsTable extends RunsTable {
	Logger log = LoggerFactory.getLogger(SLimFinderRunsTable.class);

	SLimFinderRunsTable tmp = this;

	public SLimFinderRunsTable(CyNetwork network, CyNetworkView networkView,
			Variables variables, WorkQueue workQueue, JPanel resultCards) {
		super(network, networkView, variables, SLiMFinderRun.getTitles(),
				workQueue, resultCards);

		// TODO Auto-generated constructor stub
	}

	@Override
	public void createExtraOptions(JTable source, JPopupMenu popupMewnu,
			final int row, int column) {
		// TODO Auto-generated method stub

		Run run = ((RunModel) tmp.getModel()).getRun(row);
		String id = run.getId();
		final String path = tmp.variables.getRunsDir() + "/slimfinder/" + id;
		if (Desktop.isDesktopSupported()) {
			JMenuItem menuItem = new JMenuItem("Open results folder");

			menuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					
					try {
						File data = new File(path);
						if (data.exists()) {
							Desktop.getDesktop().open(data);
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});

			popupMewnu.add(menuItem);
		}
		else
		{
			log.debug("Desktop is not supported on this platform");
			JMenuItem menuItem = new JMenuItem("Copy path to clipboard");
			menuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Toolkit toolkit = Toolkit.getDefaultToolkit();
					Clipboard clipboard = toolkit.getSystemClipboard();
					StringSelection strSel = new StringSelection(path);
					clipboard.setContents(strSel, null);
				}
			});
			popupMewnu.add(menuItem);
		}

	}

}
