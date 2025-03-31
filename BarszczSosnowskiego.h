#pragma once
#include"Roslina.h"
class BarszczSosnowskiego: public Roslina
{
public:
	BarszczSosnowskiego();
	BarszczSosnowskiego(Swiat* ref, int x, int y);
	void Akcja() override;
	std::string getName()override;
	void Rysowanie() override;
	void Kolizja(Organizm* atakowany) override;
	~BarszczSosnowskiego();
};

