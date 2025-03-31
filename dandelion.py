from plant import Plant
from Stale import Stale

class Dandelion(Plant):
    def __init__(self, world1, x, y):
        super().__init__()
        self.SetStrength(0)
        self.SetInitiative(0)
        self.SetCords(x, y)
        self.SetMove(True)
        self.world = world1

    def getName(self):
        return "DANDELION"

    def Drawing(self):
        self.world.buttons[self.GetCords().y -1][self.GetCords().x -1].configure(bg='#FFFF00')
    
    def New(self, text, reference, x, y):
        self.world.GetOrganisms().add(Dandelion(reference, x, y))    
    def Action(self):
        for i in range(3):
            # EMPTY PLACE
            child = self.EmptyPlace()
            tmp_cords = self.GetCords()
            if child != -1:
                if child == Stale.LEFT:
                    self.New(self.getName(), self.world, tmp_cords.x - 1, tmp_cords.y)
                elif child == Stale.RIGHT:
                    self.New(self.getName(), self.world, tmp_cords.x + 1, tmp_cords.y)
                elif child == Stale.UP:
                    self.New(self.getName(), self.world, tmp_cords.x, tmp_cords.y - 1)
                elif child == Stale.DOWN:
                    self.New(self.getName(), self.world, tmp_cords.x, tmp_cords.y + 1)
