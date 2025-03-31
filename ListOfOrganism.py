from Polozenie import Polozenie
import random
from math import sqrt
from antelope import Antelope
from bilberry import Bilberry
from cybersheep import CyberSheep
from dandelion import Dandelion
from fox import Fox
from grass import Grass
from guarana import Guarana
from hogweed import Hogweed
from human import Human
from sheep import Sheep
from wolf import Wolf
from zolw import Turtle
import pickle



class ListOfOrganism:
    
    def __init__(self):
        self.head = None
        self.tail = None 
        self.NumberOfOrganism = 0
    
    def add(self,organism):
        if self.head == None :
            self.head = organism
            self.tail = organism
        
        else :
            tmp_organism = self.head
            initiative = organism.GetInitiative()
            
            if initiative == 0 :
                self.tail.next = organism
                organism.prev = self.tail
                self.tail = organism            
            else : 
                while tmp_organism != None :
                    if initiative > tmp_organism.GetInitiative() :
                        if tmp_organism == self.head :
                            tmp_organism.prev = organism
                            organism.next = tmp_organism
                            self.head = organism
                            break
                        else :
                            organism.next = tmp_organism
                            organism.prev = tmp_organism.prev
                            tmp_organism.prev.next = organism
                            tmp_organism.prev = organism
                            break
            
                    elif tmp_organism == self.tail :
                        if tmp_organism.GetInitiative() < initiative :
                            organism.next = self.tail
                            organism.prev = self.tail.prev
                            self.tail.prev.next = organism
                            self.tail.prev = organism
                            break
                        else :
                            self.tail.next = organism
                            organism.prev = self.tail
                            self.tail = organism
                            break
                    tmp_organism = tmp_organism.next
        
        self.NumberOfOrganism += 1
    
    def remove(self, organism_dead, organism_stay):
        if not organism_dead.getName() == "HUMAN" or (organism_dead.getName() == "HUMAN" and not organism_dead.world.GetSuperpower()):
            tmp_organism = self.head
            while tmp_organism is not None:
                if tmp_organism == organism_dead:
                    if tmp_organism == self.tail:
                        tmp_organism.prev.next = None
                        self.tail = tmp_organism.prev
                        break
                    elif tmp_organism == self.head:
                        tmp_organism.next.prev = None
                        self.head = tmp_organism.next
                        break
                    else:
                        tmp_organism.prev.next = tmp_organism.next
                        tmp_organism.next.prev = tmp_organism.prev
                        break
                tmp_organism = tmp_organism.next
            if organism_dead.GetInitiative() != 0:
                organism_dead.world.text += "(" + str(organism_stay.GetCords().x) + "," + str(organism_stay.GetCords().y) + ") " + organism_dead.getName() + " GOT KILLED BY " + organism_stay.getName() + "\n"
            else:
                organism_dead.world.text += "(" + str(organism_stay.GetCords().x) + "," + str(organism_stay.GetCords().y) + ") "  + organism_dead.getName() + " GOT EATEN BY " + organism_stay.getName() + "\n"
            self.NumberOfOrganism -= 1
    
    def DrawOrganism(self):
        tmp_organism = self.head
        while tmp_organism is not None:
            tmp_organism.Drawing()
            tmp_organism = tmp_organism.next
    
    def GetHead(self):
        return self.head
    
    def Move(self):
        tmp_organism = self.head
        while tmp_organism is not None:
            if not tmp_organism.GetMove():
                tmp_organism.Action()
                if tmp_organism.GetInitiative() != 0:
                    self.Collision(tmp_organism)
                tmp_organism.SetMove(True)
            tmp_organism = tmp_organism.next

        tmp_organism = self.head
        while tmp_organism is not None:
            tmp_organism.SetMove(False)
            tmp_organism = tmp_organism.next

    def Collision(self, organism):
        tmp_organism = self.head
        organism_cords = organism.GetCords()
        while tmp_organism is not None:
            if tmp_organism == organism:
                tmp_organism = tmp_organism.next
                continue
            else:
                tmp_cords = tmp_organism.GetCords()
                if organism_cords.x == tmp_cords.x and organism_cords.y == tmp_cords.y:
                    organism.Collision(tmp_organism)
                    break
            tmp_organism = tmp_organism.next
    
    def Start(self, world):
        height = world.height
        width = world.length
        
        random.seed()
        self.add(Wolf(world, 1, 1))
        self.add(Wolf(world, 1, 2))
        self.add(Wolf(world, 2, 2))
        self.add(Wolf(world, 2, 3))
        self.add(Wolf(world, 3, 1))
        #self.add(Wolf(world, random.randint(1, width), random.randint(1, height)))
        #self.add(Antelope(world, random.randint(1, width), random.randint(1, height)))
        #self.add(Human(world, random.randint(1, width), random.randint(1, height)))
        
        #self.add(Guarana(world, random.randint(1, width), random.randint(1, height)))
        #self.add(Turtle(world, random.randint(1, width), random.randint(1, height)))
        #self.add(Antelope(world, random.randint(1, width), random.randint(1, height)))
        self.add(CyberSheep(world, 17, 14))
        self.add(Hogweed(world, 19, 19))
        self.add(Hogweed(world, 1, 19))
        self.add(Hogweed(world, 19, 1))
        #self.add(Hogweed(world, random.randint(1, width), random.randint(1, height)))
        # if height * width > 10:
        #     self.add(Hogweed(world, random.randint(1, width), random.randint(1, height)))
        #self.add(Wolf(world, random.randint(1, width), random.randint(1, height)))
        #self.add(Sheep(world, random.randint(1, width), random.randint(1, height)))
        #self.add(Guarana(world, random.randint(1, width), random.randint(1, height)))
        #self.add(Dandelion(world, random.randint(1, width), random.randint(1, height)))
        #self.add(Guarana(world, random.randint(1, width), random.randint(1, height)))

        tmp_organism = self.head
        while tmp_organism is not None:
            tmp_organism.SetMove(False)
            tmp_organism = tmp_organism.next
        self.DrawOrganism()
    
    def Human(self):
        tmp_organism = self.head
        cords = Polozenie(0, 0)
        while tmp_organism is not None:
            if tmp_organism.getName() == "HUMAN":
                return tmp_organism.GetCords()
            tmp_organism = tmp_organism.next
        return cords
    
    def FindHogweed(self,cyber_sheep):
        tmp_organism = self.head
        distance = 0
        cords = Polozenie(0, 0)
    
        while tmp_organism != None:
            if tmp_organism.getName() == "HOGWEED":
                if cords.x == 0:
                    cords = tmp_organism.GetCords()
                    distance = sqrt((cyber_sheep.x - cords.x) ** 2 + (cyber_sheep.y - cords.y) ** 2)
                elif sqrt((cyber_sheep.x - tmp_organism.GetCords().x) ** 2 + (cyber_sheep.y - tmp_organism.GetCords().y) ** 2) < distance:
                    cords = tmp_organism.GetCords()
        
            tmp_organism = tmp_organism.next
    
        return cords
    
    def SaveOrganisms(self,file):
        pickle.dump(self.NumberOfOrganism, file)

        tmp_organism = self.head
        while tmp_organism != None:
            pickle.dump(tmp_organism.getName(), file)
            pickle.dump(tmp_organism.GetCords().x, file)
            pickle.dump(tmp_organism.GetCords().y, file)
            tmp_organism = tmp_organism.next
    
    def LoadOrganisms(self,file):
        tmp_organism = self.head
        self.head = None
        self.tail = None
        
        self.NumberOfOrganism = pickle.load(file)
        for i in range(self.NumberOfOrganism):
            text = pickle.load(file)
            x = pickle.load(file)
            y = pickle.load(file)
            
            if text == "FOX":
                tmp_organism.world.GetOrganisms().add(Fox(tmp_organism.world, x, y))
            elif text == "ANTELOPE":
                tmp_organism.world.GetOrganisms().add(Antelope(tmp_organism.world, x, y))
            elif text == "WOLF":
                tmp_organism.world.GetOrganisms().add(Wolf(tmp_organism.world, x, y))
            elif text == "TURTLE":
                tmp_organism.world.GetOrganisms().add(Turtle(tmp_organism.world, x, y))
            elif text == "SHEEP":
                tmp_organism.world.GetOrganisms().add(Sheep(tmp_organism.world, x, y))
            elif text == "GRASS":
                tmp_organism.world.GetOrganisms().add(Grass(tmp_organism.world, x, y))
            elif text == "DANDELION":
                tmp_organism.world.GetOrganisms().add(Dandelion(tmp_organism.world, x, y))
            elif text == "GUARANA":
                tmp_organism.world.GetOrganisms().add(Guarana(tmp_organism.world, x, y))
            elif text == "BILBERRY":
                tmp_organism.world.GetOrganisms().add(Bilberry(tmp_organism.world, x, y))
            elif text == "HOGWEED":
                tmp_organism.world.GetOrganisms().add(Hogweed(tmp_organism.world, x, y))
            elif text == "CYBER-SHEEP":
                tmp_organism.world.GetOrganisms().add(Hogweed(tmp_organism.world, x, y))


        
       