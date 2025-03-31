from plant import Plant

class Guarana(Plant):
    def __init__(self, world1, x, y):
        super().__init__()
        self.SetStrength(0)
        self.SetInitiative(0)
        self.SetCords(x, y)
        self.SetMove(True)
        self.world = world1

    def getName(self):
        return "GUARANA"
    
    def Drawing(self):
        self.world.buttons[self.GetCords().y -1][self.GetCords().x -1].configure(bg='#FFC0CB')
    
    def New(self, text, reference, x, y):
        self.world.GetOrganisms().add(Guarana(reference, x, y))    


    def Collision(self, attacked):
        attacked.SetStrength(attacked.GetStrength() + 3)
        self.world.GetOrganisms().remove(self, attacked)
        # !!!NOTIFICATIONS WHEN ANIMAL IS DELETED
