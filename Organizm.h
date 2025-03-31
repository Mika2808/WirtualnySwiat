#pragma once
#include<stdio.h>
#include<malloc.h>
#include<string.h>
#include<windows.h>
#include<iostream>
#include"conio2.h"

#define WARZYWA 1
#define GORA 2
#define DOL 3
#define LEWO 4
#define PRAWO 5
#define ANIMAL_SIGN 254

struct Polozenie {
	int x, y;
};

class Swiat;

class Organizm
{
private:
	
	int sila;
	int inicjatywa;
	int lastMove;
	Polozenie cords;
	std::string name;
	bool ruch;
public:
	
	Swiat* referencja;
	Organizm();
	void New(std::string txt,Swiat*ref,int x,int y);
	
	//PARAMETRY DO LISTY ORGANIZMÓW
	Organizm* next;
	Organizm* prev;
	
	//FUNKCJE DO POBIERANIA DANYCH
	int GetSila();
	int GetInicjatywa();
	int GetLastMove();
	Polozenie GetCords();
	bool GetRuch();
	
	//FUNKCJE DO USTAWIANIA DANYCH
	void SetCords(int x, int y);
	void SetSila(int x);
	void SetLastMove(int x);
	void SetInicjatywa(int x);
	void SetRuch(bool x);

	//FUNKCJE DO NAPDPISANIA
	virtual std::string getName();
	virtual void Akcja();
	virtual void Kolizja(Organizm* atakowany);
	virtual void Rysowanie();
	virtual void CofnijRuch();

	//FUNKCJA DO SPRAWDZANIA I LOSOWANIA WOLNEGO MIEJSCA
	virtual int WolneMiejsca();
	
	~Organizm();
};

