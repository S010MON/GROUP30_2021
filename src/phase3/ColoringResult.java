package phase3;

public class ColoringResult {

    private boolean isExact;
    private int chromaticNumber;
    private boolean hasSolution;
    private int[] solution = new int[0];
    private Boolean isSolved;

    public ColoringResult(int chromaticNumber, boolean isExact, int[] solution, Boolean isSolved) {
        this.isExact = isExact;
        this.chromaticNumber = chromaticNumber;
        this.solution = solution;
        this.isSolved = isSolved;
        hasSolution = true;
    }

    public ColoringResult(int chromaticNumber, int[] solution, Boolean isSolved) {
        this.chromaticNumber = chromaticNumber;
        this.solution = solution;
        this.isSolved = isSolved;
        hasSolution = true;
    }

    public ColoringResult(int chromaticNumber, boolean isExact, Boolean isSolved) {
        this.isExact = isExact;
        this.chromaticNumber = chromaticNumber;
        this.isSolved = isSolved;
        hasSolution = false;
    }

    public ColoringResult(int chromaticNumber, Boolean isSolved) {
        this.chromaticNumber = chromaticNumber;
        this.isSolved = isSolved;
        hasSolution = false;
    }

    public int getChromaticNumber() {
        return chromaticNumber;
    }

    public int[] getSolution() {
        return solution;
    }

    public boolean isExact() {
        return isExact;
    }

    public boolean hasSolution() {
        return hasSolution;
    }

    public Boolean isSolved() {
        return isSolved;
    }

}
