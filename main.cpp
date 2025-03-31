#include<stdio.h>
#include<malloc.h>
#include<string.h>
#include<windows.h>
#include"conio2.h"
#include"Swiat.h"

using namespace std;
int main() {
#ifndef __cplusplus
		Conio2_Init();
#endif
		settitle("Mikolaj Kabala, 193380");
		Swiat s;
		_setcursortype(_NOCURSOR);
		
		int znak = 0;
		while (znak!='q')
		{			
			znak = getch();
			clrscr();
			if (znak == 0) {
				znak = getch();
				
				s.wykonajTure(znak);
				
			}
			else if (znak == 'a') {
				s.wykonajTure(znak);
			}
			else if (znak == 's') {
				s.Zapisz();
			}
			else if (znak == 'l') {
				s.Wczytaj();
			}
			s.rysujSwiat();
		}

	textcolor(WHITE);
	_setcursortype(_NORMALCURSOR);	
	return 0; 
}