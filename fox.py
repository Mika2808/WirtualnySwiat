from animal import Animal
from Stale import Stale
import random

class Fox(Animal):
    def __init__(self, world1, x, y):
        super().__init__()
        self.SetStrength(3)
        self.SetInitiative(7)
        self.SetCords(x, y)
        self.SetMove(True)
        self.world = world1

    def getName(self):
        return "FOX"

    def Drawing(self):
        self.world.buttons[self.GetCords().y -1][self.GetCords().x -1].configure(bg='#FFA500')
    
    def New(self, text, reference, x, y):
        self.world.GetOrganisms().add(Fox(reference, x, y))    

    def Action(self):
        # AROUND ORGANISMS
        up_organism = None
        down_organism = None
        left_organism = None
        right_organism = None
        organism_cords = self.GetCords()

        tmp_organism = self.world.GetOrganisms().GetHead()

        while tmp_organism != None:
            tmp_cords = tmp_organism.GetCords()

            # ORGANISM FROM TOP
            if tmp_cords.x == organism_cords.x and tmp_cords.y == organism_cords.y - 1:
                up_organism = tmp_organism
            # ORGANISM FROM DOWN
            elif tmp_cords.x == organism_cords.x and tmp_cords.y == organism_cords.y + 1:
                down_organism = tmp_organism
            # ORGANISM FROM LEFT
            elif tmp_cords.x == organism_cords.x - 1 and tmp_cords.y == organism_cords.y:
                left_organism = tmp_organism
            # ORGANISM FROM right
            elif tmp_cords.x == organism_cords.x + 1 and tmp_cords.y == organism_cords.y:
                right_organism = tmp_organism

            tmp_organism = tmp_organism.next

        # EMPTY PLACE OR WEAKER ANIMAL
        # l-LEFT r-RIGHT u-UP d-DOWN n-NAH
        randomise = ['n', 'n', 'n', 'n']
        index = 0
        tmp_sila = self.GetStrength()

        # FROM LEFT
        if (left_organism == None or left_organism.GetStrength() <= tmp_sila) and organism_cords.x > 1:
            randomise[index] = 'l'
            index += 1

        # FROM RIGHT
        if (right_organism == None or right_organism.GetStrength() <= tmp_sila) and organism_cords.x < self.world.GetWidth():
            randomise[index] = 'r'
            index += 1

        # UP
        if (up_organism == None or up_organism.GetStrength() <= tmp_sila) and organism_cords.y > 1:
            randomise[index] = 'u'
            index += 1

        # DOWN
        if (down_organism == None or down_organism.GetStrength() <= tmp_sila) and organism_cords.y < self.world.GetHeight():
            randomise[index] = 'd'
            index += 1

        if index != 0:
            number = random.randint(0, index - 1)

            if randomise[number] == 'l':
                self.SetCords(organism_cords.x - 1, organism_cords.y)
                self.SetLastMove(Stale.LEFT)
            elif randomise[number] == 'r':
                self.SetCords(organism_cords.x + 1, organism_cords.y)
                self.SetLastMove(Stale.RIGHT)
            elif randomise[number] == 'u':
                self.SetCords(organism_cords.x, organism_cords.y - 1)
                self.SetLastMove(Stale.UP)
            elif randomise[number] == 'd':
                self.SetCords(organism_cords.x, organism_cords.y + 1)
                self.SetLastMove(Stale.DOWN)
            else:
                self.SetLastMove(0)
