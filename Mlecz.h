#pragma once
#include"Roslina.h"
class Mlecz:public Roslina
{
public:
	Mlecz();
	Mlecz(Swiat* ref, int x, int y);
	void Akcja() override;
	std::string getName()override;
	void Rysowanie() override;
	~Mlecz();
};

