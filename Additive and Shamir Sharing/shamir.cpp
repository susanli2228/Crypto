//netid: SZL5
#include <NTL/ZZ.h>
#include <string.h>
#include <iostream>
#include <fstream>
#include <cstdlib>
#include <vector>
#include <NTL/vector.h>
#include <string.h>
#include <iostream>
#include <fstream>
#include <stdio.h>
#include <NTL/ZZ_pXFactoring.h>
#include <NTL/ZZ_p.h>
#include <chrono>
#include <NTL/vec_ZZ.h>
#include <NTL/vec_lzz_p.h>
#include <NTL/vec_ZZ_p.h>
#include <NTL/ZZX.h>

using namespace std;
using namespace std::chrono;
using namespace NTL;

int main (int argc, char** argv)
{

  ZZ ZZmodd;
  ZZmodd = 991;

  ZZ_p::init(ZZmodd); //current modulus of class is 991

  if( strcmp (argv[1], "s")==0) //make shares, input file has 3 lines
  {
    cout << "ok... " << endl;
    std::ifstream f;
    f.open(argv[2]);

    string numShares1;
    string minNum1;
    string secret1;

    getline(f, numShares1); //read first line
    getline(f, minNum1); //read second line
    getline(f, secret1); //read third line
    f.close();

    int num1 = stoi(numShares1);
    int min1 = stoi(minNum1);
    int sec1 = stoi(secret1);

    ZZ_p secP;
    conv(secP, to_ZZ(sec1));
    cout << secP << endl;

    ofstream oFile;
    oFile.open(argv[3]);

    oFile << num1 << "\n";
    oFile << min1 << "\n";

    auto time = high_resolution_clock::now();
    auto timeInt = std::chrono::duration_cast<nanoseconds>(time.time_since_epoch()).count();
    //ZZ ZZtimeseed;
    //conv(timeInt, ZZtimeseed);
    SetSeed(to_ZZ((long)timeInt));
    //srand(timeInt);
    ZZ_pX poly = random_ZZ_pX(min1);
    cout << poly << endl;

    ZZ zer; zer = 0;
    ZZ_p Pzer; conv(Pzer, zer);
    ZZ_p constant = eval(poly, Pzer);


    for (int i = 3; i< num1*2 + 3; i++) {

      if (i % 2 == 1 ) {//2n+1 so print 1,2,3,4....
        oFile << ((i-1)/2) << "\n";

      }

      else {//print out the leftovers

        int iVal = (i-2)/2;
        ZZ ZZiVal = to_ZZ(iVal);
        cout << ZZiVal << endl;
        //conv(iVal, ZZiVal);
        ZZ_p ZZPiVal;

        conv(ZZPiVal, ZZiVal);
        ZZ_p oValraw = eval(poly, ZZPiVal);
        ZZ_p oVal;
        oVal = oValraw - constant + secP;

        if (i == num1*2 + 2) {
          oFile << oVal;
          oFile.close();
        }
        else {
          oFile << oVal << "\n";
        }

      }
    }

  }
  else //reconstruct secret
  {
    cout << "Hey, i'm here " << endl;
    std::ifstream f;
    f.open(argv[2]);

    string numShares; string minNum;

    getline(f, numShares);
    getline(f, minNum);
    int num = stoi(numShares);
    int t = stoi(minNum);

    int oddoreven = 3;

    Vec<ZZ_p> indexVEC;
    Vec<ZZ_p> valueVEC;




    string stuff;


    while (getline(f, stuff)){

      if (oddoreven % 2 == 1) {//odd number line (share piece index)
        //string index;
        //getline(f, index);
        int indexint = stoi(stuff);
        ZZ_p indexintZZP;
        conv(indexintZZP, to_ZZ(indexint));
        indexVEC.append(indexintZZP);
        oddoreven++;
        //indexVEC[(i-1)/2] = indexint;

      }else { //even number line (share piece)
        //string value;
        //getline(f, value);
        int valueint = stoi(stuff);
        ZZ_p valueintZZP;
        conv(valueintZZP, to_ZZ(valueint));
        valueVEC.append(valueintZZP);
        oddoreven++;
        // valueVEC[(i-2)/2] = valueint;

      }
    }
    f.close();

    ZZ zer; zer = 0;
    ZZ_p Pzer; conv(Pzer, zer);

    ZZ_pX poly = interpolate(indexVEC, valueVEC);
    ZZ_p result = eval(poly, Pzer);

    ofstream oFile;
    oFile.open(argv[3]);
    oFile << num << "\n";
    oFile << t << "\n";
    oFile << result;
    oFile.close();

   }

  return 0;
}