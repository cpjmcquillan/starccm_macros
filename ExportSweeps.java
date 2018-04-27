// STAR-CCM+ macro: ExportSweeps.java
// Written by STAR-CCM+ 12.04.011
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.vis.*;

public class ExportSweeps extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {
	// Setup
    Simulation simulation_0 = getActiveSimulation();
    Region region_0 = simulation_0.getRegionManager().getRegion("Region");
    FvRepresentation fvRepresentation_0 = ((FvRepresentation) simulation_0.getRepresentationManager().getObject("Volume Mesh"));
    simulation_0.getDataSourceManager().getPartExtents(new NeoObjectVector(new Object[] {region_0}), fvRepresentation_0);
	// Get sim name
	String file_name = simulation_0.getPresentationName();
	String[] parts = file_name.split("@");
	String sim_name = parts[0];
	String folder_path = "H:\\Part III\\IP\\Standard_KE_2Layer\\6\\" + sim_name + "\\";
	
	// Define planes...horrible format
	ArrayList<HashMap> planes = new ArrayList<HashMap>();
	HashMap<String, String> x_plane = new HashMap<String, String>();
	x_plane.put("normal", "X");
	x_plane.put("extent_l", "0");
	x_plane.put("extent_u", "60");
	x_plane.put("resolution_x", "1200");
	x_plane.put("resolution_y", "800");
	HashMap<String, String> y_plane = new HashMap<String, String>();
	y_plane.put("normal", "Y");
	y_plane.put("extent_l", "-30");
	y_plane.put("extent_u", "30");
	y_plane.put("resolution_x", "1200");
	y_plane.put("resolution_y", "800");
	HashMap<String, String> z_plane = new HashMap<String, String>();
	z_plane.put("normal", "Z");
	z_plane.put("extent_l", "0");
	z_plane.put("extent_u", "60");
	z_plane.put("resolution_x", "1200");
	z_plane.put("resolution_y", "800");
	planes.add(x_plane);
	planes.add(y_plane);
	planes.add(z_plane);
	
	// Define variables
	List<String> variables = Arrays.asList("Cp", "TKE", "Vorticity");
	
	// Iterate planes
	for (HashMap plane : planes){
		// Get plane
		PlaneSection planeSection_0 = ((PlaneSection) simulation_0.getPartManager().getObject(plane.get("normal") + "_plane"));
		Coordinate coordinate_0 = planeSection_0.getOriginCoordinate();
		Units units_0 = ((Units) simulation_0.getUnitsManager().getObject("m"));
		String plane_path = folder_path + plane.get("normal") + "\\";
		String extent_l = (String)plane.get("extent_l");
		String extent_u = (String)plane.get("extent_u");
		String resolution_x = (String)plane.get("resolution_x");
		String resolution_y = (String)plane.get("resolution_y");
		// Iterate variables
		for (String variable : variables){
			// Iterate plane extent and export scenes
			String variable_path = plane_path + variable + "\\";
			for(int n = Integer.parseInt(extent_l); n <= Integer.parseInt(extent_u); n++){
				double i = 0.1 * n;
				if (plane.get("normal") == "X") {
					coordinate_0.setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {i, 0.0, 0.0}));
				} else if (plane.get("normal") == "Y") {
					coordinate_0.setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, i, 0.0}));
				} else {
					coordinate_0.setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, i}));
				}
				String scene_name = plane.get("normal") + "_" + variable;
				Scene scene_0 = simulation_0.getSceneManager().getScene(scene_name);
				scene_0.printAndWait(resolvePath(variable_path + scene_name + "_" + (double)Math.round(i * 10d) / 10d + ".png"), 1, Integer.parseInt(resolution_x), Integer.parseInt(resolution_y), true, false);
			}
		}
	}
  }
}
