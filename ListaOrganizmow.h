#pragma once
#include "Organizm.h"
class ListaOrganizmow
{
	int ilosc_organizmow;
	int wysokosc_wyswietlania;
	std::string podsumowanie;
public:
	Organizm* head;
	Organizm* tail;
	ListaOrganizmow();

	//ROZPOCZYNANIE WIRTUALNEGO ŒWIATA
	void Start(Swiat* a);
	
	//DODAWANIE ORGANIZMÓW
	void insert(Organizm* tmp);

	//USUWANIE ORGANIZMÓW
	void usun(Organizm* tmp, Organizm* tmp1);
	
	//RYSOWANIE PLANSZY
	void Plansza();
	
	//WYKONANIE AKCJI PRZEZ KA¯DY ORGANIZM
	void Ruch();
	
	//WYKRYWANIE KOLIZJI
	void Kolizje(Organizm*a);

	//ZNAJDOWANIE PO£O¯ENIA CZ£OWIEKA
	Polozenie Human();

	//OPIS ŒWIATA
	void DodajOpis(std::string);


	//WYŒWIETLANIE ZMAIN W ŒWIECIE
	void Opis();
	
	//DO WCZYTYWANIA
	void SetIloscOrganizmow(int a);
	int GetIloscOrganizmow();
	void Zapisz(FILE*plik);
	void Wczytaj(FILE*plik);

	~ListaOrganizmow();
};

