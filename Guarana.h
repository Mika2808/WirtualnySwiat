#pragma once
#include"Roslina.h"

class Guarana: public Roslina
{
public:
	Guarana();
	Guarana(Swiat* ref, int x, int y);
	std::string getName()override;
	void Rysowanie() override;
	void Kolizja(Organizm* atakowany) override;
	~Guarana();
};

