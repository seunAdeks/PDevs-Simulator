package view;

import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @class Options
 * 
 * Provides commands to set the simulation and display
 */
public class Options extends JPanel implements ChangeListener {

	private static final long serialVersionUID = -8025109706933659119L;
	
	private static final int CBX_WIDTH = 185;
    private static final int CBX_HEIGHT = 20;
    
    private static final int SPEED_MIN = 200;
    private static final int SPEED_MAX = 10;
    private static final int SPEED_GRAD = 10;
    
    private Runnable runner;
    
    private JCheckBox cbxAllowedCar[] = new JCheckBox[3];    
	private JButton btnStart = null;
	private JButton btnStop = null;
	private JSlider sliderSpeed = null;
	private JLabel lblTxtSpeed = null;
	private JLabel lblSpeed = null;
	private JSlider sliderPeriod = null;
	
	public Options(Runnable R) {
		super();
		
		runner = R;
		
		this.setLayout(null);
		
	    this.cbxAllowedCar[0] = new JCheckBox("Red car", true);
	    this.cbxAllowedCar[0].setBounds(5, 5, CBX_WIDTH, CBX_HEIGHT);
	    this.add(this.cbxAllowedCar[0]);
		
	    this.cbxAllowedCar[1] = new JCheckBox("Blue car", true);
		this.cbxAllowedCar[1].setBounds(5, 25, CBX_WIDTH, CBX_HEIGHT);
		this.add(this.cbxAllowedCar[1]);
		
		this.cbxAllowedCar[2] = new JCheckBox("Green car", true);
		this.cbxAllowedCar[2].setBounds(5, 45, CBX_WIDTH, CBX_HEIGHT);
		this.add(this.cbxAllowedCar[2]);
		
		
		
		this.btnStart = new JButton("Start simulation");
		this.btnStart.setBounds(5, 100, 150, 20);
		this.btnStart.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				btnStart.setEnabled(false);
				new Thread (runner).start();
			}
		});
		this.add(this.btnStart);
		
		this.btnStop = new JButton("Stop simulation");
		this.btnStop.setBounds(5, 150, 150, 20);
		this.btnStop.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				System.exit(0);
			}
		});
		this.add(this.btnStop);
		
		this.sliderSpeed = new JSlider(JSlider.VERTICAL, SPEED_MAX, SPEED_MIN, 100);
		this.sliderSpeed.setInverted(true);
		this.sliderSpeed.setBounds(5, 190, 175, 250);
		this.sliderSpeed.addChangeListener(this);

		this.sliderSpeed.setMajorTickSpacing(SPEED_GRAD);
		this.sliderSpeed.setPaintTicks(true);

		Hashtable<Integer, JLabel> labelTableSpeed = new Hashtable<Integer, JLabel>();
		labelTableSpeed.put(10, new JLabel("Very Fast"));
		labelTableSpeed.put(50, new JLabel("Fast"));
		labelTableSpeed.put(100, new JLabel("Normal"));
		labelTableSpeed.put(150, new JLabel("Slow"));
		labelTableSpeed.put(200, new JLabel("Very Slow"));
		this.sliderSpeed.setLabelTable(labelTableSpeed);
		this.sliderSpeed.setPaintLabels(true); 
		
		this.add(this.sliderSpeed);
		
		this.lblSpeed = new JLabel("100");
		this.lblSpeed.setBounds(10, 450, 25, 25);
		this.add(this.lblSpeed);
		
		this.lblTxtSpeed = new JLabel("per unit time");
		this.lblTxtSpeed.setBounds(35, 450, 120, 25);
		this.add(this.lblTxtSpeed);
	}
	
	public JCheckBox[] getCbxAllowedCar() {
		return this.cbxAllowedCar;
	}
	
	public JButton getBtnStart() {
		return this.btnStart;
	}

	public void stateChanged(ChangeEvent e) {
		JSlider slider = (JSlider) e.getSource();
		if (slider == this.sliderSpeed) {
			//Simulation.getInstance().getSimview().setSpeed(slider.getValue());
			this.lblSpeed.setText(new Integer(slider.getValue()).toString());
		}
		
	}

	public JSlider getSliderSpeed() {
		return sliderSpeed;
	}

}
