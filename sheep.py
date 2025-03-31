from animal import Animal

class Sheep(Animal):
    def __init__(self, world1, x, y):
        super().__init__()
        self.SetStrength(4)
        self.SetInitiative(4)
        self.SetCords(x, y)
        self.SetMove(True)
        self.world = world1

    def getName(self):
        return "SHEEP"

    def Drawing(self):
        self.world.buttons[self.GetCords().y -1][self.GetCords().x -1].configure(bg='#AAB000')
    
    def New(self, text, reference, x, y):
        self.world.GetOrganisms().add(Sheep(reference, x, y))    
