package com.example.green1.SchedukeApp;

import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Iterator;

public class Schedule
{
    private ArrayList<Class> classList = new ArrayList();
    int counter = 0;
    int position;


    public Schedule()
    {
        classList.add(new Class("Example Class Name", 8, 0 , 1 , "AM"));
    }
    public Schedule(ArrayList<Parcelable> blah)
    {
        for (int i = 0; i < blah.size(); i++)
        {
            classList.add((Class) blah.get(i));
        }
    }
    public void addClass(String paramString, int paramInt1, int paramInt2,int p, String ampm)
    {
        this.counter = 0;
        Class c = new Class(paramString, paramInt1, paramInt2,p, ampm);
        Schedule.this.classList.add(Schedule.this.position, c);
        Schedule.this.organizeClassList();
    }


    public int getSize(){ return classList.size(); }

    public ArrayList<Class> getClassList()
    {
        return this.classList;
    }

    public void organizeClassList()
    {
        ArrayList localArrayList = new ArrayList();
        int i = 1;
        while (i < this.classList.size() + 1)
        {
            Iterator localIterator = this.classList.iterator();
            while (localIterator.hasNext())
            {
                Class localClass = (Class)localIterator.next();
                if (localClass.getPeriod() == i)
                    localArrayList.add(localClass);
            }
            i += 1;
        }
        this.classList = localArrayList;
    }

    public void addToClassList(Class JingleBells)
    {
        classList.add(JingleBells);
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}