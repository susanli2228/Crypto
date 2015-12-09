//netid: SZL5
#include <NTL/ZZ.h>
#include <string.h>
#include <iostream>
#include <fstream>
#include <cstdlib>
#include <vector>
#include <stdio.h>
#include <NTL/ZZ_pXFactoring.h>
#include <NTL/ZZ_p.h>
#include <chrono>

using namespace std;
using namespace std::chrono;
using namespace NTL;

int main (int argc, char** argv)
{

  //ZZ_p::init(991);

  //ZZ a,b,c;
  //cin >> a;
  //cin >> b;
  //cout << std::stoi(argv[1]) * stoi(argv[2]) << endl;
   // local variable declarationn
   // check the boolean condition
  if( strcmp (argv[1], "s")==0)
  {
      cout << "ok... " << endl;
    std::ifstream f;
    f.open(argv[2]);

    string numShares1;
    string secret1;

    getline(f, numShares1); //read first line
    getline(f, secret1); //read second line
    f.close();

    //ZZ sec;
    int num1 = stoi(numShares1);
    int sec1 = stoi(secret1);

    ofstream oFile;
    oFile.open(argv[3]);

    //ZZ cumulative = 0;
    int cumulative = 0;

    for (int i = 1; i< num1*2 + 2; i++) {
      //first line: print numShares
      //for lines i*2, print i
      //for lines i*2+1, print s_i
      if (i==1) {
        oFile << (num1) << "\n";
      }
      else if (i % 2 == 0 ) {//even so print 1,2,3,4....
        oFile << (i/2) << "\n";

      }
      else if (i < num1*2 + 1) { // print randomly generated pieces
        auto time = high_resolution_clock::now();
        auto timeInt = std::chrono::duration_cast<nanoseconds>(time.time_since_epoch()).count();
        srand(timeInt);
        int temptemp = rand() % 991;
        cout << temptemp << endl;


        //ZZ temp;
        //temp = RandomBnd(991);
        //temp = rand() % 991; //FIND A BETTER RANDOMD NUMBER GENERATOR
        //int intTemp;
        //conv(intTemp, temp);
        cumulative = cumulative + temptemp;
        oFile << (temptemp) << "\n";

      }
      else {//print out the leftovers
        int adjCumulative = cumulative % 991;
        cout << adjCumulative << endl;
        if (adjCumulative > sec1) {
          sec1 = sec1 + 991;
        }

        oFile << (sec1 - adjCumulative);
        oFile.close();

      }
    }
  }
  else
  {
    cout << "Hey, i'm here " << endl;
    std::ifstream f;
    f.open(argv[2]);

    string numShares; string throwAway; string temp;
    getline(f, numShares);
    int num = stoi(numShares);

    //ZZ sum = 0;
    int sum = 0;

    for (int j=2; j < 2*num + 2; j++){

      if (j % 2 == 0) {//even number line (share piece index)
        getline(f, throwAway);

      }else {
        string line;
        getline(f, line);
        int temp = stoi(line);
        sum = sum + temp;

      }
    }
    f.close();

    ofstream oFile;
    oFile.open(argv[3]);
    oFile << (numShares) << "\n";
    oFile << (sum % 991);
    oFile.close();

   }
  return 0;
}

//ZZ_p::init(ZZ(991))