import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class NBodyApp {
	public static List <NBody> tempList = null;
	public static double scalingFactor = 0;
	public static int n = 20;

	public static void GenerateNBodiesBasedOnUsersNumber(int numberOfNBodiesToGenerate) {
		scalingFactor = 1000.0;
		Random r = new Random();
		int lowDiameter = 10;
		int highDiameter = 20;

		int lowMass = 1800;
		int highMass = 300000;

		int lowXcoordinate = 20;
		int highXCoordinate = 780;

		int lowYCoordinate = 20;
		int highYCoordinate = 780;

		for (int i = 0; i < numberOfNBodiesToGenerate; i++) {
			tempList.add(new NBody(r.nextInt(highMass - lowMass) + lowMass,
			r.nextInt(highXCoordinate - lowXcoordinate) + lowXcoordinate,
			r.nextInt(highYCoordinate - lowYCoordinate) + lowYCoordinate, r.nextDouble(-0.001, 0.001), r.nextDouble(-0.001, 0.001),
			r.nextInt(highDiameter - lowDiameter) + lowDiameter));
		}
	}

	public static void main(String[] args) {
		// user input
		try {
			n = Integer.parseInt(args[0]);
			n=n>5000?n=500:Integer.parseInt(args[0]);
		}
		catch (Exception e) {
			// default num of stars to 20 if user runs it without using cmd line
			System.out.println("You entered invalid value at command line");
		}
		tempList = new ArrayList<NBody>();
		GenerateNBodiesBasedOnUsersNumber(n);

		NBody nbody = new NBody(tempList, scalingFactor);
		nbody.setBackground(Color.black);

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		nbody.setBackground(Color.BLACK);
		nbody.panelSize = 800;
		nbody.maxVel = 10;
		nbody.maxMass = 10;
		nbody.dt = 0.1;
		nbody.setPreferredSize(new Dimension(nbody.panelSize, nbody.panelSize));

		frame.add(nbody);
		frame.pack();

		Timer timer = new Timer(16, nbody);
		timer.start();

		frame.setVisible(true);
	}
}
