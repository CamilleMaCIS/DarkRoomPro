import java.awt.*;
import java.net.URL;
// The secret image: CS 60 is written inside a heart
/**
 * A class that represents a picture.  This class inherits from SimplePicture
 * 	and allows the student to add functionality and picture effects.
 *
 * @author Barb Ericson (ericson@cc.gatech.edu)
 * @author Modified by Colleen Lewis (lewis@cs.hmc.edu),
 * 	Jonathan Kotker (jo_ko_berkeley@berkeley.edu),
 * 	Kaushik Iyer (kiyer@berkeley.edu), George Wang (georgewang@berkeley.edu),
 * 	and David Zeng (davidzeng@berkeley.edu), Edwin Lagos (elagos@cis.edu.hk)
 *
 *
 */
public class Picture extends SimplePicture
{
	/////////////////////////// Static Variables //////////////////////////////

	// Different axes available to flip a picture.
	public static final int HORIZONTAL = 1;
	public static final int VERTICAL = 2;
	public static final int FORWARD_DIAGONAL = 3;
	public static final int BACKWARD_DIAGONAL = 4;


	//////////////////////////// Constructors /////////////////////////////////

	/**
	 * A constructor that takes no arguments.
	 */
	public Picture () {
		super();
	}

	/**
	 * Creates a Picture from the file name provided.
	 *
	 * @param fileName The name of the file to create the picture from.
	 */
	public Picture(String fileName) {
		// Let the parent class handle this fileName.
		super(fileName);
	}

	/**
	 * Creates a Picture from the width and height provided.
	 *
	 * @param width the width of the desired picture.
	 * @param height the height of the desired picture.
	 */
	public Picture(int width, int height) {
		// Let the parent class handle this width and height.
		super(width, height);
	}

	/**
	 * Creates a copy of the Picture provided.
	 *
	 * @param pictureToCopy Picture to be copied.
	 */
	public Picture(Picture pictureToCopy) {
		// Let the parent class do the copying.
		super(pictureToCopy);
	}

	/**
	 * Creates a copy of the SimplePicture provided.
	 *
	 * @param pictureToCopy SimplePicture to be copied.
	 */
	public Picture(SimplePicture pictureToCopy) {
		// Let the parent class do the copying.
		super(pictureToCopy);
	}

	/////////////////////////////// Methods ///////////////////////////////////

	//////////////////////////// Provided Methods /////////////////////////////////

	/**
	 * Helper method to determine if a x and y coordinate is valid (within the image)
	 *
	 * @param ix is the x value that might be outside of the image
	 * @param iy is the y value that might be outside of the image
	 * @return true if the x and y values are within the image and false otherwise
	 */
	@SuppressWarnings("unused")
	private boolean inImage(int ix, int iy) {
		return ix >= 0 && ix < this.getWidth() && iy >= 0
				&& iy < this.getHeight();
	}

	/**
	 * @return A string with information about the picture, such as
	 * 	filename, height, and width.
	 */
	public String toString() {
		String output = "Picture, filename = " + this.getFileName() + "," +
				" height = " + this.getHeight() + ", width = " + this.getWidth();
		return output;
	}
	/**
	 * Equals method for two Picture objects.
	 *
	 * @param obj is an Object to compare to the current Picture object
	 * @return true if obj is a Picture object with the same size as the
	 *         original and with the same color at each Pixel
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof Picture)) {
			return false;
		}

		Picture p = (Picture) obj;
		// Check that the two pictures have the same dimensions.
		if ((p.getWidth() != this.getWidth()) ||
				(p.getHeight() != this.getHeight())) {
			return false;
		}

		// Check each pixel.
		for (int x = 0; x < this.getWidth(); x++) {
			for(int y = 0; y < this.getHeight(); y++) {
				if (!this.getPixel(x, y).equals(p.getPixel(x, y))) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Helper method for loading a picture in the current directory.
	 */
	protected static Picture loadPicture(String pictureName) {
		URL url = Picture.class.getResource(pictureName);
		return new Picture(url.getFile().replaceAll("%20", " "));
	}

	//////////////////////////// Debugging Methods /////////////////////////////////

	/**
	 * Method to print out a table of the intensity for each Pixel in an image
	 */
	public void printLuminosity(){
		int pictureHeight = this.getHeight();
		int pictureWidth = this.getWidth();
		System.out.println("Luminosity:");
		for(int y = 0; y < pictureHeight; y++) {
			System.out.print("[");
			for(int x = 0; x < pictureWidth; x++) {
				System.out.print(this.luminosityOfPixel(x, y) + "\t");
			}
			System.out.println("]");
		}
	}
	/**
	 * Method to print out a table of the energy for each Pixel in an image
	 */
	public void printEnergy(){
		int pictureHeight = this.getHeight();
		int pictureWidth = this.getWidth();
		System.out.println("Energy:");
		for(int y = 0; y < pictureHeight; y++) {
			System.out.print("[");
			for(int x = 0; x < pictureWidth; x++) {
				System.out.print(this.getEnergy(x, y) + "\t");
			}
			System.out.println("]");
		}
	}

	/**
	 * Prints a two dimensional array of ints
	 * @param array
	 */
	public void printArray(int[][] array) {
		int height = array.length;
		int width = array[0].length;
		for (int r = 0; r < width; ++r) {
			for (int c = 0; c < height; ++c) {
				System.out.print(array[c][r] + "\t");
			}
			System.out.println();
		}
	}

	/**
	 * This method can be used like the other Picture methods, to create a
	 * Picture that shows what Pixels are different between two Picture objects.
	 *
	 * @param picture2 is a Picture to compare the current Picture to
	 * @return returns a new Picture with red pixels indicating differences between
	 * 			the two Pictures
	 */
	public Picture showDifferences(Picture picture2){
		Picture newPicture = new Picture(this);

		int pictureHeight = this.getHeight();
		int pictureWidth = this.getWidth();
		Color red = new Color(255, 0, 0);
		for(int x = 0; x < pictureWidth; x++) {
			for(int y = 0; y < pictureHeight; y++) {
				if (!this.getPixel(x, y).equals(picture2.getPixel(x, y))) {
					Pixel p = newPicture.getPixel(x, y);
					p.setColor(red);
				}
			}
		}
		return newPicture;
	}


	//////////////////////////// Grayscale Example /////////////////////////////////
	/*
	 * Each of the methods below is constructive: in other words, each of the
	 * methods below generates a new Picture, without permanently modifying the
	 * original Picture.
	 */

	/**
	 * Returns a new Picture, which is the gray version of the current Picture (this)
	 *
	 * This is an example where all of the pixel-processing occurs within
	 * the nested for loops (over the columns, x, and rows, y).
	 *
	 * @return A new Picture that is the grayscale version of this Picture.
	 */
	public Picture grayscale2() {
		Picture newPicture = new Picture(this);

		int pictureHeight = this.getHeight();
		int pictureWidth = this.getWidth();

		for (int x = 0; x < pictureWidth; x++) {
			for (int y = 0; y < pictureHeight; y++) {

				Pixel currentPixel = newPicture.getPixel(x, y);

				Color c = currentPixel.getColor();
				int redComponent = c.getRed();
				int greenComponent = c.getGreen();
				int blueComponent = c.getBlue();

				int average = (redComponent + greenComponent + blueComponent) / 3;

				currentPixel.setRed(average);
				currentPixel.setGreen(average);
				currentPixel.setBlue(average);
			}
		}
		return newPicture;
	}


	/**
	 * Converts the Picture into grayscale. Since any variation of gray
	 * 	is obtained by setting the red, green, and blue components to the same
	 * 	value, a Picture can be converted into its grayscale component
	 * 	by setting the red, green, and blue components of each pixel in the
	 * 	new picture to the same value: the average of the red, green, and blue
	 * 	components of the same pixel in the original.
	 *
	 * This example shows a more modular approach: grayscale uses a helper
	 * named setPixelToGray; setPixelToGray, in turn, uses the helper averageOfRGB.
	 *
	 * @return A new Picture that is the grayscale version of this Picture.
	 */
	public Picture grayscale() {
		Picture newPicture = new Picture(this);

		int pictureHeight = this.getHeight();
		int pictureWidth = this.getWidth();

		for(int x = 0; x < pictureWidth; x++) {
			for(int y = 0; y < pictureHeight; y++) {
				newPicture.setPixelToGray(x, y);
			}
		}
		return newPicture;
	}

	/**
	 * Helper method for grayscale() to set a pixel at (x, y) to be gray.
	 *
	 * @param x The x-coordinate of the pixel to be set to gray.
	 * @param y The y-coordinate of the pixel to be set to gray.
	 */
	private void setPixelToGray(int x, int y) {
		Pixel currentPixel = this.getPixel(x, y);
		int average = Picture.averageOfRGB(currentPixel.getColor());
		currentPixel.setRed(average);
		currentPixel.setGreen(average);
		currentPixel.setBlue(average);
	}
	/**
	 * Helper method for grayscale() to calculate the
	 * average value of red, green and blue.
	 *
	 * @param c is the Color to be averaged
	 * @return The average of the red, green and blue values of this Color
	 */
	private static int averageOfRGB(Color c) {
		int redComponent = c.getRed();
		int greenComponent = c.getGreen();
		int blueComponent = c.getBlue();

		// this uses integer division, which is what we want here
		// pixels always need to have integer values from 0 to 255 (inclusive)
		// for their red, green, and blue components:
		return (redComponent + greenComponent + blueComponent) / 3;
	}

	//////////////////////////// Change Colors Menu /////////////////////////////////

	//////////////////////////// Negate /////////////////////////////////

	/**
	 * Converts the Picture into its photonegative version. The photonegative
	 * 	version of an image is obtained by setting each of the red, green,
	 * 	and blue components of every pixel to a value that is 255 minus their
	 * 	current values.
	 *
	 * @return A new Picture that is the photonegative version of this Picture.
	 */
	public Picture negate() {
		// TODO: Write negate
		Picture newPicture = new Picture(this);

		for(int x = 0; x < newPicture.getWidth(); x++) {
			for(int y = 0; y < newPicture.getHeight(); y++) {
				Pixel currentPixel = newPicture.getPixel(x, y);

				//sets RGB values to 255 - gotten RGB values of original pixel
				currentPixel.setRed(255 - currentPixel.getRed());
				currentPixel.setGreen(255 - currentPixel.getGreen());
				currentPixel.setBlue(255 - currentPixel.getBlue());
			}
		}
		return newPicture;
	}
	//////////////////////////// Lighten /////////////////////////////////

	/**
	 * Creates an image that is lighter than the original image. The range of
	 * each color component should be between 0 and 255 in the new image. The
	 * alpha value should not be changed.
	 *
	 * @return A new Picture that has every color value of the Picture increased
	 *         by the lightenAmount.
	 */
	public Picture lighten(int lightenAmount) {
		// TODO: Write lighten
		return addRGB(lightenAmount, "all");
	}

	//////////////////////////// Darken /////////////////////////////////

	/**
	 * Creates an image that is darker than the original image.The range of
	 * each color component should be between 0 and 255 in the new image. The
	 * alpha value should not be changed.
	 *
	 * @return A new Picture that has every color value of the Picture decreased
	 *         by the darkenAmount.
	 */
	public Picture darken(int darkenAmount) {
		// TODO: Write darken
		int darkDecrease = darkenAmount * -1;
		return addRGB(darkDecrease, "all");
	}

	/**
	 * Helper method for lighten(), darken(), and add colors to set a pixel at (x, y) to increase/decrease RGB levels.
	 */
	private Picture addRGB(int amount, String color) {
		Picture newPicture = new Picture(this);

		for(int x = 0; x < newPicture.getWidth(); x++) {
			for(int y = 0; y < newPicture.getHeight(); y++) {
				Pixel currentPixel = newPicture.getPixel(x, y);
				if (color.equals("all")){
					currentPixel.setRed(currentPixel.getRed() + amount);
					currentPixel.setGreen(currentPixel.getGreen() + amount);
					currentPixel.setBlue(currentPixel.getBlue() + amount);
				}
				else if (color.equals("r")){
					currentPixel.setRed(currentPixel.getRed() + amount);
				}
				else if (color.equals("g")){
					currentPixel.setGreen(currentPixel.getGreen() + amount);
				}
				else if (color.equals("b")){
					currentPixel.setBlue(currentPixel.getBlue() + amount);
				}
			}
		}

		return newPicture;
	}
	//////////////////////////// Add[Blue,Green,Red] /////////////////////////////////

	/**
	 * Creates an image where the blue value has been increased by amount.The range of
	 * each color component should be between 0 and 255 in the new image. The
	 * alpha value should not be changed.
	 *
	 * @return A new Picture that has every blue value of the Picture increased
	 *         by amount.
	 */
	public Picture addBlue(int amount) {
		// TODO: Write addBlue
		return addRGB(amount, "b");
	}

	/**
	 * Creates an image where the red value has been increased by amount. The range of
	 * each color component should be between 0 and 255 in the new image. The
	 * alpha value should not be changed.
	 *
	 * @return A new Picture that has every red value of the Picture increased
	 *         by amount.
	 */
	public Picture addRed(int amount) {
		// TODO: Write addRed
		return addRGB(amount, "r");
	}

	/**
	 * Creates an image where the green value has been increased by amount. The range of
	 * each color component should be between 0 and 255 in the new image. The
	 * alpha value should not be changed.
	 *
	 * @return A new Picture that has every green value of the Picture increased
	 *         by amount.
	 */
	public Picture addGreen(int amount) {
		// TODO: Write addGreen
		return addRGB(amount, "g");
	}

	//////////////////////////// Rotate Right /////////////////////////////////

	/**
	 * Returns a new picture where the Picture is rotated to the right by 90
	 * degrees. If the picture was originally 50 Pixels by 70 Pixels, the new
	 * Picture should be 70 Pixels by 50 Pixels.
	 *
	 * @return a new Picture rotated right by 90 degrees
	 */
	public Picture rotateRight() {
		// TODO: Write rotateRight
		int newPictureHeight = this.getWidth();
		int newPictureWidth = this.getHeight();
		//constructor takes in order: width and height
		Picture newPicture = new Picture(newPictureWidth,newPictureHeight);

		for(int x = 0; x < newPictureWidth; x++) {
			for(int y = 0; y < newPictureHeight; y++) {
				Pixel currentPixel = newPicture.getPixel(x, y);
				Pixel correspondingPixel = this.getPixel(y, newPicture.getWidth() - 1 - x);
				Color thisColor = correspondingPixel.getColor();
				currentPixel.setColor(thisColor);
			}
		}

		return newPicture;
	}

	//////////////////////////// Seam Carving Section /////////////////////////////////

	//////////////////////////// Luminosity /////////////////////////////////
	/**
	 * Returns a Picture of a version of grayscale using luminosity instead
	 * of a direct average. The Picture should be converted into its luminosity
	 * version by setting the red, green, and blue components of each pixel in
	 * the new picture to the same value: the luminosity of the red, green, and
	 * blue components of the same pixel in the original. Where luminosity =
	 * 0.21 * redness + 0.72 * greenness + 0.07 * blueness
	 *
	 * @return A new Picture that is the luminosity version of this Picture.
	 */
	public Picture luminosity(){
		// TODO: Write luminosity
		Picture newPicture = new Picture(this);

		int pictureHeight = newPicture.getHeight();
		int pictureWidth = newPicture.getWidth();

		for(int x = 0; x < pictureWidth; x++) {
			for (int y = 0; y < pictureHeight; y++) {
				Pixel currentPixel = newPicture.getPixel(x, y);
				currentPixel.setRed(luminosityOfPixel(x,y));
				currentPixel.setGreen(luminosityOfPixel(x,y));
				currentPixel.setBlue(luminosityOfPixel(x,y));
			}
		}

		return newPicture;
	}


	/**
	 * Helper method for luminosity() to calculate the
	 * luminosity of a pixel at (x,y).
	 *
	 * @param x  the x-coordinate of the pixel
	 * @param y  the y-coordinate of the pixel
	 * @return The luminosity of that pixel
	 */
	private int luminosityOfPixel(int x, int y) {
		// TODO: Write luminosityOfPixel
		int r = this.getPixel(x,y).getRed();
		int g = this.getPixel(x,y).getGreen();
		int b = this.getPixel(x,y).getBlue();
		double luminosity = r*0.21 + g*0.72 + b*0.07;
		return (int) luminosity;
	}

	//////////////////////////// Energy /////////////////////////////////

	/**
	 * Returns a Picture into a version of the energy of the Picture
	 *
	 * @return A new Picture that is the energy version of this Picture.
	 */
	public Picture energy(){
		// TODO: Write energy
		Picture newPicture = new Picture(this);
		int pictureHeight = newPicture.getHeight();
		int pictureWidth = newPicture.getWidth();

		for(int x = 0; x < pictureWidth; x++) {
			for (int y = 0; y < pictureHeight; y++) {
				Pixel currentPixel = newPicture.getPixel(x, y);
				setEnergy(x,y,currentPixel);
			}
		}

		return newPicture;
	}

	/**
	 * Helper method for energy() to calculate the
	 * energy of a Pixel.
	 *
	 * @param x is the x value of the Pixel to be evaluated
	 * @param y is the y value of the Pixel to be evaluated
	 * @return The energy of this Pixel
	 */

	private int getEnergy(int x, int y) {
		// TODO: Write getEnergy
		int energy;
		//bottom right pixel
		if (x == this.getWidth() - 1 && y == this.getHeight() - 1){
			energy = Math.abs( luminosityOfPixel(x-1,y) - luminosityOfPixel(x,y) ) + Math.abs( luminosityOfPixel(x,y-1) - luminosityOfPixel(x,y));
		}
		//very right column
		else if (x == this.getWidth() - 1){
			energy = Math.abs( luminosityOfPixel(x-1,y) - luminosityOfPixel(x,y) ) + Math.abs( luminosityOfPixel(x,y+1) - luminosityOfPixel(x,y));
		}
		//very bottom row
		else if (y == this.getHeight() - 1){
			energy = Math.abs( luminosityOfPixel(x+1,y) - luminosityOfPixel(x,y) ) + Math.abs( luminosityOfPixel(x,y-1) - luminosityOfPixel(x,y));
		}
		else{
			energy = Math.abs( luminosityOfPixel(x+1,y) - luminosityOfPixel(x,y) ) + Math.abs( luminosityOfPixel(x,y+1) - luminosityOfPixel(x,y));
		}
		return energy;
	}
	private void setEnergy(int x, int y, Pixel pixel) {
		pixel.setRed(getEnergy(x, y));
		pixel.setGreen(getEnergy(x, y));
		pixel.setBlue(getEnergy(x, y));
	}


	//////////////////////////// Compute Seam /////////////////////////////////

	/**
	 * private helper method computeSeam returns an int array with the
	 * x-coordinates (columns) of the lowest-energy seam running from the top
	 * row to the bottom row.
	 *
	 * See the course assignment for additional details.
	 */
	@SuppressWarnings("unused")
	public int[] computeSeam() {
		// TODO: Write computeSeam
		int[][] table = new int[this.getWidth()][this.getHeight()];
		for (int y = 0; y < this.getHeight(); y++) {
			for (int x = 0; x < this.getWidth(); x++) {
				table[x][y] = getEnergy(x, y);
			}
		}

		int[][] parent = new int[this.getWidth()][this.getHeight()];

		for (int y = 1; y < this.getHeight(); y++){
			for (int x = 0; x < this.getWidth(); x++){
				//if at the left boundary
				if (x == 0){
					int top = table[x][y-1];
					int topRight = table[x+1][y-1];
					int min = Math.min(top, topRight);
					table[x][y] += min;
					//if top and topRight are the same, prioritize top, then topRight
					if (min == top){
						parent[x][y] = x;
					}
					else{
						parent[x][y] = x+1;
					}
				}
				//if at right boundary
				else if (x == this.getWidth() - 1){
					int topLeft = table[x-1][y-1];
					int top = table[x][y-1];
					int min = Math.min(top,topLeft);
					table[x][y] += min;
					//if top and topLeft are the same, prioritize top, then topLeft
					if (min == top){
						parent[x][y] = x;
					}
					else{
						parent[x][y] = x-1;
					}
				}
				//else if it's inbetween
				else{
					int topLeft = table[x-1][y-1];
					int top = table[x][y-1];
					int topRight = table[x+1][y-1];
					int min1 = Math.min(top, topRight);
					int min2 = Math.min(min1, topLeft);
					table[x][y] += min2;
					//if top, topLeft and topRight are the same, prioritize top, then topLeft, lastly topRight
					if (min2 == top){
						parent[x][y] = x;
					}
					else if (min2 == topLeft){
						parent[x][y] = x-1;
					}
					else{
						parent[x][y] = x+1;
					}
				}
			}
		}

		//finding the smallest table value on bottom row
		int min = table[0][this.getHeight() - 1];
		//currentColumn is the x value at the last row in table, with the minimum value
		int currentColumn = 0;
		for (int x = 0; x < this.getWidth(); x++){
			if (table[x][this.getHeight() - 1] < min){
				min = table[x][this.getHeight() - 1];
				currentColumn = x;
			}
		}

		//working way up and collecting pixels
		int[] arraySeam = new int[this.getHeight()];
		arraySeam[this.getHeight() - 1] = currentColumn;
		int parentVal = currentColumn;
		for (int i = this.getHeight() - 1; i > 0; i--){
			arraySeam[i-1] = parent[parentVal][i];
			parentVal = parent[parentVal][i];
		}
		return arraySeam;
	}

	//////////////////////////// Show Seam /////////////////////////////////

	/**
	 * Returns a new image, with the lowest cost seam shown in red. The lowest
	 * cost seam is calculated by calling computeSeam()
	 *
	 * @return a new Picture
	 */
	public Picture showSeam(){
		// TODO: Write showSeam
		int[] seam = this.computeSeam();
		Picture newPicture = new Picture(this);
		for(int y = 0; y < seam.length; y++){
			int x = seam[y];
			//currentPixel has the x-value (column) taken from computeSeam
			Pixel currentPixel = newPicture.getPixel(x, y);
			currentPixel.setColor(Color.red);
		}
		return newPicture;
	}

	//////////////////////////// Carving (2 methods) /////////////////////////////////

	/**
	 * Returns a new picture, where the seam identified by calling computeSeam() is
	 * removed. The resulting image should be the same height as the original
	 * but have a width that is one smaller than the original.
	 */
	public Picture carve(){
		// TODO: Write carve
		int[] seam = this.computeSeam();
		//after carved 1 seam, width should be less by 1, but height stays the same
		int newWidth = this.getWidth() - 1;
		Picture newPicture = new Picture(newWidth, this.getHeight());

		for (int y = 0; y < this.getHeight(); y++){
			int seamColumn = seam[y];
			//everything before the seam
			for (int x = 0; x < seamColumn; x++){
				Pixel currentPixel = newPicture.getPixel(x, y);
				Pixel originalPixel = this.getPixel(x, y);
				Color thisColor = originalPixel.getColor();
				currentPixel.setColor(thisColor);
			}
			//everything after the seam shift to the left, aka x - 1
			for (int x = seamColumn + 1; x < this.getWidth(); x++){
				Pixel currentPixel = newPicture.getPixel(x - 1, y);
				Pixel originalPixel = this.getPixel(x, y);
				Color thisColor = originalPixel.getColor();
				currentPixel.setColor(thisColor);
			}
		}
		return newPicture;
	}

	/**
	 * This returns a new Picture that has a number of seams removed.
	 *
	 * If the input is greater than the width of the Picture, first print an error using
	 * System.err instead of System.out, then return null. Here is the error message:
	 *
	 * System.err.println("Cannot call carveMany with argument " + numSeams + " on image of width " + this.getWidth());
	 *
	 * @param numSeams is the number of times that carve should be called
	 * @return a new picture with numSeams removed
	 */
	public Picture carveMany(int numSeams){
		// TODO: Write carveMany
		if (numSeams > this.getWidth()){
			System.err.println("Cannot call carveMany with argument " + numSeams + " on image of width " + this.getWidth());
			return null;
		}

		Picture newPicture = new Picture(this);
		for (int i = 0; i < numSeams; i++){
			newPicture = newPicture.carve();
		}
		return newPicture;
	}


	//////////////////////////// Extension /////////////////////////////////

	/**
	 * @param x x-coordinate of the pixel currently selected.
	 * @param y y-coordinate of the pixel currently selected.
	 * @param background Picture to use as the background.
	 * @param threshold Threshold within which to replace pixels.
	 *
	 * @return A new Picture where all the pixels in the original Picture,
	 * 	which differ from the currently selected pixel within the provided
	 * 	threshold (in terms of color distance), are replaced with the
	 * 	corresponding pixels in the background picture provided.
	 *
	 * 	If the two Pictures are of different dimensions, the new Picture will
	 * 	have length equal to the smallest of the two Pictures being combined,
	 * 	and height equal to the smallest of the two Pictures being combined.
	 * 	In this case, the Pictures are combined as if they were aligned at
	 * 	the top left corner (0, 0).
	 */
	public Picture chromaKey(int xOfSelected, int yOfSelected, Picture background, int threshold) {
		// TODO: Write chromaKey (Extension)
		int newWidth = Math.min(this.getWidth(),background.getWidth());
		int newHeight = Math.min(this.getHeight(),background.getHeight());
		Picture newPicture = new Picture(newWidth, newHeight);

		Pixel selected = this.getPixel(xOfSelected,yOfSelected);

		for(int x = 0; x < newPicture.getWidth(); x++) {
			for(int y = 0; y < newPicture.getHeight(); y++) {
				Pixel currentPixel = newPicture.getPixel(x, y);
				Pixel thisPixel = this.getPixel(x, y);
				Pixel bgPixel = background.getPixel(x, y);

				Color thisColor = thisPixel.getColor();
				double distance = selected.colorDistance(thisColor);

				//if within threshold, replace currentPixel's color with bgPixel
				if (distance < threshold){
					currentPixel.setColor(bgPixel.getColor());
				}
				else{
					currentPixel.setColor(thisPixel.getColor());
				}
			}
		}
		return newPicture;
	}

	//////////////////////////// Flip /////////////////////////////////

	/**
	 * Flips this Picture about the given axis. The axis can be one of
	 * 	four static integer constants:
	 *
	 * 	(a) Picture.HORIZONTAL: The picture should be flipped about
	 * 		a horizontal axis passing through the center of the picture.
	 * 	(b) Picture.VERTICAL: The picture should be flipped about
	 * 		a vertical axis passing through the center of the picture.
	 * 	(c) Picture.FORWARD_DIAGONAL: The picture should be flipped about
	 * 		the line that passes through the southwest corner of the
	 * 		picture and that extends at 45deg. to the northeast
	 * 	(d) Picture.BACKWARD_DIAGONAL: The picture should be flipped about
	 * 		an axis that passes through the north-west corner and extends
	 * 		at a 45deg angle to the southeast
	 *
	 * If the input is not one of these static variables, print an error using
	 * System.err (instead of System.out):
	 * 				System.err.println("Invalid flip request");
	 *   ... and then return null.
	 *
	 *
	 * @param axis Axis about which to flip the Picture provided.
	 *
	 * @return A new Picture flipped about the axis provided.
	 */
	public Picture flip(int axis) {
		// TODO: Write flip (Extension)
		return null;
	}

	//////////////////////////// Show Edges /////////////////////////////////

	/**
	 * @param threshold
	 *            Threshold to use to determine the presence of edges.
	 *
	 * @return A new Picture that contains only the edges of this Picture. For
	 *         each pixel, we separately consider the color distance between
	 *         that pixel and the one pixel to its left, and also the color
	 *         distance between that pixel and the one pixel to the north, where
	 *         applicable. As an example, we would compare the pixel at (3, 4)
	 *         with the pixels at (3, 3) and the pixels at (2, 4). Also, since
	 *         the pixel at (0, 4) only has a pixel to its north, we would only
	 *         compare it to that pixel. If either of the color distances is
	 *         larger than the provided color threshold, it is set to black
	 *         (with an alpha of 255); otherwise, the pixel is set to white
	 *         (with an alpha of 255). The pixel at (0, 0) will always be set to
	 *         white.
	 */
	public Picture showEdges(int threshold) {
		// TODO: Write show edges (Extension)
		Picture newPicture = new Picture(this);
		Pixel firstPixel = newPicture.getPixel(0,0);
		firstPixel.setColor(Color.white);
		firstPixel.setAlpha(255);
		//left = x - 1, north = y - 1

		for (int x = 0; x < newPicture.getWidth(); x++){
			for (int y = 0; y < newPicture.getHeight(); y++){

				//everything on left boundary, not including pixel at (0,0)
				if (x == 0 && y > 0){
					Pixel currentPixel = newPicture.getPixel(0,y);
					Pixel thisPixel = this.getPixel(0, y);
					Pixel northPixel = this.getPixel(0,y-1);

					distanceCalculate(currentPixel, thisPixel, northPixel, northPixel, threshold);
				}
				//everything on north boundary, not including pixel at (0,0)
				else if (y == 0 && x > 0){
					Pixel currentPixel = newPicture.getPixel(x,0);
					Pixel thisPixel = this.getPixel(x, 0);
					Pixel leftPixel = this.getPixel(x-1,0);

					distanceCalculate(currentPixel, thisPixel, leftPixel, leftPixel, threshold);
				}
				//everything not on any boundary
				else if (x > 0 && y > 0){
					Pixel currentPixel = newPicture.getPixel(x,y);
					Pixel thisPixel = this.getPixel(x, y);
					Pixel leftPixel = this.getPixel(x-1, y);
					Pixel northPixel = this.getPixel(x,y-1);
					distanceCalculate(currentPixel, thisPixel, leftPixel, northPixel, threshold);
				}
			}
		}
		return newPicture;
	}

	//this is a helper method for showEdges() which calculates distance in Color for this Pixel
	private void distanceCalculate(Pixel currentPixel, Pixel thisPixel, Pixel otherPixel, Pixel otherPixel2, int threshold){
		Color otherPixelColor = otherPixel.getColor();
		double distance = thisPixel.colorDistance(otherPixelColor);
		int intDistance = (int) distance;

		Color otherPixel2Color = otherPixel2.getColor();
		double distance2 = thisPixel.colorDistance(otherPixel2Color);
		int intDistance2 = (int) distance2;

		//this OR part is only for pixels not on the boundary. If on boundary, just use the same otherPixel
		if (intDistance > threshold || intDistance2 > threshold){
			currentPixel.setColor(Color.black);
			currentPixel.setAlpha(255);
		}
		else {
			currentPixel.setColor(Color.white);
			currentPixel.setAlpha(255);
		}
	}
	//////////////////////////////// Blur //////////////////////////////////

	/**
	 * Blurs this Picture. To achieve this, the algorithm takes a pixel, and
	 * sets it to the average value of all the pixels in a square of side (2 *
	 * blurThreshold) + 1, centered at that pixel. For example, if blurThreshold
	 * is 2, and the current pixel is at location (8, 10), then we will consider
	 * the pixels in a 5 by 5 square that has corners at pixels (6, 8), (10, 8),
	 * (6, 12), and (10, 12). If there are not enough pixels available -- if the
	 * pixel is at the edge, for example, or if the threshold is larger than the
	 * image -- then the missing pixels are ignored, and the average is taken
	 * only of the pixels available.
	 *
	 * The red, blue, green and alpha values should each be averaged separately.
	 *
	 * @param blurThreshold
	 *            Size of the blurring square around the pixel.
	 *
	 * @return A new Picture that is the blurred version of this Picture, using
	 *         a blurring square of size (2 * threshold) + 1.
	 */
	public Picture blur(int blurThreshold) {
		// TODO: Write blur (Extension)
		return null;
	}

	//////////////////////////////// Paint Bucket //////////////////////////////////

	/**
	 * @param x x-coordinate of the pixel currently selected.
	 * @param y y-coordinate of the pixel currently selected.
	 * @param threshold Threshold within which to delete pixels.
	 * @param newColor New color to color pixels.
	 *
	 * @return A new Picture where all the pixels connected to the currently
	 * 	selected pixel, and which differ from the selected pixel within the
	 * 	provided threshold (in terms of color distance), are colored with
	 * 	the new color provided.
	 */
	public Picture paintBucket(int x, int y, int threshold, Color newColor) {
		// TODO: Write paintBucket (Extension)
		Picture newPicture = new Picture(this);

		//compare selectedPixel's color with nearby Pixel's color using colorDistance, see if
		//within threshold or not
		Pixel selectedPixel = this.getPixel(x,y);

		floodFill(newPicture, selectedPixel, x, y, threshold, newColor);
		floodFill2(newPicture, selectedPixel, x, y-1, threshold, newColor);
		return newPicture;
	}

	//helper method, the floodFill algorithm
	private void floodFill(Picture picture, Pixel selectedPixel, int x, int y, int threshold, Color newColor){

		//quit if off the Picture:
		if (x < 0 || x >= picture.getWidth() || y < 0 || y >= picture.getHeight()){
			return;
		}

		//quit if visited the Pixel already, Pixel visited if already set to newColor:
		if (picture.getPixel(x,y).getColor() == newColor){
			return;
		}

		//quit if hit a Pixel above threshold:
		if (selectedPixel.colorDistance(picture.getPixel(x,y).getColor()) >= threshold){
			return;
		}

		//change color if it is not above threshold
		if (selectedPixel.colorDistance(picture.getPixel(x,y).getColor()) < threshold){
			picture.getPixel(x,y).setColor(newColor);
		}

		//recursively fill in all directions
		floodFill(picture, selectedPixel,x+1, y, threshold, newColor);
		floodFill(picture, selectedPixel, x, y+1, threshold, newColor);
		floodFill(picture, selectedPixel, x-1, y, threshold, newColor);
	}

	private void floodFill2(Picture picture, Pixel selectedPixel, int x, int y, int threshold, Color newColor){

		//quit if off the Picture:
		if (x < 0 || x >= picture.getWidth() || y < 0 || y >= picture.getHeight()){
			return;
		}

		//quit if visited the Pixel already, Pixel visited if already set to newColor:
		if (picture.getPixel(x,y).getColor() == newColor){
			return;
		}

		//quit if hit a Pixel above threshold:
		if (selectedPixel.colorDistance(picture.getPixel(x,y).getColor()) >= threshold){
			return;
		}

		//change color if it is not above threshold
		if (selectedPixel.colorDistance(picture.getPixel(x,y).getColor()) < threshold){
			picture.getPixel(x,y).setColor(newColor);
		}

		//recursively fill in all directions
		floodFill2(picture, selectedPixel, x, y-1, threshold, newColor);
		floodFill2(picture, selectedPixel, x+1, y, threshold, newColor);
		floodFill2(picture, selectedPixel, x-1, y, threshold, newColor);
	}

	//////////////////////////////// Main Method //////////////////////////////////

	public static void main(String[] args) {
		/**Test functionality
		 * */
		Picture pic = Picture.loadPicture("Maria1.bmp");
		pic.explore();

		/**Testing getEnergy in Crit B specification 5
		 * */

		Picture picMicro = Picture.loadPicture("Micro.bmp");
		int energyTopLeft = picMicro.getEnergy(0, 0);
		int energyTopRight = picMicro.getEnergy(picMicro.getWidth() - 1, 0);
		int energyBottomRight = picMicro.getEnergy(picMicro.getWidth() - 1, picMicro.getHeight() - 1);
		int energyBottomLeft = picMicro.getEnergy(0, picMicro.getHeight() - 1);
		System.out.println(energyTopLeft);
		System.out.println(energyTopRight);
		System.out.println(energyBottomLeft);
		System.out.println(energyBottomRight);
		picMicro.printEnergy();



		System.out.println();
		/**Testing table[][] in Crit B specification 7
		 * */

		int table[][] =  {{1,2,3}, {8,6,4}, {5,6,5}};

		//copy paste table[][] code:
		for (int y = 1; y < table.length; y++){
			for (int x = 0; x < table[0].length; x++){
				//if at the left boundary
				if (x == 0){
					int top = table[y-1][x];
					int topRight = table[y-1][x+1];
					int min = Math.min(top, topRight);
					table[y][x] += min;
				}
				//if at right boundary
				else if (x == table[0].length - 1){
					int topLeft = table[y-1][x-1];
					int top = table[y-1][x];
					int min = Math.min(top,topLeft);
					table[y][x] += min;
				}
				//else if it's inbetween
				else{
					int topLeft = table[y-1][x-1];
					int top = table[y-1][x];
					int topRight = table[y-1][x+1];
					int min1 = Math.min(top, topRight);
					int min2 = Math.min(min1, topLeft);
					table[y][x] += min2;
				}
			}
		}

		//Print out table[][] values:
		for (int r = 0; r < table.length; ++r) {
			for (int c = 0; c < table[0].length; ++c) {
				System.out.print(table[r][c] + "\t");
			}
			System.out.println();
		}



		System.out.println();
		/**Testing parent[][] in Crit B specification 8
		 * */

		int parent[][] = new int[3][3];

		//copy paste parent[][] code:
		for (int y = 1; y < table.length; y++){
			for (int x = 0; x < table[0].length; x++){
				//if at the left boundary
				if (x == 0){
					int top = table[y-1][x];
					int topRight = table[y-1][x+1];
					int min = Math.min(top, topRight);
					//if top and topRight are the same, prioritize top, then topRight
					if (min == top){
						parent[y][x] = x;
					}
					else{
						parent[y][x] = x+1;
					}
				}
				//if at right boundary
				else if (x == table[0].length - 1){
					int topLeft = table[y-1][x-1];
					int top = table[y-1][x];
					int min = Math.min(top,topLeft);
//if top and topLeft are the same, prioritize top, then topLeft
					if (min == top){
						parent[y][x] = x;
					}
					else{
						parent[y][x] = x-1;
					}
				}
				//else if it's inbetween
				else{
					int topLeft = table[y-1][x-1];
					int top = table[y-1][x];
					int topRight = table[y-1][x+1];
					int min1 = Math.min(top, topRight);
					int min2 = Math.min(min1, topLeft);
					//if top, topLeft and topRight are the same, prioritize top, then topLeft, lastly topRight
					if (min2 == top){
						parent[y][x] = x;
					}
					else if (min2 == topLeft){
						parent[y][x] = x-1;
					}
					else{
						parent[y][x] = x+1;
					}
				}
			}
		}

		//Print out parent][][] values:
		for (int r = 0; r < parent.length; ++r) {
			for (int c = 0; c < parent[0].length; ++c) {
				System.out.print(parent[r][c] + "\t");
			}
			System.out.println();
		}
	}

} // End of Picture class
