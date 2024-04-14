import java.util.Scanner;

public class Recommendation
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt(); //Nはユーザー数
        int M = scanner.nextInt(); //Mは書籍数

        double[][] score = new double[N][M]; //ユーザーiの書籍jに対する評価

        //入力処理
        for(int i = 0; i < N; i++)
        {
            for(int j = 0; j < M; j++)
            {
                score[i][j] = scanner.nextDouble();
            }
        }
        
        //ユーザー1に対する類似度
        //今回はテストのため仮の値
        double[] sim = {0,0.238,0.618,0.356,0.174};
        
        //推薦度の計算
        //今回はユーザー1についてのみ
        double[] rate = Recommend_Rate(score,sim,0,N,M); 

        //結果の出力
        //"書籍番号 推薦度"のフォーマットで、推薦度の降順
        while(true)
        {
            double rank = rate[0]; //推薦度の保持用
            int num = 0; //書籍番号の保持用
            
            //一番高い推薦度を検索
            for(int i = 1; i < M; i++)
            {
                if(rank < rate[i]) //推薦度の比較
                {
                    //入れ替え処理
                    num = i;
                    rank = rate[i];
                }
            }

            if(rank == -1) break; //出力の終了
            else
            {
                //出力処理
                System.out.printf("%d %f\n",num+1,rank);
                rate[num] = -1; //表示済みに
            }
        }

        System.exit(0);
    } 

    public static double[] Recommend_Rate(double[][] score,double[] sim,int x,int N,int M) //xはターゲットにするユーザー
    {
        double[] rate = new double[M]; //推薦度、戻り値に使用

        //推薦度の計算処理
        for(int i = 0; i < M; i++)
        {
            rate[i] = 0; //推薦度を初期化

            //ユーザーxが読んでいないなら
            if(score[x][i] == -1) 
            {
                double sum = 0; //類似度の合計を保持

                    for(int j = 0; j < N; j++)
                    {
                        //ユーザーjがターゲット自身、またはユーザーjが読んでいない
                        if(j == x || score[j][i] == -1) continue; 
                        else
                        {
                            rate[i] += score[j][i]*sim[j]; //推薦度 = 評価*類似度
                            sum += sim[j]; //類似度の合計
                        }
                    }

                    rate[i] = rate[i]/sum; //最終的な推薦度
            }
            else
            {
                    rate[i] = -1; //推薦度を推薦しないに
                    continue;
            }
        }

        return rate;
    }
}