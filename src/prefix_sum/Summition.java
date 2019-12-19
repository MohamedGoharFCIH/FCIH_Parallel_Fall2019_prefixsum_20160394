/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prefix_sum;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.Math;
/**
 *
 * @author GOHAR
 */
public  class Summition extends Thread{
  int threadID;     /* thread id */
  int temp;

  int [][] y;
  int [][] z;

  Summition (int i, int [][] y, int [][] z) {
    threadID = i;
    this.y = y;
    this.z = z;
  }


  @Override
  public void run () {
      try {
          int l = PrefixSum.LOGSIZE;
          y[l][threadID] = PrefixSum.inputArr[threadID];
          PrefixSum.barrier.Synchronize();
          
          prefix (l);
          
          PrefixSum.outputArr[threadID] = z[l][threadID];
          PrefixSum.barrier.Synchronize();
      } catch (Exception ex) {
          System.out.println("Exception" + ex);
      }
  }


  void prefix (int l) throws Exception {
    if (l == 0) { 
      if (threadID == 1) 
          z[0][1] = y[0][1];
      PrefixSum.barrier.Synchronize();
      return;
    }
    // if not las thread 
    if (threadID <= Math.pow(2, l-1)) 
        y[l-1][threadID] = y[l][2*threadID-1] + y[l][2*threadID];

    
    PrefixSum.barrier.Synchronize();

    prefix (l-1);

    if (threadID <= Math.pow(2, l)) {
       if (threadID == 1)
           z[l][1] = y[l][1];
       else 
           if (threadID%2 == 0)
                z[l][threadID] = z[l-1][threadID/2];
            else
                z[l][threadID] = z[l-1][(threadID-1)/2] + y[l][threadID];
      }

    PrefixSum.barrier.Synchronize();
 
  }

}
