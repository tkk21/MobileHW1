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
	    return generatePattern1();
//        return generateRandomPattern();
    }

    /**
     * generating pattern1 for mobile weka experiments
     * 010
     * 101
     * 010
     * @return
     */
    private List<Point> generatePattern1(){
        List<Point> pattern = new ArrayList<Point>();
        pattern.add(new Point(0,1));
        pattern.add(new Point(1,2));
        pattern.add(new Point(2,1));
        pattern.add(new Point(1,0));

        return pattern;
    }

    /**
     * generating pattern2 for mobile weka experiments
     * 111
     * 010
     * 100
     * @return
     */
    private List<Point> generatePattern2(){
        List<Point> pattern = new ArrayList<Point>();

        pattern.add(new Point(0,0));
        pattern.add(new Point(1,0));
        pattern.add(new Point(2,0));
        pattern.add(new Point(1,1));
        pattern.add(new Point(0,2));
        return pattern;
    }

    /**
     * generating pattern3 for mobile weka experiments
     * 111
     * 111
     * 000
     * @return
     */
    private List<Point> generatePattern3(){
        List<Point> pattern = new ArrayList<Point>();

        pattern.add(new Point(0,1));
        pattern.add(new Point(1,1));
        pattern.add(new Point(2,1));
        pattern.add(new Point(2,0));
        pattern.add(new Point(1,0));
        pattern.add(new Point(0,0));
        return pattern;
    }

    /**
     * generating pattern4 for mobile weka experiments
     * 010
     * 110
     * 100
     * @return
     */
    private List<Point> generatePattern4(){
        List<Point> pattern = new ArrayList<Point>();

        pattern.add(new Point(1,0));
        pattern.add(new Point(1,1));
        pattern.add(new Point(0,2));
        pattern.add(new Point(0,1));
        return pattern;
    }
	private List<Point> generateRandomPattern(){
		List<Point> pattern = new ArrayList<Point>();

        Point initPoint = generatePoint();
        pattern.add(initPoint);

        for (int i = 0; i < Defaults.PATTERN_MAX-1; i++) {
            List<Point> candidateList  = generateCandidateList(pattern);
            Point p =getNextNode(candidateList, initPoint);
            pattern.add(p);
            initPoint = p;
            candidateList.remove(p);//prob not needed
        }
        for (Point p : pattern) {
            Log.d("debug", "the point is: " + p.x + ", "+ p.y);
        }
        return pattern;
	}

    public static String patternToString(List<Point> pattern){
        StringBuilder sb = new StringBuilder();
        for (Point p : pattern) {
            sb.append("("+ p.x + "  "+ p.y + ")");
        }
        return sb.toString();
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

        ArrayList<Point> pointsToRemove = new ArrayList<Point>();

        for (Point p : candidateList) {
            deltaX = p.x - initPoint.x;
            deltaY = p.y - initPoint.y;

            gcd = computeGcd(Math.abs(deltaX), Math.abs((deltaY)));

            if (gcd > 1) {

                for (int j = 1; j < gcd; j++) {
                    int unusedX = initPoint.x + deltaX/gcd*j;
                    int unusedY = initPoint.y + deltaY/gcd*j;

                    Point unusedPoint = new Point(unusedX, unusedY);

                    if (candidateList.contains((unusedPoint))) {
                        pointsToRemove.add(p);
                    }
                }
            }

        }
        for (Point p: pointsToRemove){
            candidateList.remove(p);
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
