package lab3;



public class Codificador {
    private static int[] header = {0,0,0,1, 1,0,1,0, 1,1,0,0, 1,1,1,1, 1,1,1,1, 1,1,0,0, 0,0,0,1, 1,1,0,1};
    private int[] bitsIn = new int[1000];
    private int[] bits2 = new int[2000];
    private int[] bits3 = new int[2032];
    private int[] bits4 = new int[2032];
    private int[] cs = {0,0,0,0,0,0};
    private int[] ss = {1,1,1,1, 1,1,1,1};
    
    public void convolute(){
        for(int i =0; i<1000; i++){
            bits2[2*i]   =(bitsIn[i]+cs[0]+cs[1]+cs[2]+cs[5])%2;
            bits2[2*i+1] =(bitsIn[i]+cs[1]+cs[2]+cs[4]+cs[5])%2;
            cs[0] = bitsIn[i];
            cs[1] = cs[0];
            cs[2] = cs[1];
            cs[3] = cs[2];
            cs[4] = cs[3];
            cs[5] = cs[4];
        }
    }
    
    public void addHeader(){
        for(int i=0;i<2000;i++){
            bits3[i]=bits2[i];
        }
        for(int i=0;i<32;i++){
            bits3[2000+i]=header[i];
        }
    }
    
    public void scramble(){
        for(int i=0;i<2032;i++){
            bits4[i]=(bits3[i]+ss[7])%2;
            ss[0]=(ss[0]+ss[2]+ss[4]+ss[7])%2;
            ss[1]=ss[0];
            ss[2]=ss[1];
            ss[3]=ss[1];
            ss[4]=ss[1];
            ss[5]=ss[1];
            ss[6]=ss[1];
            ss[7]=ss[1];
            ss[2]=ss[1];
        }
    }
}
