#pragma once
#include"Roslina.h"
class WilczeJagody:public Roslina
{
public:
	WilczeJagody();
	WilczeJagody(Swiat* ref, int x, int y);
	std::string getName()override;
	void Rysowanie() override;
	void Kolizja(Organizm* atakowany) override;
	~WilczeJagody();
};

