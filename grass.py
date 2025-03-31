from plant import Plant

class Grass(Plant):
    def __init__(self, world1, x, y):
        super().__init__()
        self.SetStrength(0)
        self.SetInitiative(0)
        self.SetCords(x, y)
        self.SetMove(True)
        self.world = world1

    def getName(self):
        return "GRASS"

    def Drawing(self):
        self.world.buttons[self.GetCords().y -1][self.GetCords().x -1].configure(bg='#800080')
    
    def New(self, text, reference, x, y):
        self.world.GetOrganisms().add(Grass(reference, x, y))    
