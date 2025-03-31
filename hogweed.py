from plant import Plant
from Stale import Stale

class Hogweed(Plant):
    def __init__(self, world1, x, y):
        super().__init__()
        self.SetStrength(4)
        self.SetInitiative(0)
        self.SetCords(x, y)
        self.SetMove(True)
        self.world = world1

    def Action(self):
        organism_cords = self.GetCords()
        tmp_organism = self.world.GetOrganisms().GetHead()
        while tmp_organism is not None:
            # CHECKING IF ORGANISM IS ANIMAL
            if tmp_organism.GetInitiative() != 0:
                tmp_cords = tmp_organism.GetCords()
                # ORGANISM FROM TOP
                if tmp_cords.x == organism_cords.x and tmp_cords.y == organism_cords.y - 1:
                    if tmp_organism.getName() != "CYBER-SHEEP":
                        self.world.GetOrganisms().remove(tmp_organism, self)
                # ORGANISM FROM DOWN
                elif tmp_cords.x == organism_cords.x and tmp_cords.y == organism_cords.y + 1:
                    if tmp_organism.getName() != "CYBER-SHEEP":
                        self.world.GetOrganisms().remove(tmp_organism, self)
                # ORGANISM FROM LEFT
                elif tmp_cords.x == organism_cords.x - 1 and tmp_cords.y == organism_cords.y:
                    if tmp_organism.getName() != "CYBER-SHEEP":
                        self.world.GetOrganisms().remove(tmp_organism, self)
                # ORGANISM FROM RIGHT
                elif tmp_cords.x == organism_cords.x + 1 and tmp_cords.y == organism_cords.y:
                    if tmp_organism.getName() != "CYBER-SHEEP":
                        self.world.GetOrganisms().remove(tmp_organism, self)
            tmp_organism = tmp_organism.next

        # EMPTY PLACE FOR BREADING
        place = self.EmptyPlace()

        # IF THERE IS ANY EMPTY PLACE
        if place != -1:
            if place == Stale.LEFT:
                self.New(self.getName(), self.world, organism_cords.x - 1, organism_cords.y)
            elif place == Stale.RIGHT:
                self.New(self.getName(), self.world, organism_cords.x + 1, organism_cords.y)
            elif place == Stale.UP:
                self.New(self.getName(), self.world, organism_cords.x, organism_cords.y - 1)
            elif place == Stale.DOWN:
                self.New(self.getName(), self.world, organism_cords.x, organism_cords.y + 1)

    def getName(self):
        return "HOGWEED"

    def Drawing(self):
        self.world.buttons[self.GetCords().y -1][self.GetCords().x -1].configure(bg='#808080')
    
    def New(self, text, reference, x, y):
        self.world.GetOrganisms().add(Hogweed(reference, x, y))    


    def Collision(self, attacked):
        self.world.GetOrganisms().remove(self, attacked)
        if attacked.getName() != "CYBER-SHEEP":
            self.world.GetOrganisms().remove(attacked, self)
