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
	private ArrayList<Bijoux> bijouxList;

	public RingsDashboardView(MainController mainController) {
		this.mainController = mainController;
		this.bijouxList = new ArrayList<>();

		for (Bijoux b : mainController.getProducts()) {
			if (b instanceof Ring) {
				bijouxList.add(b);
			}
		}

		setLayout(new BorderLayout());

		DashboardHeader header = new DashboardHeader(mainController);
		add(header, BorderLayout.NORTH);

		JPanel filterPanel = new FilterDashbaordPanel(bijouxList, this.getHeight(), mainController, this, "RING");
		add(filterPanel, BorderLayout.WEST);

		JPanel dashboardPanel = new JPanel();
		dashboardPanel.setLayout(new BorderLayout());
		add(dashboardPanel, BorderLayout.CENTER);

		UtilDisplayingDashboards.loadDashbaordImages(mainController, bijouxList, dashboardPanel,"client");
	}
}
