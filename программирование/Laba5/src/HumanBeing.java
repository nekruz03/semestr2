public class HumanBeing {
    public  int kol = 0;
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private boolean realHero;
    private Boolean hasToothpick; //Поле может быть null
    private long impactSpeed;
    private WeaponType weaponType; //Поле не может быть null
    private Mood mood; //Поле может быть null
    private Car car; //Поле может быть null

    //конструктор для класса HumanBeing
    public  HumanBeing(String name, Coordinates coordinates, boolean realxHero, Boolean hasToothpick, long impactSpeed, WeaponType weaponType, Mood mood, Car car ){
        setId(kol++);
        setName(name);
        setCoordinates(coordinates);
        setRealHero(realHero);
        setHasToothpick(hasToothpick);
        setImpactSpeed(impactSpeed);
        setWeaponType(weaponType);
        setMood(mood);
        setCar(car);
        // надо написать seterre
    }
    //setter для id

    public void setId(int id) {
        this.id = id;
    }

    // setter для поля String name
    public void setName(String name) throws NullPointerException {
     if(name != null && !name.isEmpty()){
         this.name = name;
     }
     else  throw new NullPointerException();
    }

    public String getName() {
        return name;
    }
    //setter для поля Coordinates

    public void setCoordinates(Coordinates coordinates) throws NullPointerException {
        if(coordinates != null){
            this.coordinates = coordinates;
        }
        else  throw  new NullPointerException();
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
    // seter для realHero

    public void setRealHero(boolean realHero) {
        this.realHero = realHero;
    }
    public boolean isRealHero() {
        return realHero;
    }
    // setter  для поля hasToothpick

    public void setHasToothpick(Boolean hasToothpick) throws NullPointerException {
        if (hasToothpick != null){
            this.hasToothpick = hasToothpick;
        }
        else throw new NullPointerException();
    }
    public Boolean getHasToothpick() {
        return hasToothpick;
    }
    //setter для impactSpeed

    public void setImpactSpeed(long impactSpeed){
        this.impactSpeed  = impactSpeed;
    }

    public long getImpactSpeed() {
        return impactSpeed;
    }
    //setter для WeaponType

    public void setWeaponType(WeaponType weaponType) throws NullPointerException {
        if(weaponType != null){
            this.weaponType = weaponType;
        }
        else  throw new NullPointerException();
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }
    //setter для Mood

    public void setMood(Mood mood) {
            this.mood = mood;
    }
    public Mood getMood() {
        return mood;
    }
    //setter для Сar
    public  void setCar(Car car) {
        this.car = car;
    }
    public Car getCar() {
        return car;
    }

    @Override
    public String toString() {
        return "HumanBeing{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", realHero=" + realHero +
                ", hasToothpick=" + hasToothpick +
                ", impactSpeed=" + impactSpeed +
                ", weaponType=" + weaponType +
                ", mood=" + mood +
                ", car=" + car +
                '}';
    }

}
