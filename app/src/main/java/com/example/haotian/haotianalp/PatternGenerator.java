/*
Copyright 2010-2013 Michael Shick

This file is part of 'Lock Pattern Generator'.

'Lock Pattern Generator' is free software: you can redistribute it and/or
modify it under the terms of the GNU General Public License as published by the
Free Software Foundation, either version 3 of the License, or (at your option)
any later version.

'Lock Pattern Generator' is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
details.

You should have received a copy of the GNU General Public License along with
'Lock Pattern Generator'.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.example.haotian.haotianalp;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PatternGenerator
{
    protected int mGridLength;
    protected int mMinNodes;
    protected int mMaxNodes;
    protected Random mRng;
    protected List<Point> mAllNodes;

    public PatternGenerator()
    {
        mRng = new Random();
        setGridLength(0);
        setMinNodes(0);
        setMaxNodes(0);
    }

    public List<Point> getPattern()
    {
        List<Point> pattern = new ArrayList<Point>();

        Point initPoint = generatePoint();
        pattern.add(initPoint);

        List<Point> candidateList;

        for (int i = 0; i < Defaults.PATTERN_MAX-1; i++) {
            candidateList  = generateCandidateList(pattern);
            Point p =getNextNode(candidateList, initPoint);
            pattern.add(p);
            initPoint = p;
//            candidateList.remove(p);
        }
        for (Point p : pattern) {
            Log.d("debug", "the point is: " + p.x + ", "+ p.y);
        }
        return pattern;
    }

    /**
     * generates the candidate list for the next iteration,
     * removing the nodes that are already picked
     * @param patternSoFar
     * @return
     */
    private  List<Point> generateCandidateList(List<Point> patternSoFar){
        List<Point> candidateList = getAllAvailablePointList();
        for (Point p: patternSoFar){
            candidateList.remove(p);
        }
        return candidateList;
    }

    private Point getNextNode(List<Point> candidateList, Point initPoint) {

        int deltaX;
        int deltaY;
        int gcd;

        ArrayList<Point> newCandidateList = new ArrayList<Point>();

        for (Point p : candidateList) {
            deltaX = Math.abs(p.x - initPoint.x);
            deltaY = Math.abs(p.y - initPoint.y);

            gcd = computeGcd(deltaX, deltaY);

            if (gcd > 1) {

                for (int j = 1; j < gcd; j++) {
                    int unusedX = initPoint.x + deltaX/gcd*j;
                    int unusedY = initPoint.y + deltaY/gcd*j;

                    Point unusedPoint = new Point(unusedX, unusedY);

                    if (candidateList.contains((unusedPoint))) {
                        continue;
                    }
                    newCandidateList.add(p);
                }
            }
        }

        return candidateList.get((int)(candidateList.size()*Math.random()));
    }

    private Point generatePoint() {
        return new Point((int)(Defaults.GRID_LENGTH*Math.random()), (int)(Defaults.GRID_LENGTH*Math.random()));
    }
    private List<Point> getAllAvailablePointList (){
        List<Point> allAvailablePointList = new ArrayList<Point>();
        for (int i=  0; i<Defaults.GRID_LENGTH; i++){
            for (int j = 0; j<Defaults.GRID_LENGTH; j++){
                allAvailablePointList.add(new Point(i,j));
            }
        }
        return allAvailablePointList;
    }
    //
    // Accessors / Mutators
    //

    public void setGridLength(int length)
    {
        // build the prototype set to copy from later
        List<Point> allNodes = new ArrayList<Point>();
        for(int y = 0; y < length; y++)
        {
            for(int x = 0; x < length; x++)
            {
                allNodes.add(new Point(x,y));
            }
        }
        mAllNodes = allNodes;

        mGridLength = length;
    }
    public int getGridLength()
    {
        return mGridLength;
    }

    public void setMinNodes(int nodes)
    {
        mMinNodes = nodes;
    }
    public int getMinNodes()
    {
        return mMinNodes;
    }

    public void setMaxNodes(int nodes)
    {
        mMaxNodes = nodes;
    }
    public int getMaxNodes()
    {
        return mMaxNodes;
    }

    //
    // Helper methods
    //

    public static int computeGcd(int a, int b)
    /* Implementation taken from
     * http://en.literateprograms.org/Euclidean_algorithm_(Java)
     * Accessed on 12/28/10
     */
    {
        if(b > a)
        {
            int temp = a;
            a = b;
            b = temp;
        }

        while(b != 0)
        {
            int m = a % b;
            a = b;
            b = m;
        }

        return a;
    }
}
