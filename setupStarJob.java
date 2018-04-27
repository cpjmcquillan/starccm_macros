// STAR-CCM+ macro: setupStarJob.java
// Written by STAR-CCM+ 12.04.011
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.base.report.*;
import star.flow.*;
import star.vis.*;
import star.meshing.*;

public class setupStarJob extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

	// Get regions and boundaries
    Simulation simulation_0 = getActiveSimulation();
	Region region_0 = simulation_0.getRegionManager().getRegion("Region");
	
	Boundary boundary_1 = region_0.getBoundaryManager().getBoundary("Subtract.Block Surface");
    boundary_1.setPresentationName("Inlet");
    PressureBoundary pressureBoundary_0 = ((PressureBoundary) simulation_0.get(ConditionTypeManager.class).get(PressureBoundary.class));
    boundary_1.setBoundaryType(pressureBoundary_0);

    Boundary boundary_2 = region_0.getBoundaryManager().getBoundary("Subtract.Block Surface 2");
    boundary_2.setPresentationName("Right");
    boundary_2.setBoundaryType(pressureBoundary_0);

    Boundary boundary_3 = region_0.getBoundaryManager().getBoundary("Subtract.Block Surface 3");
    boundary_3.setPresentationName("Ground");

    Boundary boundary_4 = region_0.getBoundaryManager().getBoundary("Subtract.Block Surface 4");
    boundary_4.setPresentationName("Top");
    SymmetryBoundary symmetryBoundary_0 = ((SymmetryBoundary) simulation_0.get(ConditionTypeManager.class).get(SymmetryBoundary.class));
    boundary_4.setBoundaryType(symmetryBoundary_0);

    Boundary boundary_5 = region_0.getBoundaryManager().getBoundary("Subtract.Block Surface 5");
    boundary_5.setPresentationName("Left");
    boundary_5.setBoundaryType(pressureBoundary_0);

    Boundary boundary_6 = region_0.getBoundaryManager().getBoundary("Subtract.Block Surface 6");
    boundary_6.setPresentationName("Outlet");
    boundary_6.setBoundaryType(pressureBoundary_0);

    Boundary boundary_7 = region_0.getBoundaryManager().getBoundary("Subtract.Block Surface 7");
    boundary_7.setPresentationName("Building1");

    Boundary boundary_8 = region_0.getBoundaryManager().getBoundary("Subtract.Block Surface 8");
    boundary_8.setPresentationName("Building2");

    Boundary boundary_9 = region_0.getBoundaryManager().getBoundary("Subtract.Block Surface 9");
    boundary_9.setPresentationName("Building3");

    Boundary boundary_10 = region_0.getBoundaryManager().getBoundary("Subtract.Block Surface 10");
    boundary_10.setPresentationName("Building4");

    Boundary boundary_11 = region_0.getBoundaryManager().getBoundary("Subtract.Block Surface 11");
    boundary_11.setPresentationName("Building6");

    Boundary boundary_12 = region_0.getBoundaryManager().getBoundary("Subtract.Block Surface 12");
    boundary_12.setPresentationName("Building5");
	
	// Reset region on probes
	List<String> buildings = Arrays.asList("b1", "b2", "b3", "b4", "b5", "b6");
	List<String> probes = Arrays.asList("Behind", "Gap", "Top", "Intersection");
	for (String probe : probes) {
		for (String building : buildings) {
			LinePart linePart_1 = ((LinePart) simulation_0.getPartManager().getObject(probe + "_" + building));
			linePart_1.getInputParts().setObjects(region_0);
		}
	}
	
	// Reset region on planes
	List<String> planes = Arrays.asList("X", "Y", "Z", "X=3.0", "Y=-1.5", "Y=0.0", "Z=2.0");
	for (String plane : planes) {
		PlaneSection planeSection_0 = ((PlaneSection) simulation_0.getPartManager().getObject(plane + "_plane"));
		planeSection_0.getInputParts().setObjects(region_0);
	}
	
	// Reset region on streamline
	StreamPart streamPart_0 = ((StreamPart) simulation_0.getPartManager().getObject("Streamline"));
    streamPart_0.getInputParts().setObjects(region_0);
	
	// Reset region on scenes with all walls
	List<String> scene_names = Arrays.asList("Skin_Friction", "Surface_Cp", "Wall_y1+");
	for (String scene_name : scene_names) {
		Scene scene_1 = simulation_0.getSceneManager().getScene(scene_name);
		ScalarDisplayer scalarDisplayer_0 = ((ScalarDisplayer) scene_1.getDisplayerManager().getDisplayer("Scalar 1"));
		scalarDisplayer_0.getInputParts().setObjects(boundary_7, boundary_8, boundary_9, boundary_10, boundary_12, boundary_11, boundary_2);
	}
	
	// Reset mesh scene
	Scene scene_1 = simulation_0.getSceneManager().getScene("Mesh_Wall");
	PartDisplayer meshDisplayer_0 = ((PartDisplayer) scene_1.getDisplayerManager().getDisplayer("Mesh 1"));
	meshDisplayer_0.getInputParts().setObjects(boundary_7, boundary_8, boundary_9, boundary_10, boundary_12, boundary_11, boundary_2);
	
	// Redefine drag monitor
    ForceCoefficientReport forceCoefficientReport_0 = ((ForceCoefficientReport) simulation_0.getReportManager().getReport("Drag Coefficient"));
    forceCoefficientReport_0.getParts().setObjects(boundary_7);
	
	// Generate Volume mesh
	MeshPipelineController meshPipelineController_0 = simulation_0.get(MeshPipelineController.class);
    meshPipelineController_0.generateVolumeMesh();
	
	// Recreate interface
    BoundaryInterface boundaryInterface_0 = simulation_0.getInterfaceManager().createBoundaryInterface(boundary_1, boundary_6, "Interface");
    BoundaryInterface boundaryInterface_1 = simulation_0.getInterfaceManager().createBoundaryInterface(boundary_4, boundary_5, "Interface");

    FullyDevelopedInterface fullyDevelopedInterface_0 = ((FullyDevelopedInterface) simulation_0.get(ConditionTypeManager.class).get(FullyDevelopedInterface.class));
    boundaryInterface_0.setInterfaceType(fullyDevelopedInterface_0);
    boundaryInterface_0.getTopology().setSelected(InterfaceConfigurationOption.Type.PERIODIC);
    boundaryInterface_1.setInterfaceType(fullyDevelopedInterface_0);
    boundaryInterface_1.getTopology().setSelected(InterfaceConfigurationOption.Type.PERIODIC);

    PressureJump pressureJump_0 = boundaryInterface_0.getValues().get(PressureJump.class);
    pressureJump_0.getPressureJump().setValue(-0.06610041);
  }
}
