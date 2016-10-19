package lab3;



public class Codificador {
    private int[] bitsIn = new int[1000];
    private int[] bits2 = new int[2000];
    private int[] bits3 = new int[2032];
    private int[] cs = {0,0,0,0,0,0};
    public void convolute(){
        int i = 0;
        while (i<1000||cs[0]==0||cs[1]==0||cs[2]==0||cs[3]==0||cs[4]==0||cs[5]==0){
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
}
