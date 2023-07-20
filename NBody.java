import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

public class NBody extends Canvas implements ActionListener {
	public int n;
	public int x;
	public int y;
	public int diameter;
	public double dt;
	public double mass;
	public double xVelocity;
	public double yVelocity;
	public double xForce;
	public double yForce;
	public int xCoordinate;
	public int yCoordinate;
	public Color color;
	public double maxVel;
	public double maxMass;
	public int panelSize;

	public List<NBody> listOfStars;
	public double scalingFactor;
	public double G = 6.673e-11;

	public NBody(double mass, int xCoordinateValue, int yCoordinateValue, double xVelocity, double yVelocity,
		int diameter) {
		this.mass = mass;
		this.xCoordinate = xCoordinateValue;
		this.yCoordinate = yCoordinateValue;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		this.diameter = diameter;

		Random r = new Random();
		color = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256), r.nextInt(256));
	}

	public NBody(List<NBody> starsList, double sc) {
		listOfStars = starsList;
		scalingFactor = sc;
	}

	public void drawCircle(Graphics g, int centerX, int centerY, int r) {
		int diameter = 2 * r;
		g.fillOval(centerX - r, centerY - r, diameter, diameter);
	}

	public void paint(Graphics g) {
		//calling super to do initialization
		super.paint(g);

		for (int i = 0; i < listOfStars.size(); i++) {
			
			//setting color of the graphics based on random color of starts
			g.setColor(listOfStars.get(i).getColor());
			
			//drawing the star
			g.fillOval(listOfStars.get(i).getxCoordinate(), listOfStars.get(i).getyCoordinate(), listOfStars.get(i).getNBodySize(),
					listOfStars.get(i).getNBodySize());
		}
	}

	public void actionPerformed(ActionEvent e) {
		//updating the stars and repainting screen
		update();
		repaint();
		Toolkit.getDefaultToolkit().sync();
	}

	// helper methods to calculate the physics for the stars
	public void update() {
		int numberOfStars;
		for (numberOfStars = 0; numberOfStars < listOfStars.size() - 1; numberOfStars++) {
			listOfStars.get(numberOfStars).force(listOfStars.get(numberOfStars + 1), scalingFactor);
			listOfStars.get(numberOfStars).updatePos();
			listOfStars.get(numberOfStars).resetForce();
		}
	}

	public void updatePos() {
		// update x and y velocities
		xVelocity += xForce / mass;
		yVelocity += yForce / mass;

		// update x and y coordinates
		xCoordinate += xVelocity;
		yCoordinate += yVelocity;
	}

	// calculate force
	public void force(NBody nBody, double scale) {
		NBody currentNBody = this;
	
		double xCoordinate = nBody.xCoordinate - currentNBody.xCoordinate;
		double yCoordinate = nBody.yCoordinate - currentNBody.yCoordinate;
		
		double magnituede = Math.sqrt(xCoordinate * xCoordinate + yCoordinate * yCoordinate);
		
		// calculate graviation force between two masses
		double force = (G * currentNBody.mass * nBody.mass / ((magnituede * magnituede) / scale));
		
		currentNBody.xForce += force * xCoordinate / magnituede;
		currentNBody.yForce += force * yCoordinate / magnituede;
	}

	public void resetForce() {
		xForce = 0;
		yForce = 0;
	}

	public double getMass() {
		return mass;
	}

	public int getxCoordinate() {
		return xCoordinate;
	}

	public int getyCoordinate() {
		return yCoordinate;
	}

	public double getxVelocity() {
		return xVelocity;
	}

	public double getyVelo() {
		return yVelocity;
	}

	public int getNBodySize() {
		return diameter;
	}
	
	public Color getColor() {
		return color;
	}
}