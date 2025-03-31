#pragma once
#include "Zwierze.h"
class Lis: public Zwierze
{
public:
	Lis();
	Lis(Swiat* ref, int x, int y);
	std::string getName() override;
	void Akcja() override;
	void Rysowanie() override;
	~Lis();
};

