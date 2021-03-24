/*******************************************************************************
 * Copyright 2013 M. Chris Golledge
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package golledge.empire.game;


/**
 * 
 */

/**
 * @author Chris Golledge
 * 
 */
public class World
{
    private int numShips;
    private Player owner;
    private int x;
    private int y;
    private int production;
    private String name;
    private boolean isKnown = false;

    public boolean isKnown()
    {
        return isKnown;
    }

    public void setKnown(boolean isKnown)
    {
        this.isKnown = isKnown;
    }

    public int getShips()
    {
        return numShips;
    }

    public void setShips(int numShips)
    {
        this.numShips = numShips;
    }

    public Player getOwner()
    {
        return owner;
    }

    public void setOwner(Player owner)
    {
        this.owner = owner;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getProduction()
    {
        return production;
    }

    public void setProduction(int production)
    {
        this.production = production;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String toString()
    {
        String str;
        if (isKnown)
        {
            if (Player.neutral == owner)
                str = name + "\r\n" + String.valueOf(numShips);
            else
                str = name + " " + String.valueOf(production) + "\r\n"
                        + String.valueOf(numShips);
        }

        else
            str = name + "\r\n   ";

        return str;
    }

    public void produceShips()
    {
        numShips += production;
    }

}
