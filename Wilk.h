#pragma once
#include"Zwierze.h"
class Wilk : public Zwierze
{
public:
	Wilk();
	Wilk(Swiat* ref, int x, int y);
	std::string getName() override;
	void Rysowanie() override;
	~Wilk();
};

