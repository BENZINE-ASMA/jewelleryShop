package view;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import controller.MainController;
import lombok.Getter;
import lombok.Setter;
import model.Bijoux;
import model.Necklace;

import shared.UtilDisplayingDashboards;

@Getter
@Setter
public class NecklacesDashboardView extends JPanel {
	private MainController mainController;
	private ArrayList<Necklace> necklaces;
		
	public NecklacesDashboardView(MainController mainController) {
		this.mainController = mainController;
		this.necklaces = new ArrayList<Necklace>();
			for(Bijoux b : mainController.getProducts()) {
		       	System.out.println(b.toString());
		        	if (b instanceof Necklace) {
		        		necklaces.add((Necklace) b);
		        	}
		        }
		        setLayout(new BorderLayout());

		        DashboardHeader header = new DashboardHeader(mainController);
		        add(header, BorderLayout.NORTH);

		       
		        UtilDisplayingDashboards.loadDashbaordImages(mainController,this.necklaces, this);
		    }
		

}
