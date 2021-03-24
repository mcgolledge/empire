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

import golledge.empire.ui.GameInterface;
import golledge.empire.ui.SettingsUI;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JOptionPane;

public class Game
{
    Random randomGen = new Random();
    public static final int X_MAX = 19;
    public static final int Y_MAX = 19;
    static final int PLAYER_PRODUCTION = 10;

    // User parameters
    GameSettings settings;
    Hashtable<String, World> worlds;
    FleetQueue fleets; // fleets, where the fleets arriving on any given year
                       // are stored in the same vector
                       // Player players[];
    GameInterface mainUI;

    int curYear = 1;

    public int getCurYear()
    {
        return curYear;
    }

    int iPlayer = 0;
    World curFromWorld;
    World curToWorld;

    public Player getCurPlayer()
    {
        return settings.getPlayers()[iPlayer];
    }

    public World getCurFromWorld()
    {
        return curFromWorld;
    }

    public void setCurFromWorld(World curFromWorld)
    {
        this.curFromWorld = curFromWorld;
    }

    public World getCurToWorld()
    {
        return curToWorld;
    }

    public void setCurToWorld(World curToWorld)
    {
        this.curToWorld = curToWorld;
    }

    public Hashtable<String, World> getWorlds()
    {
        return worlds;
    }

    public void setWorlds(Hashtable<String, World> worlds)
    {
        this.worlds = worlds;
    }

    public static void main(String[] args)
    {

        GameSettings settings = new GameSettings();
        Game game = new Game(settings);
        SettingsUI userOptsFrame = new SettingsUI(game);
        userOptsFrame.setModal(false);
        userOptsFrame.setVisible(true);

    }

    public void startGame()
    {
        makeMap();
        iPlayer = 0;
        mainUI.setPlayer(getCurPlayer());
        mainUI.setYear(curYear);
        mainUI.setMode(GameInterface.Mode.FLEET_ENTRY, null);
    }

    private void makeMap()
    {
        int response;
        mainUI = new GameInterface();
        mainUI.setGame(this);
        do
        {
            mainUI.setVisible(false);
            createWorlds();
            fleets = new FleetQueue(settings.getLastYear()); // ArrayList<ArrayList<Fleet>>(nYears);
            mainUI.makeMap(worlds);
            mainUI.setVisible(true);
            response = mainUI.confirmMap();
        }
        while (response != JOptionPane.YES_OPTION);
        mainUI.clearFields();
    }

    public Game(GameSettings settings)
    {
        this.settings = settings;
    }

    private Hashtable<String, World> createWorlds()
    {
        boolean isSpotTaken[][] = new boolean[X_MAX + 1][Y_MAX + 1]; // offsets
                                                                     // at 0;
                                                                     // counts
                                                                     // at 1
        int x;
        int y;

        for (x = 0; x < X_MAX; x++)
            for (y = 0; y < Y_MAX; y++)
                isSpotTaken[x][y] = false;

        int nWorlds = settings.getNumWorlds();
        int nPlayers = settings.getPlayers().length;
        worlds = new Hashtable<String, World>();
        int meanProduction = settings.getMeanProduction();
        int meanShips = settings.getMeanShips();
        for (int n = 0; n < nWorlds; n++)
        {
            do
            {
                x = randomGen.nextInt(X_MAX + 1);
                y = randomGen.nextInt(Y_MAX + 1);
            }
            while (isSpotTaken[x][y]);
            isSpotTaken[x][y] = true;

            // name function is dependent on a default charset where letters are
            // encoded in alphabetical order
            World world = new World();
            if (n < 26)
                world.setName(String.valueOf((char) ((int) 'a' + n)));
            else
                world.setName(String.valueOf((char) ((int) 'A' + n % 26)));
            world.setX(x);
            world.setY(y);

            world.setProduction(gausian(meanProduction));
            world.setShips(gaussian(meanShips, (float) world.getProduction()
                    / meanProduction));
            world.setOwner(Player.neutral);
            worlds.put(world.getName(), world);
        }

        int worldsBound = 0;
        while (worldsBound < nPlayers)
        {
            World w = worlds.get(String
                    .valueOf((char) ((int) 'a' + worldsBound)));
            w.setOwner(settings.getPlayers()[worldsBound]);
            w.setProduction(Game.PLAYER_PRODUCTION);
            w.setKnown(true);
            worldsBound++;
        }

        Enumeration<World> eWorlds1 = worlds.elements();
        while (eWorlds1.hasMoreElements())
        {
            World w1 = eWorlds1.nextElement();
            if (w1.getOwner() != Player.neutral) // w1 belongs to a player
            {
                // w1.setNumShips((12 * nWorlds - 15 * nPlayers) * 2);
                w1.setShips(0);
                World w2;
                Enumeration<World> eWorlds2 = worlds.elements();
                while (eWorlds2.hasMoreElements())
                {
                    w2 = eWorlds2.nextElement();
                    if (w1.getOwner() == w2.getOwner())
                        continue;
                    if (w2.getOwner() == Player.neutral)
                        w1.setShips(w1.getShips()
                                + (delta_t(w1, w2)
                                        * (GameSettings.PRODUCTION_MIN + GameSettings.PRODUCTION_MAX)
                                        / 4 + (GameSettings.SHIPS_INIT_MIN + GameSettings.SHIPS_INIT_MAX)
                                        / 4 / nWorlds));
                    else
                        w1.setShips(w1.getShips() + (100 / delta_t(w1, w2)));
                }
            }

        }

        return worlds;
    }

    static public int delta_t(World a, World b)
    {
        if (a == b)
            return 0; // avoid rounding errors

        // Pythagorean rounded up to the nearest integer
        double distance = Math.sqrt(Math.pow((a.getX() - b.getX()), 2.0)
                + Math.pow((a.getY() - b.getY()), 2.0));
        double time = delta_t(distance);
        return (int) Math.ceil(time);
    }

    static public double delta_t(double distance)
    {
        // Calculated as the time to accelerate to max speed half the way there,
        // and accelerate to 0 the rest of the way there.
        double a = 2.01;
        return Math.sqrt(distance / (2 * a)) * 2;
    }

    private int gaussian(int mean, double scaleFactor)
    {
        int fuzzy = (int) Math.round(gaussian((double) mean) * scaleFactor);
        return fuzzy;
    }

    private int gausian(int mean)
    {
        int fuzzy = (int) Math.round(gaussian((double) mean));
        return fuzzy;
    }

    // produces something like a normal, bounded by 0 and 2x the mean
    private double gaussian(double mean)
    {
        double fuzzy = (randomGen.nextDouble() + randomGen.nextDouble() + randomGen
                .nextDouble()) * mean * 2.0 / 3.0;
        return fuzzy;
    }

    public void endTurn()
    {
        if (iPlayer + 1 < settings.getPlayers().length)
        {
            iPlayer++;
        }
        else
        {
            iPlayer = 0;
            endYear();
        }
        mainUI.setPlayer(getCurPlayer());
        mainUI.clearFields();
    }

    private void endYear()
    {
        mainUI.setState("Fleet Launches");
        mainUI.refreshWorlds();
        mainUI.okContinue("Launches", "Fleets have been launched.");

        addProduction();
        mainUI.refreshWorlds();
        mainUI.okContinue("Production", "Worlds have added production.");

        try
        {
            Thread.sleep(750);
        }
        catch (InterruptedException e)
        {
        }

        mainUI.setYear(++curYear);

        processFleets();
        mainUI.refreshWorlds();
        if (curYear > getLastYear())
        {
            mainUI.disableInput();
            reportStandings();
            return;
        }

        mainUI.setMode(GameInterface.Mode.FLEET_ENTRY, null);
        mainUI.setPlayer(getCurPlayer());
    }

    private void addProduction()
    {
        mainUI.setState("Ship Production");
        Enumeration<World> eWorlds = worlds.elements();

        // iterate through Hashtable values Enumeration
        while (eWorlds.hasMoreElements())
        {
            World w = eWorlds.nextElement();

            if (settings.isNeutralProdOn() || (w.getOwner() != Player.neutral))
                w.produceShips();
        }
    }

    private void processFleets()
    {
        mainUI.setState("Fleet Arrivals");
        // Get fleets arriving this year
        LinkedList<Fleet> arrivals = fleets.getArrivals(curYear);

        // Randomize them using Fisher-Yates
        int choices = arrivals.size();
        int pick;
        Fleet holder;
        while (choices > 1)
        {
            pick = randomGen.nextInt(choices);
            choices--;

            // swap the pick and the last element not yet swapped
            // it may be the same element; that is expected
            holder = arrivals.get(choices);
            arrivals.set(choices, arrivals.get(pick));
            arrivals.set(pick, holder);
        }

        // Process them one at a time
        for (Iterator<Fleet> i = arrivals.iterator(); i.hasNext();)
        {
            Fleet fleet = i.next();
            World dest = fleet.getDestination();
            if (fleet.getOwner() == fleet.getDestination().getOwner())
            {
                mainUI.setMode(GameInterface.Mode.REINFORCEMENTS, fleet);
                mainUI.announceReinforcements(fleet);
                dest.setShips(dest.getShips() + fleet.getShips());
                mainUI.refresh(dest);
            }
            else
            {
                mainUI.setMode(GameInterface.Mode.BATTLE, fleet);
                mainUI.announceBattle(fleet);
                processBattle(fleet);
                mainUI.refresh(dest);
            }

        }

    }

    private void processBattle(Fleet fleet)
    {
        final int DIE_FACES = 8;
        int defRoll;
        int attRoll;
        int wShips = 0;
        int fShips = 0;

        World dest = fleet.getDestination();
        dest.setKnown(true);
        mainUI.refresh(dest);

        while ((dest.getShips() != 0) && (fleet.getShips() != 0))
        {
            fShips = fleet.getShips();
            wShips = dest.getShips();
            defRoll = randomGen.nextInt((int) (DIE_FACES + 0.3 * (DIE_FACES
                    * wShips / (wShips + fShips))));
            attRoll = randomGen.nextInt((int) (DIE_FACES + 0.3 * (DIE_FACES
                    * fShips / (wShips + fShips))));
            if (attRoll <= defRoll)
            {
                fleet.decrement();
            }
            else
            {
                dest.setShips(--wShips);
            }
            mainUI.updateShipCounts(fShips, wShips);
        }
        if (wShips == 0)
        {
            dest.setOwner(fleet.getOwner());
            dest.setShips(fShips);
            mainUI.announcePillagerVictory(fleet);
        }
        else
        {
            mainUI.announceVillagerVictory(fleet);
        }
    }

    private void reportStandings()
    {
        StringBuilder standings = new StringBuilder();

        int playerPlanetCounts[] = new int[settings.getPlayers().length];
        for (int i = 0; i < settings.getPlayers().length; i++)
        {
            playerPlanetCounts[i] = 0;
            Enumeration<World> eWorlds = worlds.elements();
            while (eWorlds.hasMoreElements())
            {
                World w = eWorlds.nextElement();
                if (w.getOwner() == settings.getPlayers()[i])
                    playerPlanetCounts[i]++;
            }

            // Extremely basic output format; doesn't really require an array of
            // scores, but
            // keeping that for future enhancements.
            standings.append("Admiral ");
            standings.append(settings.getPlayers()[i].toString());
            standings.append(": \t");
            standings.append(playerPlanetCounts[i]);
            standings.append('\n');
        }
        JOptionPane.showMessageDialog(mainUI, standings.toString(),
                "Final Standings", JOptionPane.INFORMATION_MESSAGE);

    }

    public int getLastYear()
    {
        return settings.getLastYear();
    }

    public void addFleet(int nShips)
    {
        Fleet f = new Fleet(nShips, getCurPlayer(), curToWorld);
        int arrivalYear = getCurYear()
                + Math.max(1, Game.delta_t(curToWorld, curFromWorld));
        fleets.addFleet(f, arrivalYear);
        curFromWorld.setShips(curFromWorld.getShips() - nShips);
        mainUI.announce("Orders received.");
    }

    public GameSettings getSettings()
    {
        return settings;

    }

}
