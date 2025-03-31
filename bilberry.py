from plant import Plant

class Bilberry(Plant):
    def __init__(self, world1, x, y):
        super().__init__()
        self.SetStrength(0)
        self.SetInitiative(0)
        self.SetCords(x, y)
        self.SetMove(True)
        self.world = world1

    def getName(self):
        return "BILBERRY"

    def Drawing(self):
        self.world.buttons[self.GetCords().y -1][self.GetCords().x -1].configure(bg='#00FF00')
    
    def New(self, text, reference, x, y):
        self.world.GetOrganisms().add(Bilberry(reference, x, y))    

    def Collision(self, attacked):
        self.world.GetOrganisms().remove(attacked, self)
        self.world.GetOrganisms().remove(self, attacked)
        # !!!NOTIFICATIONS WHEN ANIMAL IS DELETED
