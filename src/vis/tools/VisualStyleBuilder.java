package vis.tools;

import java.awt.Color;
import java.awt.Font;

import vis.data.Constants;
import cytoscape.CyNetwork;
import cytoscape.visual.EdgeAppearance;
import cytoscape.visual.EdgeAppearanceCalculator;
import cytoscape.visual.GlobalAppearanceCalculator;
import cytoscape.visual.NodeAppearance;
import cytoscape.visual.NodeAppearanceCalculator;
import cytoscape.visual.NodeShape;
import cytoscape.visual.VisualPropertyType;
import cytoscape.visual.VisualStyle;
import cytoscape.visual.calculators.BasicCalculator;
import cytoscape.visual.calculators.Calculator;
import cytoscape.visual.mappings.ContinuousMapping;
import cytoscape.visual.mappings.DiscreteMapping;
import cytoscape.visual.mappings.Interpolator;
import cytoscape.visual.mappings.LinearNumberToColorInterpolator;
import cytoscape.visual.mappings.PassThroughMapping;

public class VisualStyleBuilder {

	public static VisualStyle createPrimaryVisualStyle(CyNetwork network) {

		VisualStyle visualstyle = new VisualStyle("slimstyle");

		NodeAppearanceCalculator nodeAppCalc = visualstyle
				.getNodeAppearanceCalculator();
		EdgeAppearanceCalculator edgeAppCalc = visualstyle
				.getEdgeAppearanceCalculator();

		GlobalAppearanceCalculator globalAppCalc = new GlobalAppearanceCalculator();

		
		
		Color defaultColor = new Color(255, 200, 120);
		
		NodeAppearance nodeAppDefault = new NodeAppearance();
        nodeAppDefault.set(VisualPropertyType.NODE_FILL_COLOR, defaultColor);
        nodeAppDefault.set(VisualPropertyType.NODE_LINE_WIDTH, 1);
        nodeAppDefault.set(VisualPropertyType.NODE_SHAPE, NodeShape.ELLIPSE);
        nodeAppDefault.set(VisualPropertyType.NODE_SIZE, 40);
        nodeAppDefault.set(VisualPropertyType.NODE_HEIGHT, 36);
        nodeAppDefault.set(VisualPropertyType.NODE_WIDTH, 60);
        nodeAppDefault.set(VisualPropertyType.NODE_FONT_SIZE, 12);
        nodeAppDefault.set(VisualPropertyType.NODE_OPACITY, 155);
        Font font = new Font("FREE SANS BOLD", Font.BOLD, 12);
        nodeAppDefault.set(VisualPropertyType.NODE_FONT_FACE, font);
		
		
		nodeAppCalc.setDefaultAppearance(nodeAppDefault);
		
		
		EdgeAppearance edgeAppDefault = new EdgeAppearance();
		edgeAppDefault.set(VisualPropertyType.EDGE_LINE_WIDTH, 1);
		edgeAppDefault.set(VisualPropertyType.EDGE_COLOR, new Color(179,176,176));
        edgeAppCalc.setDefaultAppearance(edgeAppDefault);
		
		
		//Calculators
		PassThroughMapping pm = new PassThroughMapping(String.class, Constants.getLabel());
		Calculator nlc = new BasicCalculator(Constants.pluginName + " Node Label Calculator",
				pm, VisualPropertyType.NODE_LABEL);
		nodeAppCalc.setCalculator(nlc);

		ContinuousMapping continuousMapping = new ContinuousMapping(
				java.awt.Color.class, Constants.getActivityLevel());

		Interpolator numToColor = new LinearNumberToColorInterpolator();
		continuousMapping.setInterpolator(numToColor);

		Color slim = new Color(205,0,0);
		Color domain = new Color(100,149,237);
		Color both = new Color(128,0,128);
		Color other = Color.GRAY;
		
		//Default
		

		Calculator nodeColorCalculator = new BasicCalculator(
				"Example Node Color Calc", continuousMapping,
				VisualPropertyType.NODE_FILL_COLOR);

		nodeAppCalc.setCalculator(nodeColorCalculator);
		

		
		DiscreteMapping colorMapping = new DiscreteMapping(Color.class, Constants.getActivityLevel());
		colorMapping.putMapValue(0, defaultColor);
		colorMapping.putMapValue(1, slim);
		colorMapping.putMapValue(2, domain);
		colorMapping.putMapValue(3, both);
		colorMapping.putMapValue(4, other);
		Calculator colorCalculator = new BasicCalculator(Constants.pluginName + " Node Colour Calculator", colorMapping, VisualPropertyType.NODE_FILL_COLOR);
		
		nodeAppCalc.setCalculator(colorCalculator);
		
		
		DiscreteMapping disMapping = new DiscreteMapping(NodeShape.class, Constants.getType());
		disMapping.putMapValue("slim", NodeShape.DIAMOND);
		disMapping.putMapValue("domain", NodeShape.RECT_3D);
		disMapping.putMapValue("both", NodeShape.TRIANGLE);
		disMapping.putMapValue("none", NodeShape.ELLIPSE);
		Calculator shapeCalculator = new BasicCalculator(Constants.pluginName + " Node Shape Calculator", disMapping, VisualPropertyType.NODE_SHAPE);
		nodeAppCalc.setCalculator(shapeCalculator);

		VisualStyle visualStyle = new VisualStyle(Constants.getVisualStyleName(), nodeAppCalc,
				edgeAppCalc, globalAppCalc);

		return visualStyle;
	}
}