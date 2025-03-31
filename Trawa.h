#pragma once
#include"Roslina.h"

class Trawa : public Roslina
{
public:
	Trawa();
	Trawa(Swiat* ref, int x, int y);
	std::string getName()override;
	void Rysowanie() override;
	~Trawa();
};

