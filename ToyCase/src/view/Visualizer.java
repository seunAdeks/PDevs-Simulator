package view;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.*;
import java.awt.*;
import java.io.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.text.Bidi;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;

public class Visualizer extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;

	// File chooser, used to select the file to run the simulation
	protected JFileChooser fileChooser_;

	// JLabel which contains the image of the traffic light
	protected JLabel image_;

	// Image of the traffic light off
	protected ImageIcon tf_off;

	// Image of the green traffic light
	protected ImageIcon tf_green;

	// Image of the orange traffic light
	protected ImageIcon tf_orange;

	// Image of the red traffic light
	protected ImageIcon tf_red;

	// Current time of the simulation
	protected double time;

	// Boolean which allows or not to run the visualization
	protected Boolean ready = false;
	
	// Buttons of control
	JButton run, quit;

	// List of the states and dates of times of the traffic light
	protected ArrayList<String[]> infos_;
	
	// Road lanes display
	Display lane;
	
	JPanel panel;
	JSlider slide;
	JLabel showSpeed;
	JLabel showCar;
	JLabel showTime;
	JLabel sc1;
	JLabel sc2;
	JLabel sc3;

	// Coefficient to change the speed of the visualization
	// The higher the faster
	int speed = 2;

	public JCheckBox[] cbxAllowedCar = new JCheckBox[3];

	private ImageIcon tf_offH;

	private ImageIcon tf_greenH;

	private ImageIcon tf_orangeH;

	private ImageIcon tf_redH;

	static Visualizer demo;
	
	// **********************************
	// Main method: just launches the GUI
	// **********************************
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// A new instance of the visualization window is created...
				demo = new Visualizer();
				// ... and the window is made visible
				demo.setVisible(true);
			}
		});
	}

	// ***************************************
	// Constructor of the visualization window
	// ***************************************
	public Visualizer() {
		super();
		infos_ = new ArrayList<String[]>();		// This is the data list (simulation results)
		build();								// This calls for the building of the visualization window
	}

	// ************************************************************************
	// This method builds the windows and place the components in a flow layout
	// ************************************************************************
	protected void build() {
		// Create a file chooser
		fileChooser_ = new JFileChooser();

		this.setTitle("Demo RoadSystem"); // A title is given to the application
		this.setSize(1000, 730);				// A size is defined for the application window
		this.setLocationRelativeTo(null);	// The window is centered in the screen
		this.setResizable(false);			// The window is allowed to be resized if needed
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// This properly closes the application
		// if the window is closed

		// Let's create the menu bar and the menu of the GUI
		createMenu();
		
		//Let's give a layout
		setLayout(new BorderLayout());
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(5, 1));
		
		slide = new JSlider(JSlider.VERTICAL, 1, 21, 2);
		//slide.setInverted(true);
		slide.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				JSlider slider = (JSlider) e.getSource();
				showSpeed.setText(new Integer(slider.getValue()).toString());
				speed = new Integer(slider.getValue());
			}
		});

		slide.setMajorTickSpacing(5);
		slide.setMinorTickSpacing(1);
		slide.setPreferredSize(new Dimension(150, 130));
		slide.setPaintTicks(true);
		
		Hashtable<Integer, JLabel> labelTableSpeed = new Hashtable<Integer, JLabel>();
		labelTableSpeed.put(20, new JLabel("Very Fast"));
		labelTableSpeed.put(15, new JLabel("Fast"));
		labelTableSpeed.put(10, new JLabel("Normal"));
		labelTableSpeed.put(5, new JLabel("Slow"));
		labelTableSpeed.put(1, new JLabel("Very Slow"));
		this.slide.setLabelTable(labelTableSpeed);
		this.slide.setPaintLabels(true);
		
		showSpeed = new JLabel("2");
		showCar = new JLabel("0");
		showTime = new JLabel("0");
		JLabel label1 = new JLabel("                   Total : ");
		//JLabel label2 = new JLabel("Speed Unit");
		JLabel label3 = new JLabel("Time:");
		
		JPanel timer = new JPanel();
		
		timer.add(label3);
		timer.add(showTime);
		
		buttons.add(timer);
		
		JPanel keeper = new JPanel();

		// Let's add a "run" button to the GUI
		run = new JButton("run");
		keeper.add(run);
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				run.setEnabled(false);
				new Thread (demo).start();	// If the button is clicked, simulation visualization is launched
			}
		});
		
		// Let's also add an "exit" button to the GUI
		quit = new JButton("exit");
		keeper.add(quit);
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ready = false;
				System.exit(0);	// If the button is clicked, the application is exited
			}
		});
		buttons.add(keeper);
		
		JPanel sl = new JPanel();
		sl.add(slide);
		sl.add(showSpeed);
		buttons.add(sl);
		//buttons.add(label2);
		
		cbxAllowedCar[0] = new JCheckBox("Red car", true);
		cbxAllowedCar[1] = new JCheckBox("Blue car", true);
		cbxAllowedCar[2] = new JCheckBox("Green car", true);
		JPanel select = new JPanel();
		select.setLayout(new GridLayout(3,1));
		select.add(cbxAllowedCar[0]);
		select.add(cbxAllowedCar[1]);
		select.add(cbxAllowedCar[2]);
		buttons.add(select);
		
		add(buttons,BorderLayout.EAST);
		
		JPanel lp = new JPanel();
		lp.setLayout(new BoxLayout(lp, BoxLayout.X_AXIS));
		JLabel lb = new JLabel("Car(s) exiting  Lane 1: ");
		JLabel lb2 = new JLabel("Lane 2: ");
		JLabel lb3 = new JLabel("Lane 3: ");
		sc1 = new JLabel(" 0              ");
		sc2 = new JLabel(" 0                              ");
		sc3 = new JLabel(" 0     ");
		lp.add(lb);
		lp.add(sc1);
		lp.add(lb2);
		lp.add(sc2);
		lp.add(lb3);
		lp.add(sc3);
		lp.add(label1);
		lp.add(showCar);
		add(lp,BorderLayout.SOUTH);
		
		// Let's load the traffic light images
		// created as image icons
		tf_off = createImageIcon("/images/off.jpg", "traffic light off");
		tf_green = createImageIcon("/images/green.jpg", "traffic light green");
		tf_orange = createImageIcon("/images/yellow.jpg",	"traffic light orange");
		tf_red = createImageIcon("/images/red.jpg", "traffic light red");
		
		tf_offH = createImageIcon("/images/offH.jpg", "traffic light off");
		tf_greenH = createImageIcon("/images/greenH.jpg", "traffic light green");
		tf_orangeH = createImageIcon("/images/yellowH.jpg",	"traffic light orange");
		tf_redH = createImageIcon("/images/redH.jpg", "traffic light red");
		

		// Let's initialize the current image and show it
		lane = new Display();
		//panel = new JPanel(true);
		//panel.setLayout(new GridLayout(6, 1));
		image_ = new JLabel("", tf_off, JLabel.CENTER);
		//setLayout(new FlowLayout());
		lane.shouw(tf_off);
		this.add(lane);
		//this.add(panel,BorderLayout.EAST);

	}
	
	// **********************************
	// Creates and returns an image icon,
	// or null if the path was invalid
	// **********************************
	protected static ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = Visualizer.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	// *********************************
	// Creates the menu bar and the menu
	// *********************************
	protected void createMenu() {
		// The menu bar's structure
		JMenuBar menuBar;	// Menu bar, used only once.
		JMenu menu;			// Menu, used for each main element of the menu bar
		JMenuItem menuItem; // Menu item, used for each item of a menu.

		// Create the menu bar.
		menuBar = new JMenuBar();

		// Build the "File" menu.
		menu = new JMenu("File");
		menuBar.add(menu);

		// Define the "Open" item in the "File" menu
		menuItem = new JMenuItem("Open");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				File file = getDocument();		// This gets a document via a file chooser (dialog box)
				if (file != null) {
					System.out.println("Open File : " + file.getName());
					initSim(file);				// This initializes the data list from the document chosen
					
				}
			}
		});

		// Add the item to the menu, and the menu to the menu bar
		menu.add(menuItem);
		this.setJMenuBar(menuBar);
	}

	// **********************************
	// Gets a document via a file chooser
	// **********************************
	protected File getDocument() {
		File file = null;
		int returnVal = fileChooser_.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser_.getSelectedFile();
		}
		return file;
	}

	// ********************************************
	// Initialize the data list from the file given
	// ********************************************
	protected void initSim(File file) {
		// Load the file
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		DataInputStream dis = null;

		try {
			fis = new FileInputStream(file);
			// Here BufferedInputStream is added for fast reading
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);
			// dis.available() returns 0 if the file does not have more lines
			while (dis.available() != 0) {
				String line = dis.readLine();
				String[] infos = line.split(" : ");
				infos_.add(infos);
			}
			// Dispose all the resources after using them
			fis.close();
			bis.close();
			dis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Now the visualization is allowed
		ready = true;		
	}
	
	public ArrayList<Boolean> selectCars(){
		ArrayList<Boolean> tem = new ArrayList<Boolean>();
		for (int i = 0 ; i < cbxAllowedCar.length ; ++i) {
			tem.add(cbxAllowedCar[i].isSelected());
		}
		return tem;
	}

	// *********************************
	// Runs the simulation visualization
	// *********************************
	public void run() {
		int timeToSleep;
		double elapsed;
		Integer total = 0;
		
		lane.allowedCars(selectCars());
		
		if (ready) {
			time = 0.0;
			int size = infos_.size();
			
			Collections.sort(infos_, new Comparator<String[]>() {

				@Override
				public int compare(String[] o1, String[] o2) {
					double a = Double.parseDouble(o1[0]);
					double b = Double.parseDouble(o2[0]);
					if(a > b)return 1;
					else return -1;
				}
			});
			

			for (int i = 0; i < size; ++i) {
				if (!ready) break;
				// read next event time
				time = Double.parseDouble(infos_.get(i)[0]);
				Double show = new Double(Math.round(time * 100));
				show = show/100;
				showTime.setText(show.toString());
				
				if(infos_.get(i)[1].equals("LIGHT")){

					// get the next image(color) of the traffic light
					String color = infos_.get(i)[2];

					if (color.compareTo("Green")==0) {
						lane.shouw(tf_green);
					} else if (color.compareTo("Yellow")==0) {
						lane.shouw(tf_orange);
					} else if (color.compareTo("Red")==0) {
						lane.shouw(tf_red);
					} else if (color.compareTo("Off")==0) {
						lane.shouw(tf_off);
					}

					// the future is now: update the display consequently
					//image_.paintImmediately(0, 0, image_.getWidth(), image_.getHeight());
					lane.repaint();
				}else if(infos_.get(i)[1].equals("LIGHT2")){

					// get the next image(color) of the traffic light
					String color = infos_.get(i)[2];

					if (color.compareTo("Green")==0) {
						lane.sho(tf_greenH);
					} else if (color.compareTo("Yellow")==0) {
						lane.sho(tf_orangeH);
					} else if (color.compareTo("Red")==0) {
						lane.sho(tf_redH);
					} else if (color.compareTo("Off")==0) {
						lane.sho(tf_offH);
					}

					// the future is now: update the display consequently
					//image_.paintImmediately(0, 0, image_.getWidth(), image_.getHeight());
					lane.repaint();
				}else if(infos_.get(i)[1].equals("LANE")){
					lane.refresh(infos_.get(i)[2], null,infos_.get(i)[3]);
					lane.repaint();
					sc1.setText(" "+infos_.get(i)[3]+"           ");
					//lane.paintImmediately(0, 0, lane.getWidth(), lane.getHeight());
				}else if(infos_.get(i)[1].equals("LANEE")){
					lane.refresh(null, infos_.get(i)[2], infos_.get(i)[3]);
					lane.repaint();
					sc2.setText(" "+infos_.get(i)[3]+"                           ");
					//lane.paintImmediately(0, 0, lane.getWidth(), lane.getHeight());
				}
				total = Integer.parseInt(sc1.getText().replaceAll(" ", "")) + Integer.parseInt(sc2.getText().replaceAll(" ", "")) + Integer.parseInt(sc3.getText().replaceAll(" ", ""));
				showCar.setText(total.toString());
				

				// stay in the current state for a time period according to simulation results
				timeToSleep = 0;
				elapsed = 0.0;
				if (i < size - 1) {
					elapsed = Double.parseDouble(infos_.get(i + 1)[0]);
					if((elapsed-time)==0)timeToSleep=800;
					else timeToSleep = (int) (elapsed-time)*1000;
				}

				try {
					Thread.sleep(timeToSleep/speed);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// update time display
				time += elapsed;
			}
			run.setEnabled(true);
		}
	}

}