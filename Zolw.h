#pragma once
#include "Zwierze.h"
class Zolw : public Zwierze
{
public:
	Zolw();
	Zolw(Swiat* ref, int x, int y);
	std::string getName() override;
	void Akcja() override;
	void Kolizja(Organizm* atakowany) override;
	void Rysowanie() override;
	~Zolw();
};

