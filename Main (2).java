package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.lang.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.company.Company.*;
import static com.company.Trader.deleteUser;
import static com.company.Trader.deleteUser1;


public class Main {




    public static void main(String[] args) {


        BufferedReader br = null;
        String line;
        Company[] scrip = new Company[6];
        int i = 0;
        Trader[] user = new Trader[4];
        int j = 0;
        PlaceOrder[] place = new PlaceOrder[6];
        int k = 0;
        int flag = 0;
        int flag1 = 0;
        int flag2 = 0;
        int flag3 = 0;
        int flag4 = 0;
        int flag5 = 0;
        int flag6 = 0;
        String[] deleteuser = new String[2];
        int delete1 = 0;
        String[] deletescrip = new String[2];
        int delete2 = 0;



        try {

            br = new BufferedReader(new FileReader("C:\\Users\\mskhu\\Desktop\\sample_input2.txt"));
            while ((line = br.readLine()) != null) {


                line = line.replaceAll("[,:]", " ");
                line = line.replaceAll("\\s{2,}", " ").trim();
                String[] tokens = line.split(" ");
                List<String> wordlist = Arrays.asList(tokens);

                if (wordlist.get(0).equals("Add")){
                        if(wordlist.get(1).equals("scrip")) {

                            scrip[i] = new Company(wordlist.get(2), wordlist.get(4), wordlist.get(6), wordlist.get(8), wordlist.get(10), wordlist.get(12));
                            System.out.print("Added scrip: " + wordlist.get(2) + " with a new instantiation of Company\n");
                            i++;

                        }

                        else if (wordlist.get(1).equals("user")) {
                            user[j] = new Trader(wordlist.get(2), wordlist.get(4));
                            System.out.print("Added user: " + wordlist.get(2) + " with a new instantiation of Trader\n");
                            j++;
                        }
                    }

                else if (wordlist.get(0).equals("Place") && wordlist.get(1).equals("order"))
                    {
                        if(flag == 0)
                        {
                            System.out.println("Market Opens");
                            flag++;
                        }

                        place[k] = new PlaceOrder(wordlist.get(3), wordlist.get(5), wordlist.get(7), wordlist.get(9), wordlist.get(11));
                        int b = checkPlaceOrder(place[k],user,scrip);

                        if(b == 0)
                            {
                                System.out.print("Order placed for user: " + wordlist.get(3) +", type:" + wordlist.get(5) + ", scrip: " + wordlist.get(7) + ", qty:" + wordlist.get(9) + ", rate: " + wordlist.get(11) + "\n");
                            }

                        else if(b == 2)
                                {
                                    System.out.print("Order rejected for user: " + wordlist.get(3) +", type:" + wordlist.get(5) + ", scrip: " + wordlist.get(7) + ", qty:" + wordlist.get(9) + ", rate: " + wordlist.get(11) + ", reason: lower circuit violation.\n");
                                }
                        else if(b == 3)
                                {
                                    System.out.print("Order rejected for user: " + wordlist.get(3) +", type:" + wordlist.get(5) + ", scrip: " + wordlist.get(7) + ", qty:" + wordlist.get(9) + ", rate: " + wordlist.get(11) + ", reason: upper circuit violation.\n");
                                }

                        else if(b == 1)
                            {
                                System.out.print("Order rejected for user: " + wordlist.get(3) +", type:" + wordlist.get(5) + ", scrip: " + wordlist.get(7) + ", qty:" + wordlist.get(9) + ", rate: " + wordlist.get(11) + ", reason: Insufficient funds.\n");
                            }



                        k++;
                    }

                else if(wordlist.get(0).equals("Show") && wordlist.get(1).equals("Orderbook"))
                {
                    if(flag1 == 0)
                    {
                        System.out.println("\nOrderbook:");
                        flag1++;
                    }
                    int b = 1;
                    for(int d = 0; d<6; d++) {
                        b = checkPlaceOrder(place[d], user, scrip);
                        if(b == 0)
                        {
                            System.out.println(place[d].type+ " order " + place[d].scrip + ":" + place[d].quantity + " at " +  place[d].rate);
                        }
                    }


                }
                else if(wordlist.get(0).equals("Execute"))
                {
                    if(flag3 == 0)
                    {
                        System.out.println("\nExecuted transactions:");
                        flag3++;
                    }
                    int b = 1;
                    int h = 0;
                    PlaceOrder[] placenew = new PlaceOrder[4];
                    for(int d = 0; d<6; d++) {
                        b = checkPlaceOrder(place[d], user, scrip);
                        if(b == 0)
                        {
                            placenew[h] = place[d];
                            h++;
                        }
                    }
                    for(int g = 0; g<4; g++)
                    {
                        for(int f = 0; f<4; f++)
                        {
                            if(placenew[g].type.equals("sell"))
                            {
                                if(placenew[f].type.equals("buy"))
                                {
                                    if((placenew[g].scrip).equals(placenew[f].scrip))
                                    {
                                        System.out.println(placenew[g].quantity + " qty of scrip:" + placenew[g].scrip + " sold for INR " + placenew[g].rate + "; Buyer: " + placenew[f].user + ", Seller: " + placenew[g].user);
                                    }
                                }
                            }
                        }
                    }

                }

                else if(wordlist.get(0).equals("Show") && wordlist.get(1).equals("sector"))
                    {
                        String sec = wordlist.get(2);
                        if(flag2 == 0)
                        {
                        System.out.println("\nScrips listed in sector: " + sec);
                        flag2++;
                        }
                        for(int t = 0; t<6; t++)
                        {
                            if(scrip[t] !=null) {
                                if (scrip[t].sector.equals(sec)) {
                                    System.out.println(scrip[t].ticker + ", OHLC = <" + scrip[t].openPrice + "," + scrip[t].highPrice + "," + scrip[t].lowPrice + "," + scrip[t].closedPrice + ">");
                                }
                            }
                        }
                    }
                else if(wordlist.get(0).equals("Show") && wordlist.get(1).equals("Scrips"))
                    {
                        if(flag5 == 0)
                        {
                            System.out.println("\nScrips: ");
                            flag5++;
                        }

                     for(int z = 0; z<6; z++) {
                         if ((scrip[z].ticker).equals(deletescrip[0]) || (scrip[z].ticker).equals(deletescrip[1])) {
                             deleteScrip1(scrip[z]);
                         } else
                             System.out.println("scrip: " + scrip[z].ticker + ", sector: " + scrip[z].sector + ", O:" + scrip[z].openPrice + ", H:" + scrip[z].highPrice + ", L:" + scrip[z].lowPrice + ", C:" + scrip[z].closedPrice);


                     }


                    }
                else if(wordlist.get(0).equals("Show") && wordlist.get(1).equals("Users"))
                    {
                        if(flag6 == 0)
                        {
                            System.out.println("\nUsers: ");
                            flag6++;
                        }
                        for(int y = 0; y<4; y++)
                        {
                            if ((user[y].name).equals(deleteuser[0]) || (user[y].name).equals(deleteuser[1])) {
                                deleteUser1(user[y]);
                            }
                            else
                                System.out.println("user: " + user[y].name + ", funds:" + user[y].funds + ", holding: {}");

                        }
                    }
                else if(wordlist.get(0).equals("Delete") && wordlist.get(1).equals("scrip"))
                {
                    if(flag4 == 0)
                    {
                    System.out.print("\n");
                    flag4++;
                    }
                    String a = wordlist.get(2);
                    deletescrip[delete2] = a;
                    for(int x = 0; x<6; x++)
                    {
                        if(scrip[x].ticker.equals(a)){
                            deleteScrip(scrip[x]);
                        }
                    }
                    delete2++;
                }
                else if(wordlist.get(0).equals("Delete") && wordlist.get(1).equals("User"))
                {
                    String u = wordlist.get(2);
                    deleteuser[delete1] = u;
                    for(int v = 0; v<4; v++)
                    {
                        if(user[v].name.equals(u)){
                            deleteUser(user[v]);

                        }
                    }
                    delete1++;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }


}

class PlaceOrder {
    String user;
    String type;
    String scrip;
    int quantity;
    int rate;

    PlaceOrder(String u, String t, String s, String q, String r)
    {
        user = u;
        type = t;
        scrip = s;
        quantity = Integer.parseInt(q);
        rate = Integer.parseInt(r);
    }


}
class Trader {
    String name;
    int funds;
    ArrayList<String> holdingCom=new ArrayList<>();
    ArrayList<Integer> holdingCount=new ArrayList<>();
    int transactionId;


    Trader(String a,String b)
    {
        name = a;
        funds = Integer.parseInt(b);
    }
    public static void deleteUser(Trader t)
    {
        System.out.println("Deleted User: " + t.name);
        t = null;
    }
    public static void deleteUser1(Trader t)
    {
        t = null;
    }

}

class Company {
    String ticker;
    int openPrice;
    int closedPrice;
    int highPrice;
    int lowPrice;
    String sector;

    Company(String a, String b, String o, String h, String l, String c)
    {
        ticker = a;
        sector = b;
        openPrice = Integer.parseInt(o);
        highPrice = Integer.parseInt(h);
        lowPrice = Integer.parseInt(l);
        closedPrice = Integer.parseInt(c);
    }

    public static void deleteScrip(Company c)
    {
        System.out.println("Deleted scrip: " + c.ticker);
        c = null;
    }

    public static void deleteScrip1(Company c)
    {
        c = null;
    }




    public static int getFunds(PlaceOrder p,Trader[] t)
    {
        int fund = 0;
        for (int d = 0; d < 4; d++)
        {
            if (t[d].name.equals(p.user))
            {
                fund = t[d].funds;
            }
        }
        return fund;
    }
    public static double getUpperCircuit(PlaceOrder p,Company[] c)
    {
        double u=0;
        for (int e = 0; e < 6; e++)
        {
            if (c[e].ticker.equals(p.scrip))
            {
                u = (double)(c[e].closedPrice) + (double)(c[e].closedPrice)*(0.1);
            }
        }
        return u;
    }
    public static double getLowerCircuit(PlaceOrder p,Company[] c)
    {
        double l=0;
        for (int s = 0;s < 6; s++)
        {
            if((c[s].ticker).equals(p.scrip))
            {
                l = (double)(c[s].closedPrice) - (double)(c[s].closedPrice)*(0.1);
            }

        }
        return l;
    }
    public static int checkPlaceOrder(PlaceOrder p,Trader[] t,Company[] c){
        int b = 0;

        int fund = getFunds(p,t);
        double uc = getUpperCircuit(p,c);
        double lc = getLowerCircuit(p,c);

        if(p.type.equals("buy"))
        {
            if(((p.rate)*(p.quantity)) <= fund )
            {
               if(p.rate >= lc && p.rate <= uc)
                {
                    b = 0;
                }
                else if(p.rate < lc)
                {
                    b = 2;
                }
                else if(p.rate > uc)
                {
                    b = 3;
                }
            }
            else if(((p.rate)*(p.quantity)) > fund)
            {
                b = 1;
            }

        }
        else if(p.type.equals("sell"))
        {
            b = 0;
        }

        return b;

    }
}