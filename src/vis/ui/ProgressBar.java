package vis.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class ProgressBar extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ProgressBar dialog = new ProgressBar("");
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private final JPanel contentPanel = new JPanel();
	
	JProgressBar progressBar = null;
	JButton cancelButton = null;

	/**
	 * Create the dialog.
	 */
	public ProgressBar(String display) {
		setBounds(100, 100, 386, 131);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(12, 31, 330, 14);
		contentPanel.add(progressBar);
		
		JLabel lblNewLabel = new JLabel(display);
		lblNewLabel.setBounds(12, 12, 132, 15);
		contentPanel.add(lblNewLabel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				cancelButton = new JButton("Cancel");
				
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public void addCencelListener(ActionListener listener)
	{
		cancelButton.setActionCommand("Cancel");
		cancelButton.addActionListener(listener);
	}
	
	public synchronized void addProgress(final int i)
	{
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				int j = progressBar.getValue();
				progressBar.setValue(i + j);
				progressBar.updateUI();
			}
			
		});
		
	}
	
	public synchronized void setProgress(final int i)
	{
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				progressBar.setValue(i);
				progressBar.updateUI();
			}
			
		});
		
	}
}
