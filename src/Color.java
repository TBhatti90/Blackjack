public enum Color {
    BLACK("Black"),
    RED("Red");

    private final String colorName;

    Color(String colorName) {
        this.colorName = colorName;
    }

    @Override
    public String toString() {
        return colorName;
    }
}
