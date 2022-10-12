package Constants;

/**
 * This class represents the text sizes used for the NUsurf website. 
 * @author stefanieim
 */
public class TextSize {
  private float canvasH;
  public float xs;
  public float s;
  public float sm;
  public float m;
  public float l;
  public float xl;
  public float btnS;
  public float btnM;

  /**
   * Constructs a TextSide object containing the appropriate textSize dimensions, depending on the given height.
   * The given height should represent the height of the canvas dimensions.
   * @param h the given height of the canvas to calculate text sizes for
   */
  public TextSize(float h) {
    this.canvasH = h;
    this.xs = (canvasH/60); //12pt
    this.s  = (canvasH/45); //16pt
    this.sm = (canvasH/40); //18pt
    this.m = (canvasH/36); //20pt
    this.l = (canvasH/30); //24pt
    this.xl= (canvasH/18);  //40pt
    this.btnS = xs;
    this.btnM = s;
  }
}
