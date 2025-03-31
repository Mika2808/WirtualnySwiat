#pragma once
#include "Organizm.h"
class Roslina : public Organizm
{
public:
	Roslina();
	void Akcja() override;
	void Kolizja(Organizm* atakowany) override;
	void Rysowanie() override;
	void CofnijRuch() override;
	~Roslina();
};

