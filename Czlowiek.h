#pragma once
#include"Zwierze.h"
class Czlowiek :public Zwierze
{
public:
	Czlowiek();
	Czlowiek(Swiat* ref, int x, int y);
	std::string getName() override;
	void Akcja() override;
	void Kolizja(Organizm* atakowany) override;
	void Rysowanie() override;
	~Czlowiek();
};

