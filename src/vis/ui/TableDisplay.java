package vis.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableRowSorter;

import vis.data.Constants;
import vis.data.attribute.Attribute;
import vis.data.attribute.AttributeModel;

public abstract class TableDisplay extends JPanel implements ChangeListener,
		ActionListener, MouseListener, HierarchyListener {
	double divider = Constants.divider;

	JPanel sliderPanel = null;
	private JTable table;
	private TableRowSorter<AttributeModel> sorter;
	AttributeModel model = null;

	String sliderAttribute = null;
	HashMap<String, HashMap<String, Double>> sliderAttributeRanges = null;
	ColumnSizeMaker columnSizeMaker = null;
	int width = 700;
	SliderPane sliderPane = null;
	JSlider slider = null;

	public TableDisplay(String[] modelNames, String sliderAttribute,
			HashMap<String, HashMap<String, Double>> sliderAttributeRanges) {
		this.sliderAttribute = sliderAttribute;
		this.sliderAttributeRanges = sliderAttributeRanges;

		model = new AttributeModel(modelNames);
		modelNames[0] = "";
		sorter = new TableRowSorter<AttributeModel>(model);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		if (sliderAttribute != null) {

			table = new JTable(model);
			table.addMouseListener(this);
			table.setRowSorter(sorter);
			table.setPreferredScrollableViewportSize(new Dimension(1200, width));
			table.setFillsViewportHeight(true);

			// For the purposes of this example, better to have a single
			// selection.
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

			model.addTableModelListener(new TableModelListener() {
				public void tableChanged(TableModelEvent e) {
					if (isShowing()) {
						new TablePacker(TablePacker.VISIBLE_ROWS, true)
								.pack(table);
					}
				}
			});

			JScrollPane scrollPane = new JScrollPane(
					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setViewportView(table);

			// Add the scroll pane to this panel.
			add(scrollPane);

			sliderPanel = new JPanel();
			Border compound = BorderFactory.createCompoundBorder(
					new EmptyBorder(0, 0, 10, 0), new TitledBorder("Filters:"));
			sliderPanel.setBorder(compound);
			sliderPanel.setLayout(new BoxLayout(sliderPanel,
					BoxLayout.PAGE_AXIS));

			int counter = 0;
			if (this.sliderAttributeRanges == null) {
				addJSlider(sliderPanel, counter, sliderAttribute, null);
			} else {
				addJSlider(sliderPanel, counter, sliderAttribute,
						this.sliderAttributeRanges.get(sliderAttribute));
			}
			counter++;
			sliderPanel.setPreferredSize(new Dimension(width, SliderPane
					.getCustomSize().height + 100));
			sliderPanel.setVisible(true);
			sliderPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
			add(sliderPanel);
		} else {
			table = new JTable(model);
			table.addMouseListener(this);
			table.setRowSorter(sorter);
			table.setPreferredScrollableViewportSize(new Dimension(1200, width));
			table.setFillsViewportHeight(true);

			// For the purposes of this example, better to have a single
			// selection.
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

			model.addTableModelListener(new TableModelListener() {
				public void tableChanged(TableModelEvent e) {
					if (isShowing()) {
						new TablePacker(TablePacker.VISIBLE_ROWS, true)
								.pack(table);
					}
				}
			});

			JScrollPane scrollPane = new JScrollPane(
					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollPane.setViewportView(table);

			// Add the scroll pane to this panel.

			add(scrollPane);
		}

		columnSizeMaker = new ColumnSizeMaker(table, modelNames, 0);
		columnSizeMaker.initialiseColLengthTracker();
		columnSizeMaker.revaluateColSizes();
		columnSizeMaker.applyColLengths();

		DoubleRenderer _renderer = new DoubleRenderer();
		table.setDefaultRenderer(Double.class, _renderer);
	}
	
	

	public JTable getTable() {
		return table;
	}

	public void addJSlider(JPanel sliderPanel, int counter,
			String arributeName, HashMap<String, Double> sliderAttributeRanges) {

		Double min = null;
		Double max = null;
		if (sliderAttributeRanges != null) {
			min = sliderAttributeRanges.get("min");
			max = sliderAttributeRanges.get("max");
		} else {
			min = 0.0;
			max = 1.0;
		}

		sliderPane = new SliderPane(arributeName, min, max, this,
				this);
		sliderPane.setVisible(true);

		slider = sliderPane.getSlider();
		slider.addChangeListener(this);
		slider.addChangeListener(model);
		
		sliderPane.setAlignmentX(Component.RIGHT_ALIGNMENT);
		sliderPanel.add(sliderPane);
	}

	public void replaceModelData(final ArrayList<Attribute> slims) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				model.replaceModelData(slims);
				columnSizeMaker.revaluateColSizes();
				columnSizeMaker.applyColLengths();
			}

		});
	}
	
	public ArrayList<Attribute> getModelData()
	{
		return model.getData();
	}

	public void replaceModelData(final String[] titles,
			final ArrayList<Attribute> Slims) {

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				model.replaceModelData(Slims);
				// table.getColumnModel().getColumn(1).setMaxWidth(20);
				// table.getColumnModel().getColumn(1).setPreferredWidth(20);
				columnSizeMaker.revaluateColSizes();
				columnSizeMaker.applyColLengths();
				//new TablePacker(TablePacker.VISIBLE_ROWS, true).pack(table);
			}

		});

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		final JSlider source = (JSlider) e.getSource();
		if (!source.getValueIsAdjusting()) {
			sliderPane.getTextField().setText(
					"" + ((double) source.getValue()) / divider);
			doSortLogic();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		doSortLogic();
	}

	public void doSortLogic() {
		RowFilter<AttributeModel, Integer> sliderFilter = new RowFilter<AttributeModel, Integer>() {
			public boolean include(
					Entry<? extends AttributeModel, ? extends Integer> entry) {
				AttributeModel personModel = entry.getModel();
				Attribute attribute = personModel.getAttribute(entry
						.getIdentifier());
				Double value = (Double) attribute.getAttribute(sliderAttribute);
				if (value != null) {
					if (value > ((double) slider.getValue()) / divider) {
						return false;
					}

				}
				return true;
			}
		};
		sorter.setRowFilter(sliderFilter);
		new TablePacker(TablePacker.VISIBLE_ROWS, true).pack(table);
	}

	public void addNotify() {
		super.addNotify();
		addHierarchyListener(this);
	}

	public void removeNotify() {
		removeHierarchyListener(this);
		super.removeNotify();
	}

	public void hierarchyChanged(HierarchyEvent e) {
		if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
			if (isShowing()) {
				new TablePacker(TablePacker.VISIBLE_ROWS, true).pack(table);
			}
		}
	}
}
