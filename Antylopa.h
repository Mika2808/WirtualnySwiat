#pragma once
#include "Zwierze.h"
class Antylopa : public Zwierze
{
public:
	Antylopa();
	Antylopa(Swiat* ref, int x, int y);
	std::string getName() override;
	void Akcja() override;
	void Kolizja(Organizm* atakowany) override;
	void Rysowanie() override;
	int WolneMiejsca()override;
	~Antylopa();
};

