#pragma once
#include"Zwierze.h"
class Owca: public Zwierze
{
public:
	Owca();
	Owca(Swiat* ref, int x, int y);
	std::string getName() override;
	void Rysowanie() override;
	~Owca();
};

