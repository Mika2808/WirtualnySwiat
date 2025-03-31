#pragma once
#include "Organizm.h"

class Zwierze: public Organizm
{
public:
	Zwierze();
	void Akcja() override;
	void Kolizja(Organizm* atakowany) override;
	void CofnijRuch() override;
	~Zwierze();
};

