// STAR-CCM+ macro: ExportScenes.java
// Written by Connor McQuillan
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.vis.*;

public class ExportScenes extends StarMacro {

  public void execute() {
    exportScenes();
  }

  private void exportScenes() {

    for(Scene scene : getActiveSimulation().getSceneManager().getObjects()){
		String file_name = getActiveSimulation().getPresentationName();
		String[] parts = file_name.split("@");
		String sim_name = parts[0];
		String folder_path = "H:\\Part III\\IP\\Standard_KE_2Layer\\6\\" + sim_name + "\\";
		scene.printAndWait(resolvePath(folder_path + scene.getPresentationName() + ".png"), 1, 1200, 800, true, false);
	}
	
  }
}
