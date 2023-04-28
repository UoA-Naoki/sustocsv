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
            fi=new File("../sus/"+sc.next());
            if(fi.exists()&&fi.isFile()&&fi.canRead()){
                br=new BufferedReader(new FileReader(fi));
            }else{
                System.out.println("ファイルを読めません。");
                System.exit(0);
            }
            System.out.print("書き出すファイル名: ");
            fo=new File("../csv/"+sc.next());
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
                br.readLine();
                br.readLine();
                br.readLine();
                br.readLine();
                br.skip(12);
                double offsetms=Double.parseDouble(br.readLine());
                br.readLine();
                br.readLine();
                br.readLine();
                br.readLine();
                br.readLine();
                br.skip(7);
                double bpm=Double.parseDouble(br.readLine());
                double offset=offsetms*bpm/(60.0)*(-1);
                pw.println("BPM="+bpm+", offset="+offset+"拍");
                br.readLine();
                ArrayList<Integer> measure=new ArrayList<Integer>();
                ArrayList<Integer> lane=new ArrayList<Integer>();
                ArrayList<ArrayList<Integer>> wholedata=new ArrayList<ArrayList<Integer>>();
                ArrayList<ArrayList<Double>> wholebeat=new ArrayList<ArrayList<Double>>();
                while((s=br.readLine())!=null){
                    measure.add(Integer.parseInt(s.substring(1,4)));
                    lane.add(Integer.parseInt(s.substring(5,6),16)/2-1);
                    String rawdata=s.substring(7);
                    int datal=rawdata.length()/2;
                    ArrayList<Integer> data=new ArrayList<Integer>();
                    ArrayList<Double> beat=new ArrayList<Double>();
                    double currentbeat=0.0;
                    for(int i=0;i<datal;i++){
                        int currentdata=Integer.parseInt(rawdata.substring(i*2,i*2+1));
                        if(currentdata!=0){
                            data.add(currentdata);
                            beat.add(currentbeat);
                        }
                        currentbeat+=4.0/datal;
                    }
                    wholedata.add(data);
                    wholebeat.add(beat);
                    for(int i=0;i<data.size();i++){
                        pw.print(data.get(i)+"-"+beat.get(i)+" ");
                    }
                    pw.println(" "+measure.get(measure.size()-1)+" "+lane.get(lane.size()-1));
                }
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