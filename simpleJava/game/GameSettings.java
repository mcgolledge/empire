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


public class GameSettings
{
    private int numWorlds;
    private int meanProduction;
    private int meanShips;
    private int playerProduction;
    private boolean isNeutralProdOn = false;
    private Player players[];
    private int lastYear;

    public static final int WORLDS_MAX = 52;
    public static final int WORLDS_MIN = 2;
 
    // initial ships of non-player planets
    public static final int SHIPS_INIT_MAX = 100;
    public static final int SHIPS_INIT_MIN = 0;

    // production capacity of non-player planets
    public static final int PRODUCTION_MAX = 25;
    public static final int PRODUCTION_MIN = 0;

    public static final int PLAYER_PRODUCTION = 10;

    public int getNumWorlds()
    {
        return numWorlds;
    }
    public void setNumWorlds(int numWorlds)
    {
        this.numWorlds = numWorlds;
    }
    public int getMeanProduction()
    {
        return meanProduction;
    }
    public void setMeanProduction(int meanProduction)
    {
        this.meanProduction = meanProduction;
    }
    public int getMeanShips()
    {
        return meanShips;
    }
    public void setMeanShips(int meanShips)
    {
        this.meanShips = meanShips;
    }
    public int getPlayerProduction()
    {
        return playerProduction;
    }
    public void setPlayerProduction(int playerProduction)
    {
        this.playerProduction = playerProduction;
    }
    public boolean isNeutralProdOn()
    {
        return isNeutralProdOn;
    }
    public void setNeutralProdOn(boolean isNeutralProdOn)
    {
        this.isNeutralProdOn = isNeutralProdOn;
    }
    public Player[] getPlayers()
    {
        return players;
    }
    public void setPlayers(Player[] players)
    {
        this.players = players;
    }
    public int getLastYear()
    {
        return lastYear;
    }
    public void setLastYear(int lastYear)
    {
        this.lastYear = lastYear;
    }
   
}
