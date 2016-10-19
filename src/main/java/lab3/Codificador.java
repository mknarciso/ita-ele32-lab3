package lab3;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

public class Codificador {
	public static String texto;
	public static int posicao = 0;
    private static int[] header = {0,0,0,1, 1,0,1,0, 1,1,0,0, 1,1,1,1, 1,1,1,1, 1,1,0,0, 0,0,0,1, 1,1,0,1};
    private int[] bitsIn = new int[1000];
    private int[] bits2 = new int[2000];
    private int[] bits3 = new int[2032];
    private int[] bits4 = new int[2032];
    private int[] cs = {0,0,0,0,0,0};
    private int[] ss = {1,1,1,1, 1,1,1,1};
    public static String compactado = "";
    
	public static void lerTexto(String file){
		try {
			BufferedReader br = new BufferedReader(new FileReader(file+".txt"));
		    String line;
		    while ((line = br.readLine()) != null) {
		       texto = texto + line;
		    }
		    br.close();
		}
		catch (Exception e){
			System.out.println("Deu ruim!");
		}
	}
	
	public void gerarBitsIn (){
		char aux;
		int a = texto.length();
		if(a < posicao + 1000){
			for(int i = 0; i < posicao + 10000- a; i ++){
				texto = texto+ "0";
			}
		}
		for (int i = 0; i < 1000; i ++){
			aux = texto.charAt(posicao + i);
			if(aux == '1')
				bitsIn[i] = 1;
			else bitsIn[i] = 0;
		}
		posicao = posicao + 1000;
	}
	
    public void convolute(){
        for(int j = 0; j < 6; j++){
	       cs[j] = 0;
        }
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
		for(int j = 0; j < 8; j++){
	       ss[j] = 0;
	    }
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

	 public void armazenaEmHexadecimal (){
		 int a;
		 String aux = "";
		 for (int i = 0; i <508; i ++){
			 a = 1000*bits4[i] + 100* bits4[i+1] + 10*bits4[i+2] + bits4[i+4];
			 aux = aux + Integer.toHexString(a);
		 }
		 compactado = compactado + aux;
	 }
	 
	 
	 public void criaTxtHexadecimal (String filename){
		 try(  PrintWriter out = new PrintWriter( filename+"_out.txt" )  ){
			    out.println( Codificador.compactado );
		}
		 catch (Exception e){
				System.out.println("Deu ruim!");
		}
	 }
    
    public void run(String file){
        lerTexto(file);
        do{
            gerarBitsIn();
            convolute();
            addHeader();
            scramble();
            armazenaEmHexadecimal();
            texto = texto.substring(1000);
        } while (texto.length()>1000);
        criaTxtHexadecimal(file);
    }
    
    public static void main(String args[]){
        Codificador cod = new Codificador();
        cod.run("teste");
    }
}
