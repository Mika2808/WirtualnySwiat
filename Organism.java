import java.util.Random;

public abstract class Organism {
    private int strength;
    private int lastMove;
    private int initiative;
    private final Polozenie cords = new Polozenie();
    private String name;
    private boolean move;
    World world;
    Organism prev;
    Organism next;


    //FUNCTION TO OVERRIDE
    public abstract String getName();
    public abstract void Action();
    public abstract void Collision(Organism attacked);
    public abstract void Drawing();

    //SETTERS
    void SetCords(int x, int y){
        cords.x=x;
        cords.y=y;
    }
    void SetStrength(int x){
        strength=x;
    }

    void SetInitiative(int x){
        initiative=x;
    }
    void SetMove(boolean x){
        move=x;
    }
    void SetLastMove(int x){
        lastMove = x;
    }

    //GETTERS
    int GetStrength(){
        return strength;
    }
    int GetInitiative(){
        return initiative;
    }
    Polozenie GetCords(){
        return cords;
    }
    boolean GetMove(){
        return move;
    }
    int GetLastMove(){
        return lastMove;
    }

    public int EmptyPLace() {

        //AROUND ORGANISMS
        Organism up_organism = null;
        Organism down_organism = null;
        Organism left_organism = null;
        Organism right_organism = null;
        Polozenie organism_cords = GetCords();

        Organism tmp_organism = world.GetOrganisms().GetHead();
        while(tmp_organism!=null){
            Polozenie tmp_cords = tmp_organism.GetCords();

            //ORGANISM FROM TOP
            if ((tmp_cords.x == organism_cords.x) && (tmp_cords.y == organism_cords.y - 1)) {
                up_organism = tmp_organism;
            }
            //ORGANISM FROM DOWN
            else if ((tmp_cords.x == organism_cords.x) && (tmp_cords.y == organism_cords.y + 1)) {
                down_organism = tmp_organism;
            }
            //ORGANISM FROM LEFT
            else if ((tmp_cords.x == organism_cords.x - 1) && (tmp_cords.y == organism_cords.y)) {
                left_organism = tmp_organism;
            }
            //ORGANISM FROM right
            else if ((tmp_cords.x == organism_cords.x + 1) && (tmp_cords.y == organism_cords.y)) {
                right_organism = tmp_organism;
            }
            tmp_organism=tmp_organism.next;
        }

        //DRAWING RANDOM PLACE FROM EMPTY ONES
        //l-LEFT r-RIGHT u-UP d-DOWN n-NOPE
        char[] randomise = {'n', 'n', 'n', 'n'};

        int index = 0;

        //FROM LEFT SIDE
        if ((left_organism == null) && (organism_cords.x > 1)) {
            randomise[index] = 'l';
            index++;
        }

        //FROM RIGHT SIDE
        if ((right_organism == null) && (organism_cords.x < world.GetWidth())) {
            randomise[index] = 'r';
            index++;
        }

        //FROM UP
        if ((up_organism == null) && (organism_cords.y > 1)) {
            randomise[index] = 'u';
            index++;
        }

        //FROM DOWN
        if ((down_organism == null) && (organism_cords.y < world.GetHeight())) {
            randomise[index] = 'd';
            index++;
        }

        if (index != 0) {
            Random random = new Random();
            int number = random.nextInt(index);

            if (randomise[number] == 'l') {
                return Stale.LEFT;
            } else if (randomise[number] == 'r') {
                return Stale.RIGHT;
            } else if (randomise[number] == 'u') {
                return Stale.UP;
            } else if (randomise[number] == 'd') {
                return Stale.DOWN;
            }
        }

        return -1;
    }
    public void New(String text, World reference, int x, int y) {
        if (text.equals("FOX")) {
            world.GetOrganisms().add(new Fox(reference, x, y));
        }
        else if (text.equals("ANTELOPE")) {
            world.GetOrganisms().add(new Antelope(reference, x, y));
        }
        else if (text.equals("WOLF")) {
            world.GetOrganisms().add(new Wolf(reference, x, y));
        }
        else if (text.equals("TURTLE")) {
            world.GetOrganisms().add(new Turtle(reference, x, y));
        }
        else if (text.equals("SHEEP")) {
            world.GetOrganisms().add(new Sheep(reference, x, y));
        }
        else if (text.equals("GRASS")) {
            world.GetOrganisms().add(new Grass(reference, x, y));
        }
        else if (text.equals("DANDELION")) {
            world.GetOrganisms().add(new Dandelion(reference, x, y));
        }
        else if (text.equals("GUARANA")) {
            world.GetOrganisms().add(new Guarana(reference,x,y));
        }
        else if (text.equals("BILBERRY")) {
            world.GetOrganisms().add(new Bilberry(reference,x,y));
        }
        else if (text.equals("HOGWEED")) {
            world.GetOrganisms().add(new Hogweed(reference,x,y));
        }
        else if (text.equals("CYBER-SHEEP")) {
            world.GetOrganisms().add(new Hogweed(reference,x,y));
        }
    }
    void UndoMove(){
        int tmp_last_move = GetLastMove();
        Polozenie tmp_cords = GetCords();

        if (tmp_last_move == Stale.LEFT) {
            SetCords(tmp_cords.x + 1, tmp_cords.y);
        }
        else if (tmp_last_move == Stale.RIGHT) {
            SetCords(tmp_cords.x - 1, tmp_cords.y);
        }
        else if (tmp_last_move == Stale.UP) {
            SetCords(tmp_cords.x, tmp_cords.y + 1);
        }
        else if (tmp_last_move == Stale.DOWN)
        {
            SetCords(tmp_cords.x, tmp_cords.y - 1);
        }
        SetLastMove(0);
    }
}
