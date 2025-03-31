public class Plant extends Organism{
    @Override
    public  void Collision(Organism attacked){
        world.GetOrganisms().remove(this,attacked);
        //!!!NOTIFICATIONS WHEN ANIMAL IS DELETED
    }
    @Override
    public void Action(){
        //EMPTY PLACE FOR BREADING
        int place = EmptyPLace();
        Polozenie tmp_cords = GetCords();

        //IF THERE IS ANY EMPTY PLACE
        if(place != -1){
            if(place==Stale.LEFT){
                New(getName(), world, tmp_cords.x - 1, tmp_cords.y);
            }
            else if(place==Stale.RIGHT){
                New(getName(), world, tmp_cords.x + 1, tmp_cords.y);
            }
            else if(place==Stale.UP){
                New(getName(), world, tmp_cords.x, tmp_cords.y - 1);
            }
            else if(place==Stale.DOWN){
                New(getName(), world, tmp_cords.x, tmp_cords.y + 1);
            }
        }
    }
    @Override
    public  void Drawing(){}
    @Override
    public String getName(){
        return "";
    }
}
