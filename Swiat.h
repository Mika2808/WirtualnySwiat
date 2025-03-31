#pragma once
//#include "Organizm.h"
#include "ListaOrganizmow.h"

class Swiat
{
	int NextMove;
	int szerokosc;
	int wysokosc;
	bool swiat;
	bool superumiejetnosc;
	int rundy;
public:
	ListaOrganizmow list;

	Swiat();
	~Swiat();
	
	//FUNKCJE DO POBIERANIA DANYCH
	int GetSzerokosc();
	int GetWysokosc();
	int GetNextMove();
	bool GetUmiejetnosc();
	int GetRundy();

	//WYJŒCIE (KLIKNIÊCIE "q")
	int KoniecSwiata();
	
	//FUNCKCJE DO USTAWIANIA PARAMETRÓW
	void SetKoniecSwiata();
	void SetWysokosc(int x);
	void SetSzerokosc(int x);
	bool SetNextMove(int x);
	void SetUmiejetnosc(bool x);
	void SetRundy(int x);
	
	//FUNKCJE DO ZARZ¥DANIA ŒWIATEM
	void rysujSwiat();
	void legenda();
	void wykonajTure(int znak);
	void Zapisz();
	void Wczytaj();
};

