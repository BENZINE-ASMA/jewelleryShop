package view;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import controller.MainController;
import lombok.Getter;
import lombok.Setter;
import model.Bijoux;
import model.Ring;
import shared.UtilDisplayingDashboards;

 @Getter
 @Setter
public class RingsDashboardView extends JPanel {
	private MainController mainController;
	private ArrayList<Ring> rings;
	
	public RingsDashboardView(MainController mainController) {
		this.mainController = mainController;
		this.rings = new ArrayList<Ring>();
	        for(Bijoux b : mainController.getProducts()) {
	        	if (b instanceof Ring) {
	        		rings.add((Ring) b);
	        	}
	        }
	        setLayout(new BorderLayout());

	        DashboardHeader header = new DashboardHeader(mainController);
	        add(header, BorderLayout.NORTH);

	       
	        UtilDisplayingDashboards.loadDashbaordImages(mainController,this.rings, this);
	    }
	
}
