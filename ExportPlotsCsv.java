// STAR-CCM+ macro: ExportPlotsCsv.java
// Written by Connor McQuillan
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;

public class ExportPlotsCsv extends StarMacro {

  public void execute() {
    exportPlots();
  }

  private void exportPlots() {

    for(StarPlot plot : getActiveSimulation().getPlotManager().getObjects()){
		String file_name = getActiveSimulation().getPresentationName();
		String[] parts = file_name.split("@");
		String sim_name = parts[0];
		String folder_path = "H:\\Part III\\IP\\Standard_KE_2Layer\\tall\\" + sim_name + "\\csvs\\";
		plot.export(resolvePath(folder_path + plot.getPresentationName() + ".csv"), ",");
	}
	
  }
}
