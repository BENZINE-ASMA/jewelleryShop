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
	private ArrayList<Bijoux> bijouxList;

	public NecklacesDashboardView(MainController mainController) {
		this.mainController = mainController;
		this.bijouxList = new ArrayList<Bijoux>();

		for (Bijoux b : mainController.getProducts()) {
			if (b instanceof Necklace) {
				bijouxList.add(b);
			}
		}

		setLayout(new BorderLayout());

		DashboardHeader header = new DashboardHeader(mainController);
		add(header, BorderLayout.NORTH);

		JPanel filterPanel = new FilterDashbaordPanel(bijouxList, this.getHeight(), mainController, this, "NECKLACE");
		add(filterPanel, BorderLayout.WEST);

		JPanel dashboardPanel = new JPanel();
		dashboardPanel.setLayout(new BorderLayout());
		add(dashboardPanel, BorderLayout.CENTER);

		UtilDisplayingDashboards.loadDashbaordImages(mainController, bijouxList, dashboardPanel);
	}
}
