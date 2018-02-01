package View;

import java.awt.*;

import Logic.Model;

public class PieView extends AbstractView {
	private static final long serialVersionUID = 5455934187803194147L;
	private float parkedC;
	private float parkedPC;
	private float parkedRC;

	public PieView(Model model) {
		super(model);
		setSize(250, 200);
	}
	
	public void giveStats(int totalC, int parkedC, int parkedPC, int parkedRC) {
		if(this.isVisible()) {
			int tempTotalC = 1;
			
			if(totalC == 0){ 
				tempTotalC = 1;
			}
			else { 
				tempTotalC = totalC;
			}
			
			this.parkedC = ((float)parkedC * 100 / (float)tempTotalC * 3.6f);
			this.parkedPC = ((float)parkedPC * 100 / (float)tempTotalC * 3.6f);
			this.parkedRC = ((float)parkedRC * 100 / (float)tempTotalC * 3.6f);
			
			if((int)this.parkedC + (int)this.parkedPC + (int)this.parkedRC == 358) {
				this.parkedC += 2;
			}
			else if ((int)this.parkedC + (int)this.parkedPC + (int)this.parkedRC == 359) {
				this.parkedC += 2;
			}
			else {
				
			}
			super.updateView();
		}
		
		
	}

	public void paintComponent(Graphics g) {
		
		g.setColor(new Color(238,238,238));
		g.fillRect(200,0,50,200);
		
		g.setColor(new Color(238,238,238));
		g.fillRect(0, 0, 200, 200);
		g.setColor(Color.BLUE);
		g.fillArc(10, 10, 180, 180, 0, (int)this.parkedC + 1);
		g.fillRect(230, 70, 10, 10);
		g.setColor(Color.RED);
		g.fillArc(10, 10, 180, 180, (int)this.parkedC, (int)this.parkedPC);
		g.fillRect(230, 85, 10, 10);
		g.setColor(Color.GREEN);
		g.fillArc(10, 10, 180, 180, (int)this.parkedPC + (int)parkedC, (int)parkedRC);
		g.fillRect(230, 100, 10, 10);
	}
}
