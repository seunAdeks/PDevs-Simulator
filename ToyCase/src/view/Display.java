package view;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Display extends JPanel{
	
	private int Cell_WidthV = 50;
	private int Cell_HeightV = 370/5;
	
	private ArrayList<Boolean> allowed;
	
	private int Cell_WidthH = 515/5;
	private int Cell_HeightH = 50;
	
	public  int aGo,kip,aGo2,kip2;
	public int[] former,former2;
	Random x = new Random();
	
	public JLabel inLane1[] = new JLabel[5];
	public JLabel inLane2[] = new JLabel[5];
	
	JLabel lastTop,lastH,going1,going2;
	
	public JLabel tra = new JLabel();
	public JLabel tra2 = new JLabel();
	
	
	public ImageIcon img1H,img2H,img3H,img1V,img2V,img3V;
	
	public Display(){
		super(true);
		
		img1V = createImageIcon("/images/redV.png", "car type 1");
		img2V = createImageIcon("/images/blueV.png", "car type 2");
		img3V = createImageIcon("/images/greenV.png", "car type 3");
		
		img1H = createImageIcon("/images/redH.png", "car type 1");
		img2H = createImageIcon("/images/blueH.png", "car type 2");
		img3H = createImageIcon("/images/greenH.png", "car type 3");
		
		int x=715;
		
		for(int i =0; i < inLane1.length; i++){
			//System.out.println(i*600/5);
			inLane1[i]=new JLabel();
			inLane1[i].setBounds(x, (i*370/5)+295, Cell_WidthV, Cell_HeightV);
			inLane1[i].setHorizontalAlignment(JLabel.CENTER);
			inLane1[i].setVerticalAlignment(JLabel.CENTER);
		}
		
		int y=180;
		
		for(int i =0; i < inLane2.length; i++){
			inLane2[i]=new JLabel();
			inLane2[i].setBounds(i*515/5, y, Cell_WidthH, Cell_HeightH);
			inLane2[i].setHorizontalAlignment(JLabel.CENTER);
			inLane2[i].setVerticalAlignment(JLabel.CENTER);
		}
		
		
		setLayout(null);
		for(JLabel cell : inLane1)add(cell);
		for(JLabel cell : inLane2)add(cell);
		 lastTop = new JLabel();
		 lastTop.setBounds(x, (370/5)+130, Cell_WidthV, Cell_HeightV);
		 lastTop.setHorizontalAlignment(JLabel.CENTER);
		 lastTop.setVerticalAlignment(JLabel.CENTER);
		 lastH = new JLabel();
		 lastH.setBounds((5*515/5), y, Cell_WidthH, Cell_HeightH);
		 lastH.setHorizontalAlignment(JLabel.CENTER);
		 lastH.setVerticalAlignment(JLabel.CENTER);
		 going1 = new JLabel();
		 going1.setBounds(380, 51, Cell_WidthH, Cell_HeightH);
		 going1.setHorizontalAlignment(JLabel.CENTER);
		 going1.setVerticalAlignment(JLabel.CENTER);
		 
		 going2 = new JLabel();
		 going2.setBounds(560, 310, Cell_WidthV, Cell_HeightV);
		 going2.setHorizontalAlignment(JLabel.CENTER);
		 going2.setVerticalAlignment(JLabel.CENTER);
		
		 add(lastTop);
		 add(lastH);
		 add(going1);
		 add(going2);
		//tra.setIcon(createImageIcon("/images/off.jpg", "traffic light off"));
		tra.setBounds(480, 320, 40, 90);
		add(tra);
		tra2.setIcon(createImageIcon("/images/offH.jpg", "traffic light off"));
		tra2.setBounds(380, 280, 90, 40);
		add(tra2);
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.getToolkit().getImage(this.getClass().getResource("/images/CrossR.jpg")), 0, 0, this);
    }
	
	public void refresh(String ln1, String ln2, String capPassed){
		
		int[] a = null;
		boolean carHasPassed = false;
		int toGo = Integer.parseInt(capPassed);
		
		if(ln1!=null)
		{
			if(lastTop.getIcon() != null){
				if(lastTop.getIcon() == img1V)
					going1.setIcon(img1H);
				else if(lastTop.getIcon() == img2V)
					going1.setIcon(img2H);
				else
					going1.setIcon(img3H);
			}
			else going1.setIcon(null);
			lastTop.setIcon(null);
			if(toGo>0 && toGo != aGo){
				carHasPassed=true;
			}
			ImageIcon img = null;
			if(carHasPassed)lastTop.setIcon(inLane1[0].getIcon());
			String[] items = ln1.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "").split(",");
			a = new int[items.length];
			for(int i=items.length-1;i>=0;i--)
				a[a.length-1-i]=Integer.parseInt(items[i]);
			
			for(int i=0;i<a.length;i++){
				if(a[i]==1 && !carHasPassed && getZero(former)==-1){
					inLane1[i].setIcon(inLane1[i].getIcon());
				}
				if(a[i]==1 && i == 4){
					if(getZero(former)==-1)inLane1[i].setIcon(inLane1[i].getIcon());
					else{
						switch(getCar()){
							case 0: img=img1V;
									break;
							case 1: img=img2V;
									break;
							case 2: img=img3V;
									break;
						}
						inLane1[i].setIcon(img);
					}
				}
				else if(a[i]==0){
					inLane1[i].setIcon(null);
				}
				else if(a[i]==1 && a[0]+kip==2){
					int b = getZero(former);
					if(b!=-1 && b>i && !carHasPassed) inLane1[i].setIcon(inLane1[i].getIcon());
					else if(b==-1) inLane1[i].setIcon(inLane1[i].getIcon());
					else inLane1[i].setIcon(inLane1[i+1].getIcon());
				}else
					inLane1[i].setIcon(inLane1[i+1].getIcon());
			}
			kip = a[0];
			aGo = toGo;
			former=a;
		}
		if(ln2!=null)
		{
			if(lastH.getIcon() != null){
				if(lastH.getIcon() == img1H)
					going2.setIcon(img1V);
				else if(lastH.getIcon() == img2H)
					going2.setIcon(img2V);
				else
					going2.setIcon(img3V);
			}
			else going2.setIcon(null);
			lastH.setIcon(null);
			if(toGo>0 && toGo != aGo2){
				carHasPassed=true;
			}
			ImageIcon img = null;
			if(carHasPassed)lastH.setIcon(inLane2[4].getIcon());
			String[] items = ln2.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "").split(",");
			a = new int[items.length];
			for(int i=items.length-1;i>=0;i--)
				a[i]=Integer.parseInt(items[i]);
			
			for(int i=a.length-1;i>=0;i--){
				if(a[i]==1 && !carHasPassed && getZero2(former2)==-1){
					inLane2[i].setIcon(inLane2[i].getIcon());
				}
				else if(a[i]==1 && i == 0){
					switch(getCar()){
						case 0: img=img1H;
								break;
						case 1: img=img2H;
								break;
						case 2: img=img3H;
								break;
					}
					inLane2[i].setIcon(img);
				}
				else if(a[i]==0){
					inLane2[i].setIcon(null);
				}
				else if(a[i]==1 && a[4]+kip2==2){
					int b = getZero2(former2);
					if(b!=-1 && b<i && !carHasPassed) inLane2[i].setIcon(inLane2[i].getIcon());
					else inLane2[i].setIcon(inLane2[i-1].getIcon());
				}else
					inLane2[i].setIcon(inLane2[i-1].getIcon());
			}
			kip2 = a[4];
			aGo2 = toGo;
			former2=a;
		}
		
	}
	
	protected static ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = Display.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
	
	public void allowedCars(ArrayList<Boolean> al){
		allowed = al;
	}
	
	protected int getCar(){
		int rand;
		do {
			rand = x.nextInt(allowed.size());
		} while (!allowed.get(rand));

		return rand;
	}
	
	public void shouw(ImageIcon x){
		tra.setIcon(x);
		tra.repaint();
	}
	
	public void sho(ImageIcon x){
		tra2.setIcon(x);
		tra2.repaint();
	}
	
	public int getZero(int[] a){
		if(a == null)return -2;
		for(int i=0;i<a.length;i++){
			if(a[i]==0)return i;
		}
		return -1;
	}
	
public int getZero2(int[] a){
		if(a == null)return -2;
		for(int i=a.length-1;i>=0;i--){
			if(a[i]==0)return i;
		}
		return -1;
	}

}
