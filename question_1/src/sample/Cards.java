
//Obaida Issa 100702054
//CSCI 2020U




package sample;

import java.awt.*;
import javax.swing.*;



public class Cards extends JFrame

{

    //constructor of the class

    public Cards()

    {

        //declaring layout of grid

        setLayout(new GridLayout(1,3,5,5));

        //declaration of imageicon array

        ImageIcon []icon=new ImageIcon[52];



        for(int i=1; i<52 ; i++)

        {
            //pathway to the png images on my computer
            icon[i]= new ImageIcon("C:\\Users\\obaid\\IdeaProjects\\question_1\\src\\sample"+(i)+".png");

        }

        //random number generation

        int r=(int)(Math.random()*52);

        int q=(int)(Math.random()*52);

        int p=(int)(Math.random()*52);

        //adding the random image to JLabel

        add(new JLabel(icon[r]));

        add(new JLabel(icon[q]));

        add(new JLabel(icon[p]));

    }

    //main method

    public static void main(String args[])

    {

        Cards cards = new Cards();

        //frame title

        cards.setTitle("Question 1");

        //frame size

        cards.setSize(300,200);

        //exit frame on close

        cards.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

        //set the visibility of frame

        cards.setVisible(true);

    }

}