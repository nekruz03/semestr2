public class Coordinates {
    private long x; //Значение поля должно быть больше -710
    private Integer y; //Максимальное значение поля: 407, Поле не может быть null\
    public final  long max_x = -710;
    public  final  Integer max_y = 407;
    public Coordinates(long x, Integer y){
        setX(x);
        setY(y);
    }
    public void setX(long x)  throws NullPointerException {
        if(x>=-710)
        this.x = x;
    }

    public void setY(Integer y) throws NullPointerException {
        if(y <= 407 && y!= null ){
            this.y = y;
        }
    }

    public long getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                ", max_x=" + max_x +
                ", max_y=" + max_y +
                '}';
    }
}