import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class ListOfOrganisms {
    private Organism head;
    private Organism tail;
    private int NumberOfOrganism;
    public Organism GetHead(){
       return head;
    }
   ListOfOrganisms(){
       head = null;
       tail= null;
   }
    public void add(Organism organism) {
        if (head == null) {
            head = organism;
            tail = organism;
            SetNumberOfOrganism(0);
        }
        else {
            Organism tmp_organism = head;
            int initiative = organism.GetInitiative();
            if (initiative == 0) {
                tail.next = organism;
                organism.prev = tail;
                tail = organism;
            }
            else {
                while (tmp_organism != null) {
                    if (initiative > tmp_organism.GetInitiative()) {
                        if (tmp_organism == head) {
                            tmp_organism.prev = organism;
                            organism.next = tmp_organism;
                            head = organism;
                            break;
                        }
                        else {
                            organism.next = tmp_organism;
                            organism.prev = tmp_organism.prev;
                            tmp_organism.prev.next = organism;
                            tmp_organism.prev = organism;
                            break;
                        }
                    }
                    else if (tmp_organism == tail) {
                        if (tmp_organism.GetInitiative() < initiative) {
                            organism.next = tail;
                            organism.prev = tail.prev;
                            tail.prev.next = organism;
                            tail.prev = organism;
                            break;
                        }
                        else {
                            tail.next = organism;
                            organism.prev = tail;
                            tail = organism;
                            break;
                        }
                    }
                    tmp_organism = tmp_organism.next;
                }
            }
        }
        NumberOfOrganism += 1;
    }

    public void Start(World world) {
        int height = world.GetHeight();
        int width = world.GetWidth();
        Random random = new Random();

        //add(new Fox(world, random.nextInt(width) + 1, random.nextInt(height) + 1));
        add(new Human(world, random.nextInt(width) + 1, random.nextInt(height) + 1));
       // add(new Guarana(world, random.nextInt(width) + 1, random.nextInt(height) + 1));
        //add(new Turtle(world, random.nextInt(width) + 1, random.nextInt(height) + 1));
        //add(new Antelope(world, random.nextInt(width) + 1, random.nextInt(height) + 1));
        //add(new CyberSheep(world, random.nextInt(width) + 1, random.nextInt(height) + 1));
        //add(new Hogweed(world, random.nextInt(width) + 1, random.nextInt(height) + 1));
        //add(new Hogweed(world, random.nextInt(width) + 1, random.nextInt(height) + 1));
        //if(height*width>10){
           // add(new Hogweed(world, random.nextInt(width) + 1, random.nextInt(height) + 1));
            //add(new Wolf(world, random.nextInt(width) + 1, random.nextInt(height) + 1));
          //  add(new Sheep(world, random.nextInt(width) + 1, random.nextInt(height) + 1));
          //  add(new Guarana(world, random.nextInt(width) + 1, random.nextInt(height) + 1));
          //  add(new Dandelion(world, random.nextInt(width) + 1, random.nextInt(height) + 1));
          //  add(new Guarana(world, random.nextInt(width) + 1, random.nextInt(height) + 1));
        //}

        Organism tmp_organism = head;
        while (tmp_organism != null) {
            tmp_organism.SetMove(false);
            tmp_organism = tmp_organism.next;
        }
        DrawOrganism();
    }
    public void remove(Organism organism_dead,Organism organism_stay) {

        if ((!organism_dead.getName().equals("HUMAN")) || ((organism_dead.getName().equals("HUMAN"))
                && (!organism_dead.world.GetSuperpower()))) {

            Organism tmp_organism = head;
            while (tmp_organism != null) {

                if (tmp_organism == organism_dead) {
                    if (tmp_organism == tail) {
                        tmp_organism.prev.next = null;
                        tail = tmp_organism.prev;
                        break;
                    }
                    else if (tmp_organism == head) {
                        tmp_organism.next.prev = null;
                        head = tmp_organism.next;
                        break;
                    }
                    else {
                        tmp_organism.prev.next = tmp_organism.next;
                        tmp_organism.next.prev = tmp_organism.prev;
                        break;
                    }
                }
                tmp_organism = tmp_organism.next;
            }
            if(organism_dead.GetInitiative() != 0){
                organism_dead.world.TextArea.append("("+organism_stay.GetCords().x+","+organism_stay.GetCords().y+ ") "
                        + organism_dead.getName() + " GOT KILLED BY "+ organism_stay.getName() + "\n");
            }
            else
            {
                organism_dead.world.TextArea.append("("+organism_stay.GetCords().x+","+organism_stay.GetCords().y+ ") "
                        + organism_dead.getName() + " GOT EATEN BY "+ organism_stay.getName() + "\n");
            }
            NumberOfOrganism -= 1;
        }
    }
    public void DrawOrganism() {
        Organism tmp_organism = head;
        while (tmp_organism != null) {
            tmp_organism.Drawing();
            tmp_organism = tmp_organism.next;
        }
    }
    public void Move() {
        //ALL ANIMALS ARE MAKING A MOVE
        Organism tmp_organism = head;
        while (tmp_organism != null) {
            if (!tmp_organism.GetMove()) {
                tmp_organism.Action();

                //ONLY ANIMALS CAN CAUSE COLLISION
                if(tmp_organism.GetInitiative()!=0) Collision(tmp_organism);
                tmp_organism.SetMove(true);
                //Opis();
            }
            tmp_organism = tmp_organism.next;
        }

        tmp_organism = head;

        while (tmp_organism != null) {
            tmp_organism.SetMove(false);
            tmp_organism = tmp_organism.next;
        }
    }

    public void Collision(Organism organism) {
        Organism tmp_organism = head;
        Polozenie organism_cords = organism.GetCords();

        while (tmp_organism != null) {
            if (tmp_organism == organism) {
                tmp_organism = tmp_organism.next;
                continue;
            }
            else {
                Polozenie tmp_cords = tmp_organism.GetCords();
                if ((organism_cords.x == tmp_cords.x) && (organism_cords.y == tmp_cords.y)) {
                    organism.Collision(tmp_organism);
                    break;
                }
            }
            tmp_organism = tmp_organism.next;
        }
    }
    public void SaveOrganisms(PrintWriter writer){
        writer.printf("%d\n",NumberOfOrganism);
        Organism tmp_organism = head;
        while (tmp_organism != null) {
            writer.printf("%s\n",tmp_organism.getName());
            writer.printf("%d\n",tmp_organism.GetCords().x);
            writer.printf("%d\n",tmp_organism.GetCords().y);

            tmp_organism = tmp_organism.next;
        }
    }
    public void LoadOrganisms(Scanner scanner){
        Organism tmp_organism = head;
        //head.next = null;
        //tail.prev = null;
        head = null;
        tail = null;

        int amount_of_organisms = scanner.nextInt();
        for (int i = 0; i < amount_of_organisms; i++) {
            String name = scanner.next();
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            tmp_organism.New(name, tmp_organism.world, x, y);
        }
        //remove(tmp_organism);
    }
    public Polozenie Human() {
        Organism tmp_organism = head;
        Polozenie cords = new Polozenie(0,0);
        while (tmp_organism != null) {
            if (tmp_organism.getName().equals("HUMAN")) {
                return tmp_organism.GetCords();
            }
            tmp_organism = tmp_organism.next;
        }
        return cords;
    }
    public Polozenie FindHogweed(Polozenie cyber_sheep) {
        Organism tmp_organism = head;
        double distance = 0;
        Polozenie cords = new Polozenie(0,0);
        while (tmp_organism != null) {
            if (tmp_organism.getName().equals("HOGWEED")) {
                if (cords.x==0){
                    cords = tmp_organism.GetCords();
                    distance = Math.sqrt(Math.pow((cyber_sheep.x-cords.x),2)+ Math.pow((cyber_sheep.y-cords.y),2));
                }
                else if(Math.sqrt(Math.pow((cyber_sheep.x-tmp_organism.GetCords().x),2) +
                        Math.pow((cyber_sheep.y-tmp_organism.GetCords().y),2))<distance){
                    cords = tmp_organism.GetCords();
                }
            }
            tmp_organism = tmp_organism.next;
        }
        return cords;
    }
    public void SetNumberOfOrganism(int a) {
        NumberOfOrganism = a;
    }

    public int GetNumberOfOrganism() {
        return NumberOfOrganism;
    }

}