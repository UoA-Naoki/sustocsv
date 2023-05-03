import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

class sustocsv{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        File fi;
        File fo;
        FileReader fr=null;
        FileWriter fw=null;
        BufferedReader br=null;
        BufferedWriter bw=null;
        PrintWriter pw=null;
        try{
            System.out.print("読み込むファイル名: ");
            fi=new File(sc.next());


            // fi=new File(sc.next()+".sus");

            
            if(fi.exists()&&fi.isFile()&&fi.canRead()){
                br=new BufferedReader(new FileReader(fi));
            }else{
                System.out.println("ファイルを読めません。");
                System.exit(0);
            }
            System.out.print("書き出すファイル名: ");
            fo=new File(sc.next());


            // fo=new File("../../Minge2023Spring_Team2/Minge2023Spring_Team2/Minge2023Spring_Team2/example/musics/test/a.csv");


            if(fo.exists()){
                if(fo.isFile()&&fo.canWrite()){
                    pw=new PrintWriter(new BufferedWriter(new FileWriter(fo)));
                }else{
                    System.out.println("ファイルに書けません。");
                    System.exit(0);
                }
            }else{
                if(fo.createNewFile()){
                    pw=new PrintWriter(new BufferedWriter(new FileWriter(fo)));
                }else{
                    System.out.println("ファイルを作成できません。");
                    System.exit(0);
                }
            }
            if(br!=null&&pw!=null){
                String s;
                ArrayList<Double> measurel=new ArrayList<Double>();
                int currentmeasure;
                br.readLine();
                br.readLine();
                br.readLine();
                br.readLine();
                br.skip(12);
                double offsetms=Double.parseDouble(br.readLine());
                br.readLine();
                br.readLine();
                br.readLine();
                while(true){
                    s=br.readLine();
                    if(s.length()==0){
                        break;
                    }
                    while(true){
                        if(measurel.size()<(currentmeasure=Integer.parseInt(s.substring(1,4)))){
                            measurel.add(4.0);
                        }else{
                            break;
                        }
                    }
                    measurel.add(currentmeasure,Double.parseDouble(s.substring(7)));
                }
                br.skip(7);
                double bpm=Double.parseDouble(br.readLine());
                double offset=offsetms*bpm/(60.0);
                if(offset!=0){
                    offset*=-1;
                }
                br.readLine();
                ArrayList<Integer> measure=new ArrayList<Integer>();
                ArrayList<Integer> lane=new ArrayList<Integer>();
                ArrayList<ArrayList<Integer>> wholedata=new ArrayList<ArrayList<Integer>>();
                ArrayList<ArrayList<Double>> wholebeat=new ArrayList<ArrayList<Double>>();
                int wholedatal=0;
                double currentbeat=0;
                while((s=br.readLine())!=null){
                    measure.add(Integer.parseInt(s.substring(1,4)));
                    lane.add(Integer.parseInt(s.substring(5,6),16)/2-1);
                    String rawdata=s.substring(7);
                    int datal=rawdata.length()/2;
                    ArrayList<Integer> data=new ArrayList<Integer>();
                    ArrayList<Double> beat=new ArrayList<Double>();
                    currentmeasure=measure.get(measure.size()-1);
                    for(int i=0;i<datal;i++){
                        int currentdata=Integer.parseInt(rawdata.substring(i*2,i*2+1));
                        if(currentdata!=0){
                            switch(currentdata){
                                case 1:
                                    data.add(0);
                                    break;
                            }
                            beat.add(currentbeat);
                        }
                        currentbeat+=measurel.get(currentmeasure)/datal;
                    }
                    wholedata.add(data);
                    wholebeat.add(beat);
                    wholedatal+=data.size();
                }
                double[][] dataandbeatandlane=new double[wholedatal][3];
                int k=0;
                for(int i=0;i<wholedata.size();i++){
                    for(int j=0;j<wholedata.get(i).size();j++){
                        dataandbeatandlane[k][0]=wholedata.get(i).get(j);
                        dataandbeatandlane[k][1]=wholebeat.get(i).get(j);
                        dataandbeatandlane[k][2]=lane.get(i);
                        k++;
                    }
                }
                Arrays.sort(dataandbeatandlane,(a,b)->Double.valueOf(a[1]).compareTo(Double.valueOf(b[1])));
                for(int i=wholedatal-1;i>0;i--){
                    dataandbeatandlane[i][1]-=dataandbeatandlane[i-1][1];
                }
                dataandbeatandlane[0][1]=offset;
                int i;
                for(i=0;i<wholedatal-1;i++){
                    pw.println((int)dataandbeatandlane[i][0]+","+dataandbeatandlane[i][1]+","+(int)dataandbeatandlane[i][2]+","+bpm);
                }
                pw.print((int)dataandbeatandlane[i][0]+","+dataandbeatandlane[i][1]+","+(int)dataandbeatandlane[i][2]+","+bpm);
                br.close();
                pw.close();
            }
        }catch(FileNotFoundException e){
            System.out.println(e);
        }catch(IOException e){
            System.out.println(e);
        }
    }
}